package com.pancholi.dndsidekick.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pancholi.grabbag.navigation.graph.categoryGraph
import com.pancholi.grabbag.navigation.graph.grabBagGraph

@Composable
fun SidekickGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        homeGraph(navController)
        grabBagGraph(navController)
        categoryGraph(navController)
    }
}