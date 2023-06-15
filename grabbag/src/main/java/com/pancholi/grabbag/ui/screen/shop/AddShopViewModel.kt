package com.pancholi.grabbag.ui.screen.shop

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.ShopMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.repository.ShopRepository
import com.pancholi.grabbag.ui.screen.AddViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddShopViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val shopMapper: ShopMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : AddViewModel() {

    override fun onSaveClicked(model: CategoryModel) {
        val shop = model as CategoryModel.Shop
        val requiredFields = listOf(shop.name, shop.type)

        if (areFieldsMissing(requiredFields)) {
            showRequiredSupportingText()
        } else {
            val entity = shopMapper.toEntity(shop)

            viewModelScope.launch(dispatcher.io) {
                shopRepository.insertShop(entity)
                onModelSaved()
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(R.string.shop_saved)
            )

            showSnackbar(visuals)
        }
    }
}