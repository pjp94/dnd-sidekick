package com.pancholi.grabbag.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.screen.home.GrabBagHome

fun NavGraphBuilder.grabBagGraph(
    onCategoryClicked: (Category) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(startDestination = "grab_bag_home", route = "grab_bag") {
        composable(route = "grab_bag_home") {
            GrabBagHome(
                categories = Category.values().toList(),
                onCategoryClicked = onCategoryClicked
            )
        }
        nestedGraphs()
    }
}