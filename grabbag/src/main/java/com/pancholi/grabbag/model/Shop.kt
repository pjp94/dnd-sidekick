package com.pancholi.grabbag.model

data class Shop(
    val id: Int = 0,
    val name: String,
    val type: String,
    val owner: String,
    val description: String,
    val isUsed: Boolean = false
) : CategoryModel()