package com.pancholi.grabbag.mapper

import android.content.res.Resources
import com.pancholi.grabbag.R
import com.pancholi.grabbag.database.entity.LocationEntity
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.requireString
import javax.inject.Inject

class LocationMapper @Inject constructor(
    private val resources: Resources
) {

    fun fromEntity(entity: LocationEntity): CategoryModel.Location {
        return CategoryModel.Location(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            description = entity.description ?: resources.getString(R.string.unspecified),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(location: CategoryModel.Location): LocationEntity {
        return LocationEntity(
            id = location.id,
            name = location.name,
            type = location.type,
            description = location.description.requireString { resources.getString(R.string.unspecified) },
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