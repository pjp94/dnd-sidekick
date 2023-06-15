package com.pancholi.dndsidekick.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pancholi.battletracker.navigation.battleTrackerGraph
import com.pancholi.core.SidekickSnackbarVisuals
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
    showSnackbar: (SidekickSnackbarVisuals) -> Unit,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "grab_bag",
        modifier = modifier
    ) {
        grabBagGraph(
            showSnackbar = showSnackbar,
            onCategoryClicked = { category ->
                navController.navigate(category.route)
            },
            nestedGraphs = {
                npcGraph(
                    showSnackbar = showSnackbar,
                    onBackPressed = { navController.popBackStack() },
                    onAddClicked = { action -> navController.navigate(action.route) },
                    nestedGraphs = {
                        addNpcScreen(
                            onBackPressed = { navController.popBackStack() }
                        )
                    }
                )
                shopGraph(
                    showSnackbar = showSnackbar,
                    onBackPressed = { navController.popBackStack() },
                    onAddClicked = { action -> navController.navigate(action.route) },
                    nestedGraphs = {
                        addShopScreen(
                            onBackPressed = { navController.popBackStack() }
                        )
                    }
                )
                locationGraph(
                    showSnackbar = showSnackbar,
                    onBackPressed = { navController.popBackStack() },
                    onAddClicked = { action -> navController.navigate(action.route) },
                    nestedGraphs = {
                        addLocationScreen(
                            onBackPressed = { navController.popBackStack() }
                        )
                    }
                )
                itemGraph(
                    showSnackbar = showSnackbar,
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