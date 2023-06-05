package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.item.AddItemScreen

fun NavGraphBuilder.addItemScreen(
    onBackPressed: () -> Unit
) {
    composable(Action.ADD_ITEM.route) {
        AddItemScreen(
            title = stringResource(id = R.string.add_item),
            onBackPressed = onBackPressed
        )
    }
}