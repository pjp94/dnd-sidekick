package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.location.LocationActionScreen
import com.pancholi.grabbag.ui.screen.location.LocationActionViewModel
import com.pancholi.grabbag.ui.screen.modelaction.ModelAction

fun NavGraphBuilder.addLocationScreen(
    onBackPressed: () -> Unit,
    onModelSaved: () -> Unit
) {
    composable(Action.ADD_LOCATION.route) {
        val viewModel: LocationActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        LocationActionScreen(
            modelAction = ModelAction.ADD,
            title = stringResource(id = R.string.add_location),
            onBackPressed = { oldModel, newModel ->
                viewModel.onBackPressed(
                    action = ModelAction.ADD,
                    oldModel = oldModel,
                    newModel = newModel,
                    onBackConfirmed = {
                        onBackPressed()
                        viewModel.resetViewState()
                    }
                )
            },
            onBackConfirmed = {
                onBackPressed()
                viewModel.resetViewState()
            },
            onSaveClicked = { location, action -> viewModel.onSaveClicked(location, action) },
            onModelSaved = onModelSaved,
            onDialogDismissed = { viewModel.onDialogDismissed() },
            onTypeChanged = { type -> viewModel.onTypeChanged(type) },
            viewState = viewState.value,
            locationSaved = viewModel.modelSaved
        )
    }
}

fun NavGraphBuilder.editLocationScreen(
    onBackPressed: () -> Unit,
    onModelSaved: () -> Unit
) {
    composable("${Action.EDIT_LOCATION.route}{id}") {
        val viewModel: LocationActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        it.arguments?.getString("id")?.let { id -> viewModel.getModelById(id = id.toInt()) }

        LocationActionScreen(
            modelAction = ModelAction.EDIT,
            title = stringResource(id = R.string.edit_location),
            onBackPressed = { oldModel, newModel ->
                viewModel.onBackPressed(
                    action = ModelAction.EDIT,
                    oldModel = oldModel,
                    newModel = newModel,
                    onBackConfirmed = {
                        onBackPressed()
                        viewModel.resetViewState()
                    }
                )
            },
            onBackConfirmed = {
                onBackPressed()
                viewModel.resetViewState()
            },
            onSaveClicked = { location, action -> viewModel.onSaveClicked(location, action) },
            onModelSaved = onModelSaved,
            onDialogDismissed = { viewModel.onDialogDismissed() },
            onTypeChanged = { type -> viewModel.onTypeChanged(type) },
            viewState = viewState.value,
            locationSaved = viewModel.modelSaved
        )
    }
}