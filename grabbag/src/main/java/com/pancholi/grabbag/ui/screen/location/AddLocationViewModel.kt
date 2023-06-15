package com.pancholi.grabbag.ui.screen.location

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.LocationMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.repository.LocationRepository
import com.pancholi.grabbag.ui.screen.AddViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val locationMapper: LocationMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : AddViewModel() {

    override fun onSaveClicked(model: CategoryModel) {
        val location = model as CategoryModel.Location
        val requiredFields = listOf(location.name, location.type)

        if (areFieldsMissing(requiredFields)) {
            showRequiredSupportingText()
        } else {
            val entity = locationMapper.toEntity(location)

            viewModelScope.launch(dispatcher.io) {
                locationRepository.insertLocation(entity)
                onModelSaved()
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(R.string.location_saved)
            )

            showSnackbar(visuals)
        }
    }
}