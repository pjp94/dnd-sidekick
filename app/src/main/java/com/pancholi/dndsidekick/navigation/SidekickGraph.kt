package com.pancholi.dndsidekick.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pancholi.battletracker.navigation.battleTrackerGraph
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.grabbag.navigation.Routes
import com.pancholi.grabbag.navigation.graph.addItemScreen
import com.pancholi.grabbag.navigation.graph.addLocationScreen
import com.pancholi.grabbag.navigation.graph.addNpcScreen
import com.pancholi.grabbag.navigation.graph.addShopScreen
import com.pancholi.grabbag.navigation.graph.editItemScreen
import com.pancholi.grabbag.navigation.graph.editLocationScreen
import com.pancholi.grabbag.navigation.graph.editNpcScreen
import com.pancholi.grabbag.navigation.graph.editShopScreen
import com.pancholi.grabbag.navigation.graph.grabBagGraph
import com.pancholi.grabbag.navigation.graph.itemGraph
import com.pancholi.grabbag.navigation.graph.locationGraph
import com.pancholi.grabbag.navigation.graph.npcGraph
import com.pancholi.grabbag.navigation.graph.shopGraph
import com.pancholi.grabbag.navigation.graph.suggestGraph

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
            onSuggestClicked = {
                navController.navigate(Routes.SUGGEST_SCREEN)
            },
            nestedGraphs = {
                npcGraph(
                    showSnackbar = showSnackbar,
                    onBackPressed = { navController.popBackStack() },
                    onNavigateAction = { action, id ->
                        navController.navigate("${action.route}$id") {
                            launchSingleTop = true
                        }
                    },
                    nestedGraphs = {
                        addNpcScreen(
                            onBackPressed = { navController.popBackStack() },
                            onModelSaved = { navController.popBackStack() }
                        )
                        editNpcScreen(
                            onBackPressed = { navController.popBackStack() },
                            onModelSaved = { navController.popBackStack() }
                        )
                    }
                )
                shopGraph(
                    showSnackbar = showSnackbar,
                    onBackPressed = { navController.popBackStack() },
                    onNavigateAction = { action, id ->
                        navController.navigate("${action.route}$id") {
                            launchSingleTop = true
                        }
                    },
                    nestedGraphs = {
                        addShopScreen(
                            onBackPressed = { navController.popBackStack() },
                            onModelSaved = { navController.popBackStack() }
                        )
                        editShopScreen(
                            onBackPressed = { navController.popBackStack() },
                            onModelSaved = { navController.popBackStack() }
                        )
                    }
                )
                locationGraph(
                    showSnackbar = showSnackbar,
                    onBackPressed = { navController.popBackStack() },
                    onNavigateAction = { action, id ->
                        navController.navigate("${action.route}$id") {
                            launchSingleTop = true
                        }
                   },
                    nestedGraphs = {
                        addLocationScreen(
                            onBackPressed = { navController.popBackStack() },
                            onModelSaved = { navController.popBackStack() }
                        )
                        editLocationScreen(
                            onBackPressed = { navController.popBackStack() },
                            onModelSaved = { navController.popBackStack() }
                        )
                    }
                )
                itemGraph(
                    showSnackbar = showSnackbar,
                    onBackPressed = { navController.popBackStack() },
                    onNavigateAction = { action, id ->
                        navController.navigate("${action.route}$id") {
                            launchSingleTop = true
                        }
                    },
                    nestedGraphs = {
                        addItemScreen(
                            onBackPressed = { navController.popBackStack() },
                            onModelSaved = { navController.popBackStack() }
                        )
                        editItemScreen(
                            onBackPressed = { navController.popBackStack() },
                            onModelSaved = { navController.popBackStack() }
                        )
                    }
                )
                suggestGraph(
                    showSnackbar = showSnackbar,
                    onBackPressed = { navController.popBackStack() },
                    onNavigateAction = { _, _ -> },
                    nestedGraphs = {}
                )
            }
        )
        battleTrackerGraph(
            navController = navController,
            nestedGraphs = {}
        )
    }
}