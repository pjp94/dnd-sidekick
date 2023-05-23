package com.pancholi.grabbag.mapper

import com.google.gson.Gson
import com.pancholi.grabbag.database.entity.NpcEntity
import com.pancholi.grabbag.model.Npc
import javax.inject.Inject

internal class NpcMapper @Inject constructor(
    private val gson: Gson
) {

    fun fromEntity(entity: NpcEntity): Npc {
        return Npc(
            name = entity.name,
            race = entity.race,
            gender = entity.gender,
            clss = entity.clss,
            type = entity.type,
            description = entity.description,
            isAssigned = entity.isAssigned
        )
    }

    fun toEntity(npc: Npc): NpcEntity {
        return NpcEntity(
            name = npc.name,
            race = npc.race,
            gender = npc.gender,
            clss = npc.clss,
            type = npc.type,
            description = npc.description,
            isAssigned = npc.isAssigned
        )
    }


}