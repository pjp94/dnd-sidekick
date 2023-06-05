package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.screen.CategoryScreen
import com.pancholi.grabbag.ui.screen.item.ItemViewModel
import com.pancholi.grabbag.ui.screen.location.LocationViewModel
import com.pancholi.grabbag.ui.screen.npc.NpcViewModel
import com.pancholi.grabbag.ui.screen.shop.ShopViewModel

fun NavGraphBuilder.npcGraph(
    onBackPressed: () -> Unit,
    onAddClicked: (Action) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = Category.NPC.route, route = "npc_home") {
        composable(Category.NPC.route) {
            val title = stringResource(id = R.string.npcs)
            val errorMessage = stringResource(id = R.string.empty_category, title)
            CategoryScreen(
                category = Category.NPC,
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<NpcViewModel>(),
                onBackPressed = onBackPressed,
                onAddClicked = onAddClicked
            )
        }
        nestedGraphs()
    }
}

fun NavGraphBuilder.shopGraph(
    onBackPressed: () -> Unit,
    onAddClicked: (Action) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = Category.SHOP.route, route = "shop_home") {
        composable(Category.SHOP.route) {
            val title = stringResource(id = R.string.shops)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            CategoryScreen(
                category = Category.SHOP,
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<ShopViewModel>(),
                onBackPressed = onBackPressed,
                onAddClicked = onAddClicked
            )
        }
        nestedGraphs()
    }
}

fun NavGraphBuilder.locationGraph(
    onBackPressed: () -> Unit,
    onAddClicked: (Action) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = Category.LOCATION.route, route = "location_home") {
        composable(Category.LOCATION.route) {
            val title = stringResource(id = R.string.locations)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            CategoryScreen(
                category = Category.LOCATION,
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<LocationViewModel>(),
                onBackPressed = onBackPressed,
                onAddClicked = onAddClicked
            )
        }
        nestedGraphs()
    }
}

fun NavGraphBuilder.itemGraph(
    onBackPressed: () -> Unit,
    onAddClicked: (Action) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = Category.ITEM.route, route = "item_home") {
        composable(Category.ITEM.route) {
            val title = stringResource(id = R.string.items)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            CategoryScreen(
                category = Category.ITEM,
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<ItemViewModel>(),
                onBackPressed = onBackPressed,
                onAddClicked = onAddClicked
            )
        }
        nestedGraphs()
    }
}