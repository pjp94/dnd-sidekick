package com.pancholi.grabbag.ui.screen.location

import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.mapper.LocationMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.Location
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
    private val dispatcher: Dispatcher
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
                .catch { _viewState.value = Result.Error(it) }
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val locations = entities.map { locationMapper.fromEntity(it) }
                        val viewState = ViewState(items = locations)
                        _viewState.value = Result.Success(viewState)
                    } else {
                        _viewState.value = Result.Error(EmptyDatabaseException())
                    }
                }
        }
    }

    override fun onSaveClicked(model: CategoryModel) {
        val location = model as Location
        val requiredFields = listOf(location.name, location.type)

        if (areFieldsMissing(requiredFields)) {
            viewModelScope.launch { _showRequired.emit(true) }
        } else {
            val entity = locationMapper.toEntity(location)

            viewModelScope.launch(dispatcher.io) {
                locationRepository.insertLocation(entity)
                _locationSaved.emit(true)
            }
        }
    }
}