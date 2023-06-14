package com.pancholi.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.viewModelScopedTo(route: String): T {
    val navController = LocalNavController.current
    val parentEntry = remember(this) { navController.getBackStackEntry(route) }
    return hiltViewModel(parentEntry)
}