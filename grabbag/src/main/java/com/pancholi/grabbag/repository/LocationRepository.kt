package com.pancholi.grabbag.repository

import com.pancholi.grabbag.database.GrabBagDatabase
import com.pancholi.grabbag.database.entity.LocationEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val database: GrabBagDatabase
) {

    suspend fun insertLocation(location: LocationEntity) {
        database
            .locationDao()
            .insert(location)
    }

    suspend fun insertAllLocations(locations: List<LocationEntity>) {
        database
            .locationDao()
            .insertAll(locations)
    }

    suspend fun updateLocation(location: LocationEntity): Int {
        return database
            .locationDao()
            .update(location)
    }

    suspend fun deleteLocation(location: LocationEntity): Int {
        return database
            .locationDao()
            .delete(location)
    }

    fun getById(id: Int): Flow<LocationEntity> {
        return database
            .locationDao()
            .getById(id)
    }

    fun getAllLocations(): Flow<List<LocationEntity>> {
        return database
            .locationDao()
            .getAll()
    }

    fun getAllTypes(): Flow<List<String>> {
        return database
            .locationDao()
            .getAllTypes()
    }
}