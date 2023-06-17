package com.pancholi.grabbag.ui.screen.item

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.ItemMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.repository.ItemRepository
import com.pancholi.grabbag.ui.screen.modelaction.ActionViewModel
import com.pancholi.grabbag.ui.screen.modelaction.ModelAction
import com.pancholi.grabbag.ui.screen.modelaction.ModelEditor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemActionViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val itemMapper: ItemMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : ActionViewModel(), ModelEditor {

    override fun onSaveClicked(
        model: CategoryModel,
        action: ModelAction
    ) {
        val item = model as CategoryModel.Item
        val requiredFields = listOf(item.name, item.type)

        if (areFieldsMissing(requiredFields)) {
            showRequiredSupportingText()
        } else {
            val entity = itemMapper.toEntity(item)

            viewModelScope.launch(dispatcher.io) {
                when (action) {
                    ModelAction.ADD -> {
                        itemRepository.insertItem(entity)
                        val visuals = SidekickSnackbarVisuals(
                            message = resources.getString(R.string.item_saved)
                        )

                        showSnackbar(visuals)
                    }
                    ModelAction.EDIT -> itemRepository.updateItem(entity)
                }
            }

            onModelSaved()
            resetViewState()
        }
    }

    override fun getModelById(id: Int) {
        viewModelScope.launch(dispatcher.io) {
            itemRepository
                .getById(id)
                .collect {
                    val item = itemMapper.fromEntityToEdit(it)
                    onModelToEditLoaded(item)
                }
        }
    }
}