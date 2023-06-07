package com.pancholi.grabbag.mapper

import android.content.res.Resources
import com.google.gson.Gson
import com.pancholi.grabbag.R
import com.pancholi.grabbag.database.entity.ShopEntity
import com.pancholi.grabbag.model.Shop
import javax.inject.Inject

class ShopMapper @Inject constructor(
    private val gson: Gson,
    private val resources: Resources
) {

    fun fromEntity(
        entity: ShopEntity
    ): Shop {
        return Shop(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            owner = entity.owner ?: resources.getString(R.string.unspecified),
            description = entity.description ?: resources.getString(R.string.unspecified),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(
        shop: Shop,
    ): ShopEntity {
        return ShopEntity(
            name = shop.name,
            type = shop.type,
            owner = requireString(shop.owner),
            description = requireString(shop.description),
            isUsed = shop.isUsed
        )
    }

    fun fromJson(json: String): ShopEntity {
        TODO()
    }

    private fun requireString(value: String): String {
        return value.ifBlank { resources.getString(R.string.unspecified) }
    }
}