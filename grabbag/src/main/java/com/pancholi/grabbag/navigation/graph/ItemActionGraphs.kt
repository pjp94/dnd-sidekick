package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.item.ItemActionScreen
import com.pancholi.grabbag.ui.screen.item.ItemActionViewModel
import com.pancholi.grabbag.ui.screen.modelaction.ModelAction

fun NavGraphBuilder.addItemScreen(
    onBackPressed: () -> Unit,
    onModelSaved: () -> Unit
) {
    composable(Action.ADD_ITEM.route) {
        val viewModel: ItemActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        ItemActionScreen(
            modelAction = ModelAction.ADD,
            title = stringResource(id = R.string.add_item),
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
            onSaveClicked = { item, action -> viewModel.onSaveClicked(item, action) },
            onModelSaved = onModelSaved,
            onDialogDismissed = { viewModel.onDialogDismissed() },
            viewState = viewState.value,
            itemSaved = viewModel.modelSaved
        )
    }
}

fun NavGraphBuilder.editItemScreen(
    onBackPressed: () -> Unit,
    onModelSaved: () -> Unit
) {
    composable("${Action.EDIT_ITEM.route}{id}") {
        val viewModel: ItemActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        it.arguments?.getString("id")?.let { id -> viewModel.getModelById(id = id.toInt()) }

        ItemActionScreen(
            modelAction = ModelAction.EDIT,
            title = stringResource(id = R.string.edit_item),
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
            onSaveClicked = { item, action -> viewModel.onSaveClicked(item, action) },
            onModelSaved = onModelSaved,
            onDialogDismissed = { viewModel.onDialogDismissed() },
            viewState = viewState.value,
            itemSaved = viewModel.modelSaved
        )
    }
}