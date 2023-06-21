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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopActionViewModel @Inject constructor(
    private val shopRepository: ShopRepository,
    private val shopMapper: ShopMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : ActionViewModel(), ModelEditor {

    private val defaultShops = resources.getStringArray(R.array.shops)
    private val npcs = mutableListOf<String>()

    private lateinit var shops: List<String>

    init {
        getAllTypes()
        getAllNpcNames()
    }

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
                    ModelAction.ADD -> {
                        shopRepository.insertShop(entity)
                        val visuals = SidekickSnackbarVisuals(
                            message = resources.getString(R.string.shop_saved)
                        )

                        showSnackbar(visuals)
                    }
                    ModelAction.EDIT -> shopRepository.updateShop(entity)
                }
            }

            onModelSaved()
            resetViewState()
        }
    }

    override fun getModelById(id: Int) {
        viewModelScope.launch(dispatcher.io) {
            shopRepository
                .getById(id)
                .filterNotNull()
                .collect {
                    val shop = shopMapper.fromEntityToEdit(it)
                    onModelToEditLoaded(shop)
                }
        }
    }

    fun onTypeChanged(text: String) {
        onPropertyFieldTextChanged(
            text = text,
            options = shops
        )
    }

    fun onOwnerChanged(text: String) {
        onPropertyFieldTextChanged(
            text = text,
            options = npcs
        )
    }

    private fun getAllTypes() {
        viewModelScope.launch(dispatcher.io) {
            shopRepository
                .getAllTypes()
                .collect { savedTypes ->
                    savedTypes
                        .filter { it.isNotBlank() }
                        .toMutableList()
                        .apply { addAll(defaultShops) }
                        .distinct()
                        .sorted()
                        .also { shops = it }
                }
        }
    }

    private fun getAllNpcNames() {
        viewModelScope.launch(dispatcher.io) {
            shopRepository
                .getAllNpcNames()
                .collect { savedNpcs ->
                    npcs.apply {
                        clear()
                        addAll(savedNpcs)
                    }
                }
        }
    }
}