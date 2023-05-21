package com.pancholi.dndsidekick.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pancholi.battletracker.ui.BattleTracker
import com.pancholi.dndsidekick.navigation.Screen
import com.pancholi.grabbag.ui.GrabBag

@Composable
fun BottomNavBar(
    navController: NavHostController,
    items: List<Screen>
) {
    Scaffold(
        bottomBar = {
            BottomNavigation(
                elevation = 64.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    BottomNavigationItem(
                        label = { Text(
                            text = stringResource(id = screen.labelId)
                        ) },
                        icon = { Icon(
                            painterResource(id = screen.iconId),
                            contentDescription = stringResource(id = screen.iconDescriptionId)
                        ) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route} == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.GrabBag.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.GrabBag.route) { GrabBag(navController) }
            composable(Screen.BattleTracker.route) { BattleTracker(navController) }
        }
    }
}

@Preview(name = "Bottom Nav Preview")
@Composable
fun BottomNavBarPreview() {
    BottomNavBar(
        navController = rememberNavController(),
        items = listOf(Screen.GrabBag, Screen.BattleTracker)
    )
}