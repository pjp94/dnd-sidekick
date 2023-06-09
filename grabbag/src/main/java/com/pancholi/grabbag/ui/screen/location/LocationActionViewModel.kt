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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationActionViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val locationMapper: LocationMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : ActionViewModel(), ModelEditor {

    private val defaultLocations = resources.getStringArray(R.array.locations)

    private lateinit var locations: List<String>

    init {
        getAllTypes()
    }

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
                    ModelAction.ADD -> {
                        locationRepository.insertLocation(entity)
                        val visuals = SidekickSnackbarVisuals(
                            message = resources.getString(R.string.location_saved)
                        )

                        showSnackbar(visuals)
                    }
                    ModelAction.EDIT -> locationRepository.updateLocation(entity)
                }
            }

            onModelSaved()
            resetViewState()
        }
    }

    override fun getModelById(id: Int) {
        viewModelScope.launch(dispatcher.io) {
            locationRepository
                .getById(id)
                .filterNotNull()
                .collect {
                    val npc = locationMapper.fromEntityToEdit(it)
                    onModelToEditLoaded(npc)
                }
        }
    }

    fun onTypeChanged(text: String) {
        onPropertyFieldTextChanged(
            text = text,
            options = locations
        )
    }

    private fun getAllTypes() {
        viewModelScope.launch(dispatcher.io) {
            locationRepository
                .getAllTypes()
                .collect { savedTypes ->
                    savedTypes
                        .filter { it.isNotBlank() }
                        .toMutableList()
                        .apply { addAll(defaultLocations) }
                        .distinct()
                        .sorted()
                        .also { locations = it }
                }
        }
    }
}