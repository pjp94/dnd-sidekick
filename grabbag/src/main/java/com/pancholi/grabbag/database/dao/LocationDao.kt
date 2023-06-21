package com.pancholi.grabbag.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pancholi.grabbag.database.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: LocationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<LocationEntity>)

    @Update
    suspend fun update(location: LocationEntity): Int

    @Delete
    suspend fun delete(location: LocationEntity): Int

    @Query("SELECT * FROM location WHERE id = :id")
    fun getById(id: Int): Flow<LocationEntity>

    @Query("SELECT * FROM location")
    fun getAll(): Flow<List<LocationEntity>>

    @Query("SELECT type FROM location WHERE type IS NOT NULL")
    fun getAllTypes(): Flow<List<String>>
}