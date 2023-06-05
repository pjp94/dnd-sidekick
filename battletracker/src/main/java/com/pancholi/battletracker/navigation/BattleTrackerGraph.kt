package com.pancholi.battletracker.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pancholi.battletracker.ui.BattleTrackerHome

fun NavGraphBuilder.battleTrackerGraph(
    navController: NavHostController,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = "battle_tracker_home", route = "battle_tracker") {
        composable(route = "battle_tracker_home") {
            BattleTrackerHome(
                navController = navController
            )
        }
    }
}