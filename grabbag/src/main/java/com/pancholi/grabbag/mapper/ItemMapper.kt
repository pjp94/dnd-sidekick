package com.pancholi.grabbag.mapper

import com.pancholi.grabbag.database.entity.ItemEntity
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import javax.inject.Inject

class ItemMapper @Inject constructor() {

    fun fromEntity(entity: ItemEntity): CategoryModel.Item {
        return CategoryModel.Item(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            cost = entity.cost.orEmpty(),
            currency = entity.currency,
            description = entity.description.orEmpty(),
            isUsed = entity.isUsed
        )
    }

    fun fromEntityToEdit(entity: ItemEntity): CategoryModel.Item {
        return CategoryModel.Item(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            cost = entity.cost.orEmpty(),
            currency = entity.currency,
            description = entity.description.orEmpty(),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(item: CategoryModel.Item): ItemEntity {
        return ItemEntity(
            id = item.id,
            name = item.name,
            type = item.type,
            cost = item.cost,
            currency = if (item.cost.isNotBlank()) item.currency else null,
            description = item.description,
            isUsed = item.isUsed
        )
    }

    fun toEntity(importedItem: ImportedModel.ImportedItem): ItemEntity {
        return ItemEntity(
            name = importedItem.name,
            type = importedItem.type,
            cost = importedItem.cost,
            currency = if (importedItem.cost?.isNotBlank() == true) importedItem.currency else null,
            description = importedItem.description,
        )
    }
}