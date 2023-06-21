package com.pancholi.grabbag.repository

import com.pancholi.grabbag.database.GrabBagDatabase
import com.pancholi.grabbag.database.entity.ShopEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShopRepository @Inject constructor(
    private val database: GrabBagDatabase
) {

    suspend fun insertShop(shop: ShopEntity) {
        database
            .shopDao()
            .insert(shop)
    }

    suspend fun insertAllShops(shops: List<ShopEntity>) {
        database
            .shopDao()
            .insertAll(shops)
    }

    suspend fun updateShop(shop: ShopEntity): Int {
        return database
            .shopDao()
            .update(shop)
    }

    suspend fun deleteShop(shop: ShopEntity): Int {
        return database
            .shopDao()
            .delete(shop)
    }

    fun getById(id: Int): Flow<ShopEntity> {
        return database
            .shopDao()
            .getById(id)
    }

    fun getAllShops(): Flow<List<ShopEntity>> {
        return database
            .shopDao()
            .getAll()
    }

    fun getAllNpcNames(): Flow<List<String>> {
        return database
            .npcDao()
            .getAllNames()
    }

    fun getAllTypes(): Flow<List<String>> {
        return database
            .shopDao()
            .getAllTypes()
    }
}