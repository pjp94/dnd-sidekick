package com.pancholi.grabbag.mapper

import com.pancholi.grabbag.database.entity.LocationEntity
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import javax.inject.Inject

class LocationMapper @Inject constructor() {

    fun fromEntity(entity: LocationEntity): CategoryModel.Location {
        return CategoryModel.Location(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            description = entity.description.orEmpty(),
            isUsed = entity.isUsed
        )
    }

    fun fromEntityToEdit(entity: LocationEntity): CategoryModel.Location {
        return CategoryModel.Location(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            description = entity.description.orEmpty(),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(location: CategoryModel.Location): LocationEntity {
        return LocationEntity(
            id = location.id,
            name = location.name,
            type = location.type,
            description = location.description,
            isUsed = location.isUsed
        )
    }

    fun toEntity(importedLocation: ImportedModel.ImportedLocation): LocationEntity {
        return LocationEntity(
            name = importedLocation.name,
            type = importedLocation.type,
            description = importedLocation.description
        )
    }
}