package com.pancholi.grabbag.ui.screen.location

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.LocationMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.repository.LocationRepository
import com.pancholi.grabbag.ui.screen.modelaction.ActionViewModel
import com.pancholi.grabbag.ui.screen.modelaction.ModelAction
import com.pancholi.grabbag.ui.screen.modelaction.ModelEditor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationActionViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val locationMapper: LocationMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : ActionViewModel(), ModelEditor {

    override fun onSaveClicked(
        model: CategoryModel,
        action: ModelAction
    ) {
        val location = model as CategoryModel.Location
        val requiredFields = listOf(location.name, location.type)

        if (areFieldsMissing(requiredFields)) {
            showRequiredSupportingText()
        } else {
            val entity = locationMapper.toEntity(location)

            viewModelScope.launch(dispatcher.io) {
                when (action) {
                    ModelAction.ADD -> locationRepository.insertLocation(entity)
                    ModelAction.EDIT -> locationRepository.updateLocation(entity)
                }
            }

            onModelSaved()

            val messageId = when (action) {
                ModelAction.ADD -> R.string.location_saved
                ModelAction.EDIT -> R.string.location_updated
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(messageId)
            )

            showSnackbar(visuals)
        }
    }

    override fun getModelById(id: Int) {
        viewModelScope.launch(dispatcher.io) {
            locationRepository
                .getById(id)
                .collect {
                    val npc = locationMapper.fromEntityToEdit(it)
                    onModelToEditLoaded(npc)
                }
        }
    }
}