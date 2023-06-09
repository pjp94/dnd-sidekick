package com.pancholi.grabbag.model.filter

data class Filter(
    val name: String,
    val category: Category,
    val field: Field?,
    var isSelected: Boolean = false
) {

    enum class Category {
        NPC,
        SHOP,
        LOCATION,
        ITEM
    }

    enum class Field {
        RACE,
        GENDER,
        CLASS,
        PROFESSION,
        TYPE
    }

}

