package com.pancholi.grabbag.model

internal data class Npc(
    val name: String,
    val race: String,
    val gender: String,
    val clss: String,
    val type: String,
    val description: String?,
    val isAssigned: Boolean
)