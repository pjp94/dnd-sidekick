package com.pancholi.grabbag.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "location",
    indices = [
        Index(
            value = [
                "name",
                "type"
            ],
            unique = true
        )
    ]
)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "is_used")
    val isUsed: Boolean = false
)