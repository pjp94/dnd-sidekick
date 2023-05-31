package com.pancholi.grabbag.repository

import com.pancholi.grabbag.database.GrabBagDatabase
import com.pancholi.grabbag.database.entity.ItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val database: GrabBagDatabase
) {

    suspend fun insertItem(item: ItemEntity) {
        database
            .itemDao()
            .insert(item)
    }

    suspend fun insertAllItems(items: List<ItemEntity>) {
        database
            .itemDao()
            .insertAll(items)
    }

    suspend fun updateItem(item: ItemEntity): Int {
        return database
            .itemDao()
            .update(item)
    }

    suspend fun deleteItem(item: ItemEntity): Int {
        return database
            .itemDao()
            .delete(item)
    }

    fun getAllItems(): Flow<List<ItemEntity>> {
        return database
            .itemDao()
            .getAll()
    }
}