package com.pancholi.grabbag.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pancholi.grabbag.CategoryScreen
import com.pancholi.grabbag.ui.NpcHome

fun NavGraphBuilder.grabBagGraph(navController: NavHostController) {
    navigation(startDestination = "grab_bag_home", route = "grab_bag") {
        composable(CategoryScreen.Npc.route) {
            NpcHome(navController)
        }
        composable(CategoryScreen.Shop.route) {

        }
        composable(CategoryScreen.Location.route) {

        }
        composable(CategoryScreen.Item.route) {

        }
    }
}