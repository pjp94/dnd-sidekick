package com.pancholi.grabbag.model

data class Shop(
    val id: Int,
    val name: String,
    val type: String,
    val npcId: Int?,
    val description: String?,
    val isUsed: Boolean
)