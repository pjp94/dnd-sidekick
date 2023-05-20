package com.pancholi.grabbag.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pancholi.grabbag.database.dao.LocationDao
import com.pancholi.grabbag.database.dao.NpcDao
import com.pancholi.grabbag.database.dao.ShopDao
import com.pancholi.grabbag.database.entity.LocationEntity
import com.pancholi.grabbag.database.entity.NpcEntity
import com.pancholi.grabbag.database.entity.ShopEntity

@Database(
    entities =
    [
        NpcEntity::class,
        ShopEntity::class,
        LocationEntity::class
   ],
    version = 1,
    exportSchema = false
)
internal abstract class GrabBagDatabase : RoomDatabase() {

    abstract fun npcDao(): NpcDao
    abstract fun shopDao(): ShopDao
    abstract fun locationDao(): LocationDao
}