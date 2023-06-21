package com.pancholi.grabbag.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.navigation.Routes
import com.pancholi.grabbag.ui.screen.suggest.SuggestScreen

fun NavGraphBuilder.suggestGraph(
    showSnackbar: (SidekickSnackbarVisuals) -> Unit,
    onBackPressed: () -> Unit,
    onNavigateAction: (Action, String?) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(startDestination = Routes.SUGGEST_SCREEN, route = "suggest_home") {
        composable(Routes.SUGGEST_SCREEN) {
            SuggestScreen(
                onBackPressed = onBackPressed
            )
        }
        nestedGraphs()
    }
}