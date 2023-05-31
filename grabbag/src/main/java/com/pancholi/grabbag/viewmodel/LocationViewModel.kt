package com.pancholi.grabbag.viewmodel

import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.mapper.LocationMapper
import com.pancholi.grabbag.repository.LocationRepository
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
}