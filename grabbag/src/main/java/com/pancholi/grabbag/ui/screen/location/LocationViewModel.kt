package com.pancholi.grabbag.ui.screen.location

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.LocationMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.repository.LocationRepository
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
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val locationMapper: LocationMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : CategoryViewModel() {

    private val _showRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showRequired: StateFlow<Boolean> = _showRequired.asStateFlow()

    private val _locationSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val locationSaved: StateFlow<Boolean> = _locationSaved.asStateFlow()

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(dispatcher.io) {
            locationRepository
                .getAllLocations()
                .distinctUntilChanged()
                .catch { _viewState.emit(Result.Error(it)) }
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val locations = entities.map { locationMapper.fromEntity(it) }
                        val viewState = ViewState(items = locations)
                        _viewState.emit(Result.Success(viewState))
                    } else {
                        _viewState.emit(Result.Error(EmptyDatabaseException()))
                    }
                }
        }
    }

    override fun onSaveClicked(model: CategoryModel) {
        val location = model as CategoryModel.Location
        val requiredFields = listOf(location.name, location.type)

        if (areFieldsMissing(requiredFields)) {
            viewModelScope.launch { _showRequired.emit(true) }
        } else {
            val entity = locationMapper.toEntity(location)

            viewModelScope.launch(dispatcher.io) {
                locationRepository.insertLocation(entity)
                _locationSaved.emit(true)
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(R.string.location_saved)
            )

            showSnackbar(visuals)
        }
    }

    override fun onModelsImported(models: List<ImportedModel>?) {
        models?.let {
            val locations = models.map { locationMapper.toEntity(it as ImportedModel.ImportedLocation) }

            viewModelScope.launch(dispatcher.io) {
                locationRepository.insertAllLocations(locations)
            }
        }
    }

    override fun onDeleteModel(model: CategoryModel) {
        val location = model as CategoryModel.Location
        val entity = locationMapper.toEntity(location)

        viewModelScope.launch(dispatcher.io) {
            val deleted = locationRepository.deleteLocation(entity)

            if (deleted > 0) {
                val visuals = SidekickSnackbarVisuals(
                    message = resources.getString(R.string.location_deleted)
                )

                showSnackbar(visuals)
            }
        }
    }
}