package com.pancholi.grabbag.navigation.graph

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.screen.home.GrabBagHome

fun NavGraphBuilder.grabBagGraph(
    snackbarHostState: SnackbarHostState,
    onCategoryClicked: (Category) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = "grab_bag_home", route = "grab_bag") {
        composable(route = "grab_bag_home") {
            GrabBagHome(
                snackbarHostState = snackbarHostState,
                categories = Category.values().toList(),
                onCategoryClicked = onCategoryClicked
            )
        }
        nestedGraphs()
    }
}