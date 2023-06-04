package com.pancholi.grabbag.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "npc")
data class NpcEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "race")
    val race: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "class")
    val clss: String? = null,
    @ColumnInfo(name = "profession")
    val profession: String? = null,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "is_used")
    val isUsed: Boolean = false
)