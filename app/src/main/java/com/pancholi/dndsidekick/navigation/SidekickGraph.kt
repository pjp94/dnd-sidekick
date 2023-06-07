package com.pancholi.dndsidekick.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pancholi.battletracker.navigation.battleTrackerGraph
import com.pancholi.grabbag.navigation.graph.addItemScreen
import com.pancholi.grabbag.navigation.graph.addLocationScreen
import com.pancholi.grabbag.navigation.graph.addNpcScreen
import com.pancholi.grabbag.navigation.graph.addShopScreen
import com.pancholi.grabbag.navigation.graph.grabBagGraph
import com.pancholi.grabbag.navigation.graph.itemGraph
import com.pancholi.grabbag.navigation.graph.locationGraph
import com.pancholi.grabbag.navigation.graph.npcGraph
import com.pancholi.grabbag.navigation.graph.shopGraph

@Composable
fun SidekickGraph(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "grab_bag",
        modifier = modifier
    ) {
        grabBagGraph(
            snackbarHostState = snackbarHostState,
            onCategoryClicked = { category ->
                navController.navigate(category.route)
            },
            nestedGraphs = {
                npcGraph(
                    onBackPressed = { navController.popBackStack() },
                    onAddClicked = { action -> navController.navigate(action.route) },
                    nestedGraphs = {
                        addNpcScreen(
                            onBackPressed = { navController.popBackStack() }
                        )
                    }
                )
                shopGraph(
                    onBackPressed = { navController.popBackStack() },
                    onAddClicked = { action -> navController.navigate(action.route) },
                    nestedGraphs = {
                        addShopScreen(
                            onBackPressed = { navController.popBackStack() }
                        )
                    }
                )
                locationGraph(
                    onBackPressed = { navController.popBackStack() },
                    onAddClicked = { action -> navController.navigate(action.route) },
                    nestedGraphs = {
                        addLocationScreen(
                            onBackPressed = { navController.popBackStack() }
                        )
                    }
                )
                itemGraph(
                    onBackPressed = { navController.popBackStack() },
                    onAddClicked = { action -> navController.navigate(action.route) },
                    nestedGraphs = {
                        addItemScreen(
                            onBackPressed = { navController.popBackStack() }
                        )
                    }
                )
            }
        )
        battleTrackerGraph(
            navController = navController,
            nestedGraphs = {}
        )
    }
}