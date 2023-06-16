package com.pancholi.grabbag.mapper

import android.content.res.Resources
import com.pancholi.grabbag.R
import com.pancholi.grabbag.database.entity.ItemEntity
import com.pancholi.grabbag.getOrEmptyForEdit
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.requireString
import javax.inject.Inject

class ItemMapper @Inject constructor(
    private val resources: Resources
) {

    fun fromEntity(entity: ItemEntity): CategoryModel.Item {
        return CategoryModel.Item(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            cost = entity.cost ?: resources.getString(R.string.unspecified),
            currency = entity.currency,
            description = entity.description ?: resources.getString(R.string.unspecified),
            isUsed = entity.isUsed
        )
    }

    fun fromEntityToEdit(entity: ItemEntity): CategoryModel.Item {
        val unspecified = resources.getString(R.string.unspecified)

        return CategoryModel.Item(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            cost = entity.cost.getOrEmptyForEdit(unspecified),
            currency = entity.currency,
            description = entity.description.getOrEmptyForEdit(unspecified),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(item: CategoryModel.Item): ItemEntity {
        return ItemEntity(
            id = item.id,
            name = item.name,
            type = item.type,
            cost = item.cost.requireString { resources.getString(R.string.unspecified) },
            currency = if (item.cost.isNotBlank()) item.currency else null,
            description = item.description.requireString { resources.getString(R.string.unspecified) },
            isUsed = item.isUsed
        )
    }

    fun toEntity(importedItem: ImportedModel.ImportedItem): ItemEntity {
        return ItemEntity(
            name = importedItem.name,
            type = importedItem.type,
            cost = importedItem.cost,
            currency = if (importedItem.cost?.isNotBlank() == true) importedItem.currency else null,
            description = importedItem.description.requireString { resources.getString(R.string.unspecified) },
        )
    }
}