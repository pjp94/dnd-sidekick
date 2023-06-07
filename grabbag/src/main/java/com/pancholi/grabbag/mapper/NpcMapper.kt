package com.pancholi.grabbag.mapper

import android.content.res.Resources
import com.google.gson.Gson
import com.pancholi.grabbag.R
import com.pancholi.grabbag.database.entity.NpcEntity
import com.pancholi.grabbag.database.entity.ShopEntity
import com.pancholi.grabbag.model.Npc
import javax.inject.Inject

class NpcMapper @Inject constructor(
    private val gson: Gson,
    private val resources: Resources
) {

    fun fromEntity(entity: NpcEntity): Npc {
        return Npc(
            id = entity.id,
            name = entity.name,
            race = entity.race,
            gender = entity.gender,
            clss = entity.clss ?: resources.getString(R.string.unspecified),
            profession = entity.profession ?: resources.getString(R.string.unspecified),
            description = entity.description ?: resources.getString(R.string.unspecified),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(npc: Npc): NpcEntity {
        return NpcEntity(
            name = npc.name,
            race = npc.race,
            gender = npc.gender,
            clss = requireString(npc.clss),
            profession = requireString(npc.profession),
            description = requireString(npc.description),
            isUsed = npc.isUsed
        )
    }

    fun fromJson(json: String): ShopEntity {
       TODO()
    }

    private fun requireString(value: String): String {
        return value.ifBlank { resources.getString(R.string.unspecified) }
    }
}