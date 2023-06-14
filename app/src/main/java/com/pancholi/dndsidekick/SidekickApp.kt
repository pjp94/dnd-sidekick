package com.pancholi.dndsidekick

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pancholi.core.LocalNavController
import com.pancholi.dndsidekick.navigation.Screen
import com.pancholi.dndsidekick.navigation.SidekickGraph

@Composable
internal fun SidekickApp(
    navController: NavHostController = rememberNavController(),
    items: List<Screen>
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(id = R.color.red),
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    val selected = currentDestination.isTopLevelDestinationInHierarchy(screen)

                    NavigationBarItem(
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
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = colorResource(id = R.color.dark_red)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        CompositionLocalProvider(LocalNavController provides navController) {
            SidekickGraph(
                navController = navController,
                snackbarHostState = snackbarHostState,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(screen: Screen) =
    this?.hierarchy?.any {
        it.route?.contains(screen.name, true) ?: false
    } ?: false