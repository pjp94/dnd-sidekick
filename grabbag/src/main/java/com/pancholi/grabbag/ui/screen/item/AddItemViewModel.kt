package com.pancholi.grabbag.ui.screen.item

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.ItemMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.repository.ItemRepository
import com.pancholi.grabbag.ui.screen.AddViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val itemMapper: ItemMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : AddViewModel() {

    override fun onSaveClicked(model: CategoryModel) {
        val item = model as CategoryModel.Item
        val requiredFields = listOf(item.name, item.type)

        if (areFieldsMissing(requiredFields)) {
            showRequiredSupportingText()
        } else {
            val entity = itemMapper.toEntity(item)

            viewModelScope.launch(dispatcher.io) {
                itemRepository.insertItem(entity)
                onModelSaved()
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(R.string.item_saved)
            )

            showSnackbar(visuals)
        }
    }
}