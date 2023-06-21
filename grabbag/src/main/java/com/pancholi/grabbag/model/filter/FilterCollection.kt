package com.pancholi.grabbag.model.filter

data class FilterCollection(
    val npcFieldFilters: FieldFilters.NpcFieldFilters,
    val shopFieldFilters: FieldFilters.ShopFieldFilters,
    val locationFieldFilters: FieldFilters.LocationFieldFilters,
    val itemFieldFilters: FieldFilters.ItemFieldFilters
)

sealed class FieldFilters {

    data class NpcFieldFilters(
        val races: List<Filter>,
        val genders: List<Filter>,
        val classes: List<Filter>,
        val professions: List<Filter>
    ) : FieldFilters()

    data class ShopFieldFilters(
        val types: List<Filter>
    ) : FieldFilters()

    data class LocationFieldFilters(
        val types: List<Filter>
    ) : FieldFilters()

    data class ItemFieldFilters(
        val types: List<Filter>
    ) : FieldFilters()
}