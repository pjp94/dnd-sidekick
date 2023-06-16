package com.pancholi.grabbag.model

sealed class CategoryModel(
    open val id: Int
) {

    data class Npc(
        override val id: Int = 0,
        val name: String,
        val race: String,
        val gender: String,
        val clss: String,
        val profession: String,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel(id)

    data class Shop(
        override val id: Int = 0,
        val name: String,
        val type: String,
        val owner: String,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel(id)

    data class Location(
        override val id: Int = 0,
        val name: String,
        val type: String,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel(id)

    data class Item(
        override val id: Int = 0,
        val name: String,
        val type: String,
        val cost: String,
        val currency: Currency?,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel(id)
}