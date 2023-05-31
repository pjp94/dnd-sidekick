package com.pancholi.grabbag.model

data class Item(
    val id: Int,
    val name: String,
    val type: String,
    val cost: Int?,
    val description: String?,
    val isUsed: Boolean
)
