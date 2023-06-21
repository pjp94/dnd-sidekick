@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.pancholi.grabbag.ui.screen.suggest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.core.Result
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.filter.CategoryFilter
import com.pancholi.grabbag.model.filter.FieldFilters
import com.pancholi.grabbag.model.filter.Filter
import com.pancholi.grabbag.ui.BackableScreen
import com.pancholi.grabbag.ui.LoadingIndicator

@Composable
fun SuggestScreen(
    onBackPressed: () -> Unit,
    viewModel: SuggestViewModel = hiltViewModel()
) {
    BackableScreen(
        title = stringResource(id = R.string.suggest_title),
        backSingleClick = true,
        onBackPressed = onBackPressed
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            val viewState = viewModel.viewState.collectAsStateWithLifecycle()

            when (val filters = viewState.value.filters) {
                is Result.Loading -> LoadingIndicator()
                is Result.Success<*> -> {
                    SuggestBody(
                        categories = viewState.value.categories,
                        filters = filters.value as? FieldFilters,
                        onCategorySelected = { category, isSelected ->
                            viewModel.onCategoryClicked(
                                category = category,
                                isSelected = isSelected
                            )
                        },
                        onFilterSelected = { viewModel.onFilterSelected(it) }
                    )
                }
                is Result.Error -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuggestBody(
    categories: List<CategoryFilter>,
    filters: FieldFilters?,
    onCategorySelected: (CategoryFilter, Boolean) -> Unit,
    onFilterSelected: (Filter) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.category),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            categories.forEach {
                var selected by rememberSaveable { mutableStateOf(false) }

                FilterChip(
                    selected = selected,
                    onClick = {
                        selected = !selected
                        onCategorySelected(it, selected)
                    },
                    label = { Text(text = it.name) }
                )
            }
        }

        filters?.let {
            val selectedCategory = categories.find { it.isSelected }

            when (selectedCategory?.type) {
                Filter.Category.NPC -> NpcFilters(
                    filters = filters as FieldFilters.NpcFieldFilters,
                    onFilterSelected = onFilterSelected
                )

                Filter.Category.SHOP -> ShopFilters(
                    filters = filters as FieldFilters.ShopFieldFilters,
                    onFilterSelected = onFilterSelected
                )

                Filter.Category.LOCATION -> LocationFilters(
                    filters = filters as FieldFilters.LocationFieldFilters,
                    onFilterSelected = onFilterSelected
                )

                Filter.Category.ITEM -> ItemFilters(
                    filters = filters as FieldFilters.ItemFieldFilters,
                    onFilterSelected = onFilterSelected
                )

                else -> Unit
            }
        }
    }
}

@Composable
private fun NpcFilters(
    filters: FieldFilters.NpcFieldFilters,
    onFilterSelected: (Filter) -> Unit
) {
    FilterFlowGroup(
        header = stringResource(id = R.string.race),
        filters = filters.races,
        onFilterSelected = onFilterSelected
    )

    FilterFlowGroup(
        header = stringResource(id = R.string.gender),
        filters = filters.genders,
        onFilterSelected = onFilterSelected
    )

    FilterFlowGroup(
        header = stringResource(id = R.string.clss),
        filters = filters.classes,
        onFilterSelected = onFilterSelected
    )

    FilterFlowGroup(
        header = stringResource(id = R.string.profession),
        filters = filters.professions,
        onFilterSelected = onFilterSelected
    )
}

@Composable
private fun ShopFilters(
    filters: FieldFilters.ShopFieldFilters,
    onFilterSelected: (Filter) -> Unit
) {
    FilterFlowGroup(
        header = stringResource(id = R.string.type),
        filters = filters.types,
        onFilterSelected = onFilterSelected
    )
}

@Composable
private fun LocationFilters(
    filters: FieldFilters.LocationFieldFilters,
    onFilterSelected: (Filter) -> Unit
) {
    FilterFlowGroup(
        header = stringResource(id = R.string.type),
        filters = filters.types,
        onFilterSelected = onFilterSelected
    )
}

@Composable
private fun ItemFilters(
    filters: FieldFilters.ItemFieldFilters,
    onFilterSelected: (Filter) -> Unit
) {
    FilterFlowGroup(
        header = stringResource(id = R.string.type),
        filters = filters.types,
        onFilterSelected = onFilterSelected
    )
}

@Composable
private fun FilterFlowGroup(
    header: String,
    filters: List<Filter>,
    onFilterSelected: (Filter) -> Unit
) {
    Text(
        text = header,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(start = 8.dp)
    ) {
        filters.forEach { filter ->
            var selected by rememberSaveable { mutableStateOf(false) }

            FilterChip(
                selected = selected,
                onClick = {
                    selected = !selected
                    onFilterSelected(filter)
                },
                label = { Text(text = filter.name) },
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
}