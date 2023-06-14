package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.location.AddLocationScreen
import com.pancholi.grabbag.ui.screen.location.LocationViewModel

fun NavGraphBuilder.addLocationScreen(
    onBackPressed: () -> Unit
) {
    composable(Action.ADD_LOCATION.route) {
        val viewModel: LocationViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val locationViewState = viewModel.locationViewState.collectAsStateWithLifecycle()

        AddLocationScreen(
            title = stringResource(id = R.string.add_location),
            onBackPressed = onBackPressed,
            onLocationSaved = {
                onBackPressed()
                viewModel.onBackPressed()
            },
            onSaveClicked = { location -> viewModel.onSaveClicked(location) },
            locationViewState = locationViewState.value
        )
    }
}