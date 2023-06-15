package com.pancholi.dndsidekick

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pancholi.core.SidekickSnackbarVisuals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SidekickAppState(
    val snackbarHost: SnackbarHostState,
    val snackbarScope: CoroutineScope,
    val navController: NavHostController
) {

    fun showSnackbar(visuals: SidekickSnackbarVisuals) {
        snackbarScope.launch {
            snackbarHost.showSnackbar(visuals)
        }
    }
}

@Composable
fun rememberSidekickAppState(
    snackbarHost: SnackbarHostState = remember { SnackbarHostState() },
    snackbarScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) = remember(snackbarHost, snackbarScope, navController) {
    SidekickAppState(
        snackbarHost = snackbarHost,
        snackbarScope = snackbarScope,
        navController = navController
    )
}