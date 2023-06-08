package com.pancholi.grabbag.mapper

import android.content.res.Resources
import com.pancholi.grabbag.R
import com.pancholi.grabbag.database.entity.NpcEntity
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.requireString
import javax.inject.Inject

class NpcMapper @Inject constructor(
    private val resources: Resources
) {

    fun fromEntity(entity: NpcEntity): CategoryModel.Npc {
        return CategoryModel.Npc(
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

    fun toEntity(npc: CategoryModel.Npc): NpcEntity {
        return NpcEntity(
            id = npc.id,
            name = npc.name,
            race = npc.race,
            gender = npc.gender,
            clss = npc.clss.requireString { resources.getString(R.string.unspecified) },
            profession = npc.profession.requireString { resources.getString(R.string.unspecified) },
            description = npc.description.requireString { resources.getString(R.string.unspecified) },
            isUsed = npc.isUsed
        )
    }

    fun toEntity(importedNpc: ImportedModel.ImportedNpc): NpcEntity {
        return NpcEntity(
            name = importedNpc.name,
            race = importedNpc.race,
            gender = importedNpc.gender,
            clss = importedNpc.clss.requireString { resources.getString(R.string.unspecified) },
            profession = importedNpc.profession.requireString { resources.getString(R.string.unspecified) },
            description = importedNpc.description.requireString { resources.getString(R.string.unspecified) }
        )
    }
}