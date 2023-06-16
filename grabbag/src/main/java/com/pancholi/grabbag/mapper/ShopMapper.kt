package com.pancholi.grabbag.mapper

import android.content.res.Resources
import com.pancholi.grabbag.R
import com.pancholi.grabbag.database.entity.ShopEntity
import com.pancholi.grabbag.getOrEmptyForEdit
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.requireString
import javax.inject.Inject

class ShopMapper @Inject constructor(
    private val resources: Resources
) {

    fun fromEntity(entity: ShopEntity): CategoryModel.Shop {
        return CategoryModel.Shop(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            owner = entity.owner ?: resources.getString(R.string.unspecified),
            description = entity.description ?: resources.getString(R.string.unspecified),
            isUsed = entity.isUsed
        )
    }

    fun fromEntityToEdit(entity: ShopEntity): CategoryModel.Shop {
        val unspecified = resources.getString(R.string.unspecified)

        return CategoryModel.Shop(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            owner = entity.owner.getOrEmptyForEdit(unspecified),
            description = entity.description.getOrEmptyForEdit(unspecified),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(shop: CategoryModel.Shop): ShopEntity {
        return ShopEntity(
            id = shop.id,
            name = shop.name,
            type = shop.type,
            owner = shop.owner.requireString { resources.getString(R.string.unspecified) },
            description = shop.description.requireString { resources.getString(R.string.unspecified) },
            isUsed = shop.isUsed
        )
    }

    fun toEntity(importedShop: ImportedModel.ImportedShop): ShopEntity {
        return ShopEntity(
            name = importedShop.name,
            type = importedShop.type,
            owner = importedShop.owner.requireString { resources.getString(R.string.unspecified) },
            description = importedShop.description.requireString { resources.getString(R.string.unspecified) }
        )
    }
}