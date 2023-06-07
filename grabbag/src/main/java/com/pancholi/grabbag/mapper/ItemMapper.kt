package com.pancholi.grabbag.mapper

import android.content.res.Resources
import com.google.gson.Gson
import com.pancholi.grabbag.R
import com.pancholi.grabbag.database.entity.ItemEntity
import com.pancholi.grabbag.model.Item
import javax.inject.Inject

class ItemMapper @Inject constructor(
    private val gson: Gson,
    private val resources: Resources
) {

    fun fromEntity(entity: ItemEntity): Item {
        return Item(
            id = entity.id,
            name = entity.name,
            type = entity.type,
            cost = entity.cost ?: resources.getString(R.string.unspecified),
            description = entity.description ?: resources.getString(R.string.unspecified),
            isUsed = entity.isUsed
        )
    }

    fun toEntity(item: Item): ItemEntity {
        return ItemEntity(
            id = item.id,
            name = item.name,
            type = item.type,
            cost = requireString(item.cost),
            description = requireString(item.description),
            isUsed = item.isUsed
        )
    }

    fun fromJson(json: String): ItemEntity {
        TODO()
    }

    private fun requireString(value: String): String {
        return value.ifBlank { resources.getString(R.string.unspecified) }
    }
}