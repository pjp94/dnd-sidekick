package com.pancholi.grabbag.mapper

import com.pancholi.grabbag.database.entity.ShopEntity
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import javax.inject.Inject

class ShopMapper @Inject constructor() {

    fun fromEntity(entity: ShopEntity): CategoryModel.Shop {
        return CategoryModel.Shop(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            owner = entity.owner.orEmpty(),
            description = entity.description.orEmpty(),
            isUsed = entity.isUsed
        )
    }

    fun fromEntityToEdit(entity: ShopEntity): CategoryModel.Shop {
        return CategoryModel.Shop(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            owner = entity.owner.orEmpty(),
            description = entity.description.orEmpty(),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(shop: CategoryModel.Shop): ShopEntity {
        return ShopEntity(
            id = shop.id,
            name = shop.name,
            type = shop.type,
            owner = shop.owner,
            description = shop.description,
            isUsed = shop.isUsed
        )
    }

    fun toEntity(importedShop: ImportedModel.ImportedShop): ShopEntity {
        return ShopEntity(
            name = importedShop.name,
            type = importedShop.type,
            owner = importedShop.owner,
            description = importedShop.description
        )
    }
}