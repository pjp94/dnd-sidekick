@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)

package com.pancholi.grabbag.ui.screen.suggest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
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
import com.pancholi.grabbag.model.filter.FilterCollection
import com.pancholi.grabbag.ui.BackableScreen
import com.pancholi.grabbag.ui.LoadingIndicator
import kotlinx.coroutines.launch

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
                    TabbedCategoryLayout(
                        categories = viewState.value.categories,
                        filters = filters.value as FilterCollection,
                        onCategoryChanged = { viewModel.onCategoryChanged(it) },
                        onFilterSelected = { filter, isSelected ->
                            viewModel.onFilterSelected(
                                filter = filter,
                                isSelected = isSelected
                            )
                        }
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
    onFilterSelected: (Filter, Boolean) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        FilterHeader(text = stringResource(id = R.string.category),)

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
        ) {
            categories.forEach {
                FilterChip(
                    selected = it.isSelected,
                    onClick = { onCategorySelected(it, !it.isSelected) },
                    label = {
                        Text(
                            text = it.name,
                            fontSize = 18.sp
                        )
                    }
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
    onFilterSelected: (Filter, Boolean) -> Unit
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

    UnusedSwitch()
}

@Composable
private fun ShopFilters(
    filters: FieldFilters.ShopFieldFilters,
    onFilterSelected: (Filter, Boolean) -> Unit
) {
    FilterFlowGroup(
        header = stringResource(id = R.string.type),
        filters = filters.types,
        onFilterSelected = onFilterSelected
    )

    UnusedSwitch()
}

@Composable
private fun LocationFilters(
    filters: FieldFilters.LocationFieldFilters,
    onFilterSelected: (Filter, Boolean) -> Unit
) {
    FilterFlowGroup(
        header = stringResource(id = R.string.type),
        filters = filters.types,
        onFilterSelected = onFilterSelected
    )

    UnusedSwitch()
}

@Composable
private fun ItemFilters(
    filters: FieldFilters.ItemFieldFilters,
    onFilterSelected: (Filter, Boolean) -> Unit
) {
    FilterFlowGroup(
        header = stringResource(id = R.string.type),
        filters = filters.types,
        onFilterSelected = onFilterSelected
    )

    UnusedSwitch()
}

@Composable
private fun FilterHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier
    )
}

@Composable
private fun FilterFlowGroup(
    header: String,
    filters: List<Filter>,
    onFilterSelected: (Filter, Boolean) -> Unit
) {
    FilterHeader(text = header)

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)
    ) {
        filters.forEach { filter ->
            var selected by rememberSaveable(key = filter.toString()) { mutableStateOf(filter.isSelected) }

            FilterChip(
                selected = selected,
                onClick = {
                    selected = !selected
                    onFilterSelected(filter, selected)
                },
                label = { Text(text = filter.name) },
                modifier = Modifier.wrapContentWidth()
            )
        }
    }
}

@Composable
private fun UnusedSwitch() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterHeader(
            text = stringResource(id = R.string.unused),
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )

        var checked by rememberSaveable { mutableStateOf(true) }

        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
            }
        )
    }
}

@Composable
private fun TabbedCategoryLayout(
    categories: List<CategoryFilter>,
    filters: FilterCollection,
    onCategoryChanged: (Int) -> Unit,
    onFilterSelected: (Filter, Boolean) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { categories.size }
    )

    Column {
        HeaderLayout(
            categories = categories,
            pagerState = pagerState,
            onCategoryChanged = onCategoryChanged
        )

        CategoryPager(
            categories = categories,
            filters = filters,
            pagerState = pagerState,
            onFilterSelected = onFilterSelected
        )
    }
}

@Composable
private fun HeaderLayout(
    categories: List<CategoryFilter>,
    pagerState: PagerState,
    onCategoryChanged: (Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onSurface,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                width = 30.dp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.tabIndicatorOffset(it[selectedIndex])
            )
        }
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    onCategoryChanged(index)

                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                },
                modifier = Modifier.height(48.dp)
            ) {
                Text(text = category.name)
            }
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { selectedIndex = it }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CategoryPager(
    categories: List<CategoryFilter>,
    filters: FilterCollection,
    pagerState: PagerState,
    onFilterSelected: (Filter, Boolean) -> Unit
) {
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(top = 8.dp),
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            val selectedCategory = categories[page]

            when (selectedCategory.type) {
                Filter.Category.NPC -> NpcFilters(
                    filters = filters.npcFieldFilters,
                    onFilterSelected = onFilterSelected
                )

                Filter.Category.SHOP -> ShopFilters(
                    filters = filters.shopFieldFilters,
                    onFilterSelected = onFilterSelected
                )

                Filter.Category.LOCATION -> LocationFilters(
                    filters = filters.locationFieldFilters,
                    onFilterSelected = onFilterSelected
                )

                Filter.Category.ITEM -> ItemFilters(
                    filters = filters.itemFieldFilters,
                    onFilterSelected = onFilterSelected
                )
            }
        }
    }
}
