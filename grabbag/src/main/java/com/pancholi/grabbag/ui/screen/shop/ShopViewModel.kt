package com.pancholi.grabbag.ui.screen.shop

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.ShopMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.repository.ShopRepository
import com.pancholi.grabbag.viewmodel.CategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val shopMapper: ShopMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : CategoryViewModel() {

    data class ShopViewState(
        val showRequired: Boolean = false,
        val shopSaved: Boolean = false
    )

    private val _shopViewState: MutableStateFlow<ShopViewState> = MutableStateFlow(ShopViewState())
    val shopViewState: StateFlow<ShopViewState> = _shopViewState.asStateFlow()

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(dispatcher.io) {
            shopRepository
                .getAllShops()
                .distinctUntilChanged()
                .catch { _viewState.emit(Result.Error(it)) }
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val shops = entities.map { shopMapper.fromEntity(it) }
                        val viewState = ViewState(items = shops)
                        _viewState.emit(Result.Success(viewState))
                    } else {
                        _viewState.emit(Result.Error(EmptyDatabaseException()))
                    }
                }
        }
    }

    override fun onSaveClicked(model: CategoryModel) {
        val shop = model as CategoryModel.Shop
        val requiredFields = listOf(shop.name, shop.type)

        if (areFieldsMissing(requiredFields)) {
            _shopViewState.update { it.copy(showRequired = true) }
        } else {
            val entity = shopMapper.toEntity(shop)

            viewModelScope.launch(dispatcher.io) {
                shopRepository.insertShop(entity)
                _shopViewState.update { it.copy(shopSaved = true) }
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(R.string.shop_saved)
            )

            showSnackbar(visuals)
        }
    }

    override fun onModelsImported(models: List<ImportedModel>?) {
        models?.let {
            val shops = models.map { shopMapper.toEntity(it as ImportedModel.ImportedShop) }

            viewModelScope.launch(dispatcher.io) {
                shopRepository.insertAllShops(shops)
            }
        }
    }

    override fun onDeleteModel(model: CategoryModel) {
        val shop = model as CategoryModel.Shop
        val entity = shopMapper.toEntity(shop)

        viewModelScope.launch(dispatcher.io) {
            val deleted = shopRepository.deleteShop(entity)

            if (deleted > 0) {
                val visuals = SidekickSnackbarVisuals(
                    message = resources.getString(R.string.shop_deleted)
                )

                showSnackbar(visuals)
            }
        }
    }

    override fun onModelSaved() {
        _shopViewState.update { it.copy(shopSaved = false) }
    }

    override fun onBackPressed() {
        _shopViewState.update { it.copy(showRequired = false) }
        onModelSaved()
    }
}