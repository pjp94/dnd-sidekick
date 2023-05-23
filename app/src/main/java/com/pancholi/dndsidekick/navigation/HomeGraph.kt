package com.pancholi.dndsidekick.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pancholi.battletracker.ui.BattleTrackerHome
import com.pancholi.grabbag.ui.GrabBagHome

fun NavGraphBuilder.homeGraph(navController: NavHostController) {
    navigation(startDestination = Screen.GrabBag.route, route = "home") {
        composable(Screen.GrabBag.route) {
            GrabBagHome(navController)
        }
        composable(Screen.BattleTracker.route) {
            BattleTrackerHome(navController)
        }
    }
}