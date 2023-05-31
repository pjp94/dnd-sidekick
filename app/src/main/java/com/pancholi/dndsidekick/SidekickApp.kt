package com.pancholi.dndsidekick

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pancholi.dndsidekick.navigation.Screen
import com.pancholi.dndsidekick.navigation.SidekickGraph

@Composable
internal fun SidekickApp(
    navController: NavHostController = rememberNavController(),
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
                        label = {
                            Text(
                                text = stringResource(id = screen.labelId)
                            )
                        },
                        icon = {
                            Icon(
                                painterResource(id = screen.iconId),
                                contentDescription = stringResource(id = screen.iconDescriptionId)
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
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
        SidekickGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}