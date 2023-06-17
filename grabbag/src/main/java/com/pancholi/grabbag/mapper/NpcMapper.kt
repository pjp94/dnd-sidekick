package com.pancholi.grabbag.mapper

import com.pancholi.grabbag.database.entity.NpcEntity
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import javax.inject.Inject

class NpcMapper @Inject constructor() {

    fun fromEntity(entity: NpcEntity): CategoryModel.Npc {
        return CategoryModel.Npc(
            id = entity.id,
            name = entity.name,
            race = entity.race,
            gender = entity.gender,
            clss = entity.clss.orEmpty(),
            profession = entity.profession.orEmpty(),
            description = entity.description.orEmpty(),
            isUsed = entity.isUsed
        )
    }

    fun fromEntityToEdit(entity: NpcEntity): CategoryModel.Npc {
        return CategoryModel.Npc(
            id = entity.id,
            name = entity.name,
            race = entity.race,
            gender = entity.gender,
            clss = entity.clss.orEmpty(),
            profession = entity.profession.orEmpty(),
            description = entity.description.orEmpty(),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(npc: CategoryModel.Npc): NpcEntity {
        return NpcEntity(
            id = npc.id,
            name = npc.name,
            race = npc.race,
            gender = npc.gender,
            clss = npc.clss,
            profession = npc.profession,
            description = npc.description,
            isUsed = npc.isUsed
        )
    }

    fun toEntity(importedNpc: ImportedModel.ImportedNpc): NpcEntity {
        return NpcEntity(
            name = importedNpc.name,
            race = importedNpc.race,
            gender = importedNpc.gender,
            clss = importedNpc.clss,
            profession = importedNpc.profession,
            description = importedNpc.description
        )
    }
}