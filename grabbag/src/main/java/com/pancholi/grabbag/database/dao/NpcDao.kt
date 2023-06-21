package com.pancholi.grabbag.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pancholi.grabbag.database.entity.NpcEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NpcDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(npc: NpcEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(npcs: List<NpcEntity>)

    @Update
    suspend fun update(npc: NpcEntity): Int

    @Delete
    suspend fun delete(npc: NpcEntity): Int

    @Query("SELECT * FROM npc WHERE id = :id")
    fun getById(id: Int): Flow<NpcEntity>

    @Query("SELECT * FROM npc")
    fun getAll(): Flow<List<NpcEntity>>

    @Query("SELECT * FROM npc WHERE is_used = 0")
    fun getAllUnused(): Flow<List<NpcEntity>>

    @Query("SELECT DISTINCT name FROM npc ORDER BY name ASC")
    fun getAllNames(): Flow<List<String>>

    @Query("SELECT DISTINCT race FROM npc WHERE race IS NOT NULL")
    fun getAllRaces(): Flow<List<String>>

    @Query("SELECT DISTINCT gender FROM npc ORDER BY gender ASC")
    fun getAllGenders(): Flow<List<String>>

    @Query("SELECT DISTINCT class FROM npc WHERE class IS NOT NULL")
    fun getAllClasses(): Flow<List<String>>

    @Query("SELECT DISTINCT profession FROM npc WHERE profession IS NOT NULL")
    fun getAllProfessions(): Flow<List<String>>
}