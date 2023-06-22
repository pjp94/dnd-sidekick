package com.pancholi.grabbag.model.filter

data class FilterCollection(
    val npcFieldFilters: FieldFilters.NpcFieldFilters,
    val shopFieldFilters: FieldFilters.ShopFieldFilters,
    val locationFieldFilters: FieldFilters.LocationFieldFilters,
    val itemFieldFilters: FieldFilters.ItemFieldFilters
)

sealed class FieldFilters : FilterUpdater {

    data class NpcFieldFilters(
        val races: List<Filter>,
        val genders: List<Filter>,
        val classes: List<Filter>,
        val professions: List<Filter>
    ) : FieldFilters() {

        override fun updateFilter(
            filter: Filter,
            isSelected: Boolean
        ): FieldFilters {
            return when (filter.field) {
                Filter.Field.RACE -> {
                    val updated = races
                        .toMutableList()
                        .apply { find { it.name == filter.name }?.isSelected = isSelected }

                    this.copy(races = updated)
                }
                Filter.Field.GENDER -> {
                    val updated = genders
                        .toMutableList()
                        .apply { find { it.name == filter.name }?.isSelected = isSelected }

                    this.copy(genders = updated)
                }
                Filter.Field.CLASS -> {
                    val updated = classes
                        .toMutableList()
                        .apply { find { it.name == filter.name }?.isSelected = isSelected }

                    this.copy(classes = updated)
                }
                Filter.Field.PROFESSION -> {
                    val updated = professions
                        .toMutableList()
                        .apply { find { it.name == filter.name }?.isSelected = isSelected }

                    this.copy(professions = updated)
                }
                else -> this
            }
        }

        override fun resetFilters() {
            races.forEach { it.isSelected = false }
            genders.forEach { it.isSelected = false }
            classes.forEach { it.isSelected = false }
            professions.forEach { it.isSelected = false }
        }
    }

    data class ShopFieldFilters(
        val types: List<Filter>
    ) : FieldFilters() {

        override fun updateFilter(filter: Filter, isSelected: Boolean): FieldFilters {
            return when (filter.field) {
                Filter.Field.TYPE -> {
                    val updated = types
                        .toMutableList()
                        .apply { find { it.name == filter.name }?.isSelected = isSelected }

                    this.copy(types = updated)
                }
                else -> this
            }
        }

        override fun resetFilters() {
            types.forEach { it.isSelected = false }
        }
    }

    data class LocationFieldFilters(
        val types: List<Filter>
    ) : FieldFilters() {

        override fun updateFilter(filter: Filter, isSelected: Boolean): FieldFilters {
            return when (filter.field) {
                Filter.Field.TYPE -> {
                    val updated = types
                        .toMutableList()
                        .apply { find { it.name == filter.name }?.isSelected = isSelected }

                    this.copy(types = updated)
                }
                else -> this
            }
        }

        override fun resetFilters() {
            types.forEach { it.isSelected = false }
        }
    }

    data class ItemFieldFilters(
        val types: List<Filter>
    ) : FieldFilters() {

        override fun updateFilter(filter: Filter, isSelected: Boolean): FieldFilters {
            return when (filter.field) {
                Filter.Field.TYPE -> {
                    val updated = types
                        .toMutableList()
                        .apply { find { it.name == filter.name }?.isSelected = isSelected }

                    this.copy(types = updated)
                }
                else -> this
            }
        }

        override fun resetFilters() {
            types.forEach { it.isSelected = false }
        }
    }
}

private interface FilterUpdater {

    fun updateFilter(
        filter: Filter,
        isSelected: Boolean
    ): FieldFilters

    fun resetFilters()
}