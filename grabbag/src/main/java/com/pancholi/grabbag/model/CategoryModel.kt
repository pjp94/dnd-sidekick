package com.pancholi.grabbag.model

sealed class CategoryModel {

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

    data class Shop(
        val id: Int = 0,
        val name: String,
        val type: String,
        val owner: String,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel()

    data class Location(
        val id: Int = 0,
        val name: String,
        val type: String,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel()

    data class Item(
        val id: Int = 0,
        val name: String,
        val type: String,
        val cost: String,
        val currency: Currency?,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel()
}