package com.pancholi.grabbag.navigation

import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pancholi.grabbag.CategoryScreen
import com.pancholi.grabbag.R
import com.pancholi.grabbag.ui.CategoryScreen
import com.pancholi.grabbag.viewmodel.LocationViewModel
import com.pancholi.grabbag.viewmodel.NpcViewModel
import com.pancholi.grabbag.viewmodel.ShopViewModel

fun NavGraphBuilder.grabBagGraph(navController: NavHostController) {
    navigation(startDestination = "grab_bag_home", route = "grab_bag") {
        composable(CategoryScreen.Npc.route) {
            val title = stringResource(id = R.string.npcs)
            val errorMessage = stringResource(id = R.string.empty_category, title)
            CategoryScreen(
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<NpcViewModel>(),
                onBackPressed = { navController.popBackStack() }
            )
        }
        composable(CategoryScreen.Shop.route) {
            val title = stringResource(id = R.string.shops)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            CategoryScreen(
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<ShopViewModel>(),
                onBackPressed = { navController.popBackStack() }
            )
        }
        composable(CategoryScreen.Location.route) {
            val title = stringResource(id = R.string.locations)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            CategoryScreen(
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<LocationViewModel>(),
                onBackPressed = { navController.popBackStack() }
            )
        }
        composable(CategoryScreen.Item.route) {
            val title = stringResource(id = R.string.items)
            val errorMessage = stringResource(id = R.string.empty_category, title.lowercase())
            CategoryScreen(
                title = title,
                errorMessage = errorMessage,
                viewModel = hiltViewModel<ShopViewModel>(),
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}