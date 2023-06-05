package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.shop.AddShopScreen

fun NavGraphBuilder.addShopScreen(
    onBackPressed: () -> Unit
) {
    composable(Action.ADD_SHOP.route) {
        AddShopScreen(
            title = stringResource(id = R.string.add_shop),
            onBackPressed = onBackPressed
        )
    }
}