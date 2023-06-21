package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.screen.category.CategoryScreen
import com.pancholi.grabbag.ui.screen.item.ItemActionViewModel
import com.pancholi.grabbag.ui.screen.item.ItemViewModel
import com.pancholi.grabbag.ui.screen.location.LocationActionViewModel
import com.pancholi.grabbag.ui.screen.location.LocationViewModel
import com.pancholi.grabbag.ui.screen.npc.NpcActionViewModel
import com.pancholi.grabbag.ui.screen.npc.NpcViewModel
import com.pancholi.grabbag.ui.screen.shop.ShopActionViewModel
import com.pancholi.grabbag.ui.screen.shop.ShopViewModel

fun NavGraphBuilder.npcGraph(
    showSnackbar: (SidekickSnackbarVisuals) -> Unit,
    onBackPressed: () -> Unit,
    onNavigateAction: (Action, String?) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(startDestination = Category.NPC.route, route = "npc_home") {
        composable(Category.NPC.route) {
            val title = stringResource(id = R.string.npcs)
            val errorMessage = stringResource(id = R.string.empty_category, title)
            val npcViewModel: NpcViewModel = hiltViewModel()
            val npcActionViewModel: NpcActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")

            CategoryScreen(
                category = Category.NPC,
                title = title,
                showSnackbar = showSnackbar,
                errorMessage = errorMessage,
                viewModel = npcViewModel,
                actionViewModel = npcActionViewModel,
                onBackPressed = onBackPressed,
                onNavigateAction = onNavigateAction
            )
        }
        nestedGraphs()
    }
}

fun NavGraphBuilder.shopGraph(
    showSnackbar: (SidekickSnackbarVisuals) -> Unit,
    onBackPressed: () -> Unit,
    onNavigateAction: (Action, String?) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = Category.SHOP.route, route = "shop_home") {
        composable(Category.SHOP.route) {
            val title = stringResource(id = R.string.shops)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            val shopViewModel: ShopViewModel = hiltViewModel()
            val shopActionViewModel: ShopActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")

            CategoryScreen(
                category = Category.SHOP,
                title = title,
                showSnackbar = showSnackbar,
                errorMessage = errorMessage,
                viewModel = shopViewModel,
                actionViewModel = shopActionViewModel,
                onBackPressed = onBackPressed,
                onNavigateAction = onNavigateAction
            )
        }
        nestedGraphs()
    }
}

fun NavGraphBuilder.locationGraph(
    showSnackbar: (SidekickSnackbarVisuals) -> Unit,
    onBackPressed: () -> Unit,
    onNavigateAction: (Action, String?) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = Category.LOCATION.route, route = "location_home") {
        composable(Category.LOCATION.route) {
            val title = stringResource(id = R.string.locations)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            val locationViewModel: LocationViewModel = hiltViewModel()
            val locationActionViewModel: LocationActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")

            CategoryScreen(
                category = Category.LOCATION,
                title = title,
                showSnackbar = showSnackbar,
                errorMessage = errorMessage,
                viewModel = locationViewModel,
                actionViewModel = locationActionViewModel,
                onBackPressed = onBackPressed,
                onNavigateAction = onNavigateAction
            )
        }
        nestedGraphs()
    }
}

fun NavGraphBuilder.itemGraph(
    showSnackbar: (SidekickSnackbarVisuals) -> Unit,
    onBackPressed: () -> Unit,
    onNavigateAction: (Action, String?) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = Category.ITEM.route, route = "item_home") {
        composable(Category.ITEM.route) {
            val title = stringResource(id = R.string.items)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            val itemViewModel: ItemViewModel = hiltViewModel()
            val itemActionViewModel: ItemActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")

            CategoryScreen(
                category = Category.ITEM,
                title = title,
                showSnackbar = showSnackbar,
                errorMessage = errorMessage,
                viewModel = itemViewModel,
                actionViewModel = itemActionViewModel,
                onBackPressed = onBackPressed,
                onNavigateAction = onNavigateAction
            )
        }
        nestedGraphs()
    }
}