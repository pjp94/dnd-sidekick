package com.pancholi.grabbag.mapper

import android.content.res.Resources
import com.google.gson.Gson
import com.pancholi.grabbag.R
import com.pancholi.grabbag.database.entity.LocationEntity
import com.pancholi.grabbag.model.Location
import javax.inject.Inject

class LocationMapper @Inject constructor(
    private val gson: Gson,
    private val resources: Resources
) {

    fun fromEntity(entity: LocationEntity): Location {
        return Location(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            description = entity.description ?: resources.getString(R.string.unspecified),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(location: Location): LocationEntity {
        return LocationEntity(
            id = location.id,
            name = location.name,
            type = location.type,
            description = requireString(location.description),
            isUsed = location.isUsed
        )
    }

    fun fromJson(json: String): LocationEntity {
        TODO()
    }

    private fun requireString(value: String): String {
        return value.ifBlank { resources.getString(R.string.unspecified) }
    }
}