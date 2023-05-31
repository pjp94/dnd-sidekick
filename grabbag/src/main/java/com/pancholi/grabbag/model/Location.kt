package com.pancholi.grabbag.model

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val description: String?,
    val isUsed: Boolean
)
