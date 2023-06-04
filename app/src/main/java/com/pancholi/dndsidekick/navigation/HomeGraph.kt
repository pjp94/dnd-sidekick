package com.pancholi.dndsidekick.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pancholi.battletracker.ui.BattleTrackerHome
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.screen.home.GrabBagHome

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(startDestination = Screen.GrabBag.route, route = "home") {
        composable(Screen.GrabBag.route) {
            GrabBagHome(
                categories = Category.values().toList(),
                onCategoryClicked = { navController.navigate(it) }
            )
        }
        composable(Screen.BattleTracker.route) {
            BattleTrackerHome(navController)
        }
    }
}