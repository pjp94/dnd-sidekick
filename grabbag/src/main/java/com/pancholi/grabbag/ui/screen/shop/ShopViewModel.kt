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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val shopMapper: ShopMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : CategoryViewModel() {

    private val _showRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showRequired: StateFlow<Boolean> = _showRequired.asStateFlow()

    private val _shopSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val shopSaved: StateFlow<Boolean> = _shopSaved.asStateFlow()

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
            viewModelScope.launch { _showRequired.emit(true) }
        } else {
            val entity = shopMapper.toEntity(shop)

            viewModelScope.launch(dispatcher.io) {
                shopRepository.insertShop(entity)
                _shopSaved.emit(true)
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
}