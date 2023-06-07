package com.pancholi.grabbag.model

data class Npc(
    val id: Int = 0,
    val name: String,
    val race: String,
    val gender: String,
    val clss: String,
    val profession: String,
    val description: String,
    val isUsed: Boolean = false
) : CategoryModel()