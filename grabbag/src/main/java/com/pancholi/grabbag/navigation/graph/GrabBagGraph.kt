package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.screen.CategoryScreen
import com.pancholi.grabbag.ui.screen.item.ItemViewModel
import com.pancholi.grabbag.ui.screen.location.LocationViewModel
import com.pancholi.grabbag.ui.screen.npc.NpcViewModel
import com.pancholi.grabbag.ui.screen.shop.ShopViewModel

fun NavGraphBuilder.grabBagGraph(
    navController: NavHostController,
    onCategoryClicked: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = "grab_bag_home", route = "grab_bag") {
        composable(Category.NPC.route) {
            val title = stringResource(id = R.string.npcs)
            val errorMessage = stringResource(id = R.string.empty_category, title)
            CategoryScreen(
                category = Category.NPC,
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<NpcViewModel>(),
                onBackPressed = { navController.popBackStack() },
                onAddClicked = { navController.navigate(Action.ADD_NPC.route) }
            )
        }
        composable(Category.SHOP.route) {
            val title = stringResource(id = R.string.shops)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            CategoryScreen(
                category = Category.SHOP,
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<ShopViewModel>(),
                onBackPressed = { navController.popBackStack() },
                onAddClicked = { navController.navigate(Action.ADD_SHOP.route) }
            )
        }
        composable(Category.LOCATION.route) {
            val title = stringResource(id = R.string.locations)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            CategoryScreen(
                category = Category.LOCATION,
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<LocationViewModel>(),
                onBackPressed = { navController.popBackStack() },
                onAddClicked = { navController.navigate(Action.ADD_LOCATION.route) }
            )
        }
        composable(Category.ITEM.route) {
            val title = stringResource(id = R.string.items)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            CategoryScreen(
                category = Category.ITEM,
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<ItemViewModel>(),
                onBackPressed = { navController.popBackStack() },
                onAddClicked = { navController.navigate(Action.ADD_ITEM.route) }
            )
        }
    }
}