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
import com.pancholi.grabbag.repository.ShopRepository
import com.pancholi.grabbag.ui.screen.CategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(dispatcher.io) {
            shopRepository
                .getAllShops()
                .distinctUntilChanged()
                .catch { onResultChanged(Result.Error(it)) }
                .collect { entities ->
                    val result = if (entities.isNotEmpty()) {
                        val shops = entities.map { shopMapper.fromEntity(it) }
                        Result.Success(shops)
                    } else {
                        Result.Error(EmptyDatabaseException())
                    }

                    onResultChanged(result)
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