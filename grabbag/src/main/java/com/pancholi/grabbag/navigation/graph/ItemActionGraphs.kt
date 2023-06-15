package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.item.AddItemScreen
import com.pancholi.grabbag.ui.screen.item.AddItemViewModel

fun NavGraphBuilder.addItemScreen(
    onBackPressed: () -> Unit
) {
    composable(Action.ADD_ITEM.route) {
        val viewModel: AddItemViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        AddItemScreen(
            title = stringResource(id = R.string.add_item),
            onBackPressed = {
                onBackPressed()
                viewModel.resetViewState()
            },
            onSaveClicked = { item -> viewModel.onSaveClicked(item) },
            viewState = viewState.value,
            itemSaved = viewModel.modelSaved
        )
    }
}