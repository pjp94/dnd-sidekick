package com.pancholi.grabbag.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pancholi.grabbag.database.entity.ShopEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShopDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shop: ShopEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shops: List<ShopEntity>)

    @Update
    suspend fun update(shop: ShopEntity): Int

    @Delete
    suspend fun delete(shop: ShopEntity): Int

    @Query("SELECT * FROM shop WHERE id = :id")
    fun getById(id: Int): Flow<ShopEntity>

    @Query("SELECT * FROM shop")
    fun getAll(): Flow<List<ShopEntity>>

    @Query("SELECT * FROM shop WHERE is_used = 0")
    fun getAllUnused(): Flow<List<ShopEntity>>

    @Query("SELECT type FROM shop WHERE type IS NOT NULL")
    fun getAllTypes(): Flow<List<String>>
}