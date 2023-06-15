package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.location.AddLocationScreen
import com.pancholi.grabbag.ui.screen.location.AddLocationViewModel

fun NavGraphBuilder.addLocationScreen(
    onBackPressed: () -> Unit
) {
    composable(Action.ADD_LOCATION.route) {
        val viewModel: AddLocationViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        AddLocationScreen(
            title = stringResource(id = R.string.add_location),
            onBackPressed = {
                onBackPressed()
                viewModel.resetViewState()
            },
            onSaveClicked = { location -> viewModel.onSaveClicked(location) },
            viewState = viewState.value,
            locationSaved = viewModel.modelSaved
        )
    }
}