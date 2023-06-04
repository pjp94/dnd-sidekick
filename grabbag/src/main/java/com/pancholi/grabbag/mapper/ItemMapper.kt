package com.pancholi.grabbag.mapper

import com.google.gson.Gson
import com.pancholi.grabbag.database.entity.ItemEntity
import com.pancholi.grabbag.model.Item
import javax.inject.Inject

class ItemMapper @Inject constructor(
    private val gson: Gson
) {

    fun fromEntity(entity: ItemEntity): Item {
        return Item(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            cost = entity.cost,
            currency = entity.currency,
            description = entity.description,
            isUsed = entity.isUsed
        )
    }

    fun toEntity(item: Item): ItemEntity {
        return ItemEntity(
            id = item.id,
            name = item.name,
            type = item.type,
            cost = item.cost,
            currency = item.currency,
            description = item.description,
            isUsed = item.isUsed
        )
    }

    fun fromJson(json: String): ItemEntity {
        TODO()
    }
}