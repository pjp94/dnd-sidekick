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
internal interface NpcDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(npc: NpcEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(npcs: List<NpcEntity>)

    @Update
    suspend fun update(npc: NpcEntity): Int

    @Delete
    suspend fun delete(npc: NpcEntity): Int

    @Query("SELECT * FROM npc")
    fun getAll(): Flow<List<NpcEntity>>
}