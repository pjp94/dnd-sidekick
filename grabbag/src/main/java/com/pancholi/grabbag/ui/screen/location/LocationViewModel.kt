package com.pancholi.grabbag.ui.screen.location

import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.mapper.LocationMapper
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

    private val _viewState: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    override val viewState: StateFlow<Result> = _viewState.asStateFlow()

    private val _showRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showRequired: StateFlow<Boolean> = _showRequired.asStateFlow()

    private val _locationSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val locationSaved: StateFlow<Boolean> = _locationSaved.asStateFlow()

    private var name: String? = null
    private var type: String? = null
    private var description: String? = null

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

    override fun onSaveClicked() {
        if (areFieldsMissing(listOf(name, type))) {
            viewModelScope.launch { _showRequired.emit(true) }
        } else {
            val location = Location(
                name = name.orEmpty(),
                type = type.orEmpty(),
                description = description
            )
            val entity = locationMapper.toEntity(location)

            viewModelScope.launch(dispatcher.io) {
                locationRepository
                    .insertLocation(entity)

                _locationSaved.emit(true)
            }
        }
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setType(type: String) {
        this.type = type
    }

    fun setDescription(description: String) {
        this.description = description
    }
}