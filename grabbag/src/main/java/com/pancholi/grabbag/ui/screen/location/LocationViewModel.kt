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
import com.pancholi.grabbag.repository.LocationRepository
import com.pancholi.grabbag.ui.screen.category.CategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(dispatcher.io) {
            locationRepository
                .getAllLocations()
                .distinctUntilChanged()
                .catch { onResultChanged(Result.Error(it)) }
                .collect { entities ->
                    val result = if (entities.isNotEmpty()) {
                        val locations = entities.map { locationMapper.fromEntity(it) }
                        updateModelForDialog(locations)

                        Result.Success(locations)
                    } else {
                        Result.Error(EmptyDatabaseException())
                    }

                    onResultChanged(result)
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