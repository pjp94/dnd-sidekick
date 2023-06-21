package com.pancholi.grabbag.model.filter

data class CategoryFilter(
    val name: String,
    val type: Filter.Category,
    val isSelected: Boolean = false
)