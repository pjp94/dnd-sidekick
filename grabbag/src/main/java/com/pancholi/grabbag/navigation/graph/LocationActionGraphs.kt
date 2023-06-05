package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.location.AddLocationScreen

fun NavGraphBuilder.addLocationScreen(
    onBackPressed: () -> Unit
) {
    composable(Action.ADD_LOCATION.route) {
        AddLocationScreen(
            title = stringResource(id = R.string.add_location),
            onBackPressed = onBackPressed
        )
    }
}