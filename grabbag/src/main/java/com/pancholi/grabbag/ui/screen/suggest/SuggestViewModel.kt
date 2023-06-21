package com.pancholi.grabbag.ui.screen.suggest

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.filter.CategoryFilter
import com.pancholi.grabbag.model.filter.FieldFilters.ItemFieldFilters
import com.pancholi.grabbag.model.filter.FieldFilters.LocationFieldFilters
import com.pancholi.grabbag.model.filter.FieldFilters.NpcFieldFilters
import com.pancholi.grabbag.model.filter.FieldFilters.ShopFieldFilters
import com.pancholi.grabbag.model.filter.Filter
import com.pancholi.grabbag.model.filter.FilterCollection
import com.pancholi.grabbag.repository.ItemRepository
import com.pancholi.grabbag.repository.LocationRepository
import com.pancholi.grabbag.repository.NpcRepository
import com.pancholi.grabbag.repository.ShopRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestViewModel @Inject constructor(
    private val npcRepository: NpcRepository,
    private val locationRepository: LocationRepository,
    private val shopRepository: ShopRepository,
    private val itemRepository: ItemRepository,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : ViewModel() {

    data class ViewState(
        val filters: Result = Result.Loading,
        val categories: List<CategoryFilter> = emptyList()
    )

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private lateinit var filterCollection: FilterCollection

    init {
        setCategories()
        getAllFilters()
    }

    fun onCategoryClicked(
        category: CategoryFilter,
        isSelected: Boolean
    ) {
        val updatedCategories = if (isSelected) {
            _viewState.value.categories.map {
                it.copy(isSelected = it == category)
            }
        } else {
            _viewState.value.categories.map {
                it.copy(isSelected = false)
            }
        }

        val filters = when (category.type) {
            Filter.Category.NPC -> filterCollection.npcFieldFilters
            Filter.Category.SHOP -> filterCollection.shopFieldFilters
            Filter.Category.LOCATION -> filterCollection.locationFieldFilters
            Filter.Category.ITEM -> filterCollection.itemFieldFilters
        }

        val result = if (isSelected) {
            Result.Success(filters)
        } else {
            Result.Success(null)
        }

        _viewState.update { it.copy(categories = updatedCategories, filters = result) }
    }

    fun onFilterSelected(filter: Filter) {

    }

    private fun setCategories() {
        val categories = listOf(
            CategoryFilter(
                name = resources.getString(R.string.npc),
                type = Filter.Category.NPC
            ),
            CategoryFilter(
                name = resources.getString(R.string.shop),
                type = Filter.Category.SHOP
            ),
            CategoryFilter(
                name = resources.getString(R.string.location),
                type = Filter.Category.LOCATION
            ),
            CategoryFilter(
                name = resources.getString(R.string.item),
                type = Filter.Category.ITEM
            )
        )

        _viewState.update { it.copy(categories = categories) }
    }

    private fun getAllFilters() {
        viewModelScope.launch(dispatcher.io) {
            combine(
                getAllNpcFilters(),
                getAllShopFilters(),
                getAllLocationFilters(),
                getAllItemFilters()
            ) { npcFilter, shopFilter, locationFilter, itemFilter ->
                FilterCollection(
                    npcFieldFilters = npcFilter,
                    shopFieldFilters = shopFilter,
                    locationFieldFilters = locationFilter,
                    itemFieldFilters = itemFilter
                )
            }.collect { collection ->
                filterCollection = collection
                _viewState.update { it.copy(filters = Result.Success(null)) }
            }
        }
    }

    private fun getAllNpcFilters(): Flow<NpcFieldFilters> {
        with (npcRepository) {
            return combine(
                getAllRaces(),
                getAllGenders(),
                getAllClasses(),
                getAllProfessions()
            ) { races, genders, classes, professions ->
                val defaultRaces = resources.getStringArray(R.array.races).toMutableList()
                val defaultClasses = resources.getStringArray(R.array.classes).toMutableList()
                val defaultProfessions = resources.getStringArray(R.array.professions).toMutableList()

                val allRaces = defaultRaces
                    .apply { addAll(races) }
                    .map {
                        Filter(
                            name = it,
                            category = Filter.Category.NPC,
                            field = Filter.Field.RACE
                        )
                    }
                    .distinct()
                    .sortedBy { it.name }

                val allClasses = defaultClasses
                    .apply { addAll(classes) }
                    .map {
                        Filter(
                            name = it,
                            category = Filter.Category.NPC,
                            field = Filter.Field.CLASS
                        )
                    }
                    .distinct()
                    .sortedBy { it.name }

                val allProfessions = defaultProfessions
                    .apply { addAll(professions) }
                    .map {
                        Filter(
                            name = it,
                            category = Filter.Category.NPC,
                            field = Filter.Field.PROFESSION
                        )
                    }
                    .distinct()
                    .sortedBy { it.name }

                NpcFieldFilters(
                    races = allRaces,
                    genders = genders.map {
                        Filter(
                            name = it,
                            category = Filter.Category.NPC,
                            field = Filter.Field.GENDER
                        )
                    },
                    classes = allClasses,
                    professions = allProfessions
                )
            }
        }
    }

    private fun getAllShopFilters(): Flow<ShopFieldFilters> {
        with (shopRepository) {
            return getAllTypes()
                .map { types ->
                    ShopFieldFilters(
                        types = types.map {
                            Filter(
                                name = it,
                                category = Filter.Category.SHOP,
                                field = Filter.Field.TYPE
                            )
                        }
                    )
                }
        }
    }

    private fun getAllLocationFilters(): Flow<LocationFieldFilters> {
        with (locationRepository) {
            return getAllTypes()
                .map { locations ->
                    LocationFieldFilters(
                        types = locations.map {
                            Filter(
                                name = it,
                                category = Filter.Category.LOCATION,
                                field = Filter.Field.TYPE
                            )
                        }
                    )
                }
        }
    }

    private fun getAllItemFilters(): Flow<ItemFieldFilters> {
        with (itemRepository) {
            return getAllTypes()
                .map { items ->
                    ItemFieldFilters(
                        types = items.map {
                            Filter(
                                name = it,
                                category = Filter.Category.ITEM,
                                field = Filter.Field.TYPE
                            )
                        }
                    )
                }
        }
    }
}