package com.pancholi.grabbag.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.pancholi.grabbag.model.Currency

@Entity(
    tableName = "item",
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
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "cost")
    val cost: String? = null,
    @ColumnInfo(name = "currency")
    val currency: Currency? = null,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "is_used")
    val isUsed: Boolean = false
)