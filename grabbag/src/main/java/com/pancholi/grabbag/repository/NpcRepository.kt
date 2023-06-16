package com.pancholi.grabbag.repository

import com.pancholi.grabbag.database.GrabBagDatabase
import com.pancholi.grabbag.database.entity.NpcEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NpcRepository @Inject constructor(
    private val database: GrabBagDatabase
) {

    suspend fun insertNpc(npc: NpcEntity) {
        database
            .npcDao()
            .insert(npc)
    }

    suspend fun insertAllNpcs(npcs: List<NpcEntity>) {
        database
            .npcDao()
            .insertAll(npcs)
    }

    suspend fun updateNpc(npc: NpcEntity): Int {
        return database
            .npcDao()
            .update(npc)
    }

    suspend fun deleteNpc(npc: NpcEntity): Int {
        return database
            .npcDao()
            .delete(npc)
    }

    fun getById(id: Int): Flow<NpcEntity> {
        return database
            .npcDao()
            .getById(id)
    }

    fun getAllNpcs(): Flow<List<NpcEntity>> {
        return database
            .npcDao()
            .getAll()
    }
}