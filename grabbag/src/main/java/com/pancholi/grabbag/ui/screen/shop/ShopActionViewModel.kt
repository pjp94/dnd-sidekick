package com.pancholi.grabbag.ui.screen.shop

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.ShopMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.repository.ShopRepository
import com.pancholi.grabbag.ui.screen.modelaction.ActionViewModel
import com.pancholi.grabbag.ui.screen.modelaction.ModelAction
import com.pancholi.grabbag.ui.screen.modelaction.ModelEditor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopActionViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val shopMapper: ShopMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : ActionViewModel(), ModelEditor {

    override fun onSaveClicked(
        model: CategoryModel,
        action: ModelAction
    ) {
        val shop = model as CategoryModel.Shop
        val requiredFields = listOf(shop.name, shop.type)

        if (areFieldsMissing(requiredFields)) {
            showRequiredSupportingText()
        } else {
            val entity = shopMapper.toEntity(shop)

            viewModelScope.launch(dispatcher.io) {
                when (action) {
                    ModelAction.ADD -> shopRepository.insertShop(entity)
                    ModelAction.EDIT -> shopRepository.updateShop(entity)
                }
            }

            onModelSaved()

            val messageId = when (action) {
                ModelAction.ADD -> R.string.shop_saved
                ModelAction.EDIT -> R.string.shop_updated
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(messageId)
            )

            showSnackbar(visuals)
        }
    }

    override fun getModelById(id: Int) {
        viewModelScope.launch(dispatcher.io) {
            shopRepository
                .getById(id)
                .collect {
                    val shop = shopMapper.fromEntityToEdit(it)
                    onModelToEditLoaded(shop)
                }
        }
    }
}