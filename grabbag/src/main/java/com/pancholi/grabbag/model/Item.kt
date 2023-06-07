package com.pancholi.grabbag.model

data class Item(
    val id: Int = 0,
    val name: String,
    val type: String,
    val cost: String,
    val description: String,
    val isUsed: Boolean = false
) : CategoryModel()