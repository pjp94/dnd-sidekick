package com.pancholi.grabbag.model

sealed class CategoryModel(
    open val id: Int
) {

    abstract fun isDrafted(): Boolean

    data class Npc(
        override val id: Int = 0,
        val name: String,
        val race: String,
        val gender: String,
        val clss: String,
        val profession: String,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel(id) {

        override fun isDrafted(): Boolean {
            return name.isNotEmpty()
                    || race.isNotEmpty()
                    || gender.isNotEmpty()
                    || clss.isNotEmpty()
                    || profession.isNotEmpty()
                    || description.isNotEmpty()
        }
    }

    data class Shop(
        override val id: Int = 0,
        val name: String,
        val type: String,
        val owner: String,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel(id) {

        override fun isDrafted(): Boolean {
            return name.isNotEmpty()
                    || type.isNotEmpty()
                    || owner.isNotEmpty()
                    || description.isNotEmpty()
        }
    }

    data class Location(
        override val id: Int = 0,
        val name: String,
        val type: String,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel(id) {

        override fun isDrafted(): Boolean {
            return name.isNotEmpty()
                    || type.isNotEmpty()
                    || description.isNotEmpty()
        }
    }

    data class Item(
        override val id: Int = 0,
        val name: String,
        val type: String,
        val cost: String,
        val currency: Currency?,
        val description: String,
        val isUsed: Boolean = false
    ) : CategoryModel(id) {

        override fun isDrafted(): Boolean {
            return name.isNotEmpty()
                    || type.isNotEmpty()
                    || cost.isNotEmpty()
                    || currency != Currency.GP
                    || description.isNotEmpty()
        }
    }
}