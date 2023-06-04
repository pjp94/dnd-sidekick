package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.screen.item.AddItemScreen
import com.pancholi.grabbag.ui.screen.location.AddLocationScreen
import com.pancholi.grabbag.ui.screen.npc.AddNpcScreen
import com.pancholi.grabbag.ui.screen.shop.AddShopScreen

fun NavGraphBuilder.categoryGraph(navController: NavHostController) {
    navigation(startDestination = Action.ADD_NPC.route, route = "category") {
        composable(Action.ADD_NPC.route) {
            AddNpcScreen(
                title = stringResource(id = R.string.add_npc),
                onBackPressed = {
                    navController.popBackStack(
                        route = Category.NPC.route,
                        inclusive = false,
                        saveState = false
                    )
                }
            )
        }
        composable(Action.ADD_SHOP.route) {
            AddShopScreen(
                title = stringResource(id = R.string.add_shop),
                onBackPressed = {
                    navController.popBackStack(
                        route = Category.SHOP.route,
                        inclusive = false,
                        saveState = false
                    )
                }
            )
        }
        composable(Action.ADD_LOCATION.route) {
            AddLocationScreen(
                title = stringResource(id = R.string.add_location),
                onBackPressed = {
                    navController.popBackStack(
                        route = Category.LOCATION.route,
                        inclusive = false,
                        saveState = false
                    )
                }
            )
        }
        composable(Action.ADD_ITEM.route) {
            AddItemScreen(
                title = stringResource(id = R.string.add_item),
                onBackPressed = {
                    navController.popBackStack(
                        route = Category.ITEM.route,
                        inclusive = false,
                        saveState = false
                    )
                }
            )
        }
    }
}