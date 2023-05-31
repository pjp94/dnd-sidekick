package com.pancholi.grabbag.model

data class Npc(
    val id: Int,
    val name: String,
    val race: String,
    val gender: String,
    val clss: String,
    val type: String,
    val description: String?,
    val isUsed: Boolean
)