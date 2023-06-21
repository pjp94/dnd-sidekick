package com.pancholi.grabbag.importcontent

import com.pancholi.grabbag.mapper.LocationMapper
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.repository.LocationRepository
import javax.inject.Inject

class ImportLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val locationMapper: LocationMapper
) : ContentImporter {

    override suspend operator fun invoke(models: List<ImportedModel>?) {
        models?.let {
            val locations = models.map { locationMapper.toEntity(it as ImportedModel.ImportedLocation) }
            locationRepository.insertAllLocations(locations)
        }
    }
}