package com.pancholi.grabbag.model

data class Item(
    val id: Int = 0,
    val name: String,
    val type: String,
    val cost: String?,
    val currency: Currency?,
    val description: String?,
    val isUsed: Boolean = false
) : CategoryModel() {

    fun getCurrency(): String? {
        return currency?.name?.lowercase()
    }
}