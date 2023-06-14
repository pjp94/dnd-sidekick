package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.item.AddItemScreen
import com.pancholi.grabbag.ui.screen.item.ItemViewModel

fun NavGraphBuilder.addItemScreen(
    onBackPressed: () -> Unit
) {
    composable(Action.ADD_ITEM.route) {
        val viewModel: ItemViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val itemViewState = viewModel.itemViewState.collectAsStateWithLifecycle()

        AddItemScreen(
            title = stringResource(id = R.string.add_item),
            onBackPressed = onBackPressed,
            onItemSaved = {
                onBackPressed()
                viewModel.onBackPressed()
            },
            onSaveClicked = { item -> viewModel.onSaveClicked(item) },
            itemViewState = itemViewState.value
        )
    }
}