package com.pancholi.grabbag.mapper

import com.google.gson.Gson
import com.pancholi.grabbag.database.entity.ShopEntity
import com.pancholi.grabbag.model.Shop
import javax.inject.Inject

class ShopMapper @Inject constructor(
    private val gson: Gson
) {

    fun fromEntity(entity: ShopEntity): Shop {
        return Shop(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            npcId = entity.npcId,
            description = entity.description,
            isUsed = entity.isUsed
        )
    }

    fun toEntity(shop: Shop): ShopEntity {
        return ShopEntity(
            name = shop.name,
            type = shop.type,
            npcId = shop.npcId,
            description = shop.description,
            isUsed = shop.isUsed
        )
    }

    fun fromJson(json: String): ShopEntity {
        TODO()
    }
}