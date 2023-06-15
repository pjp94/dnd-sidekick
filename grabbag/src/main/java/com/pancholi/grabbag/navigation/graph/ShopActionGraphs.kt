package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.shop.AddShopScreen
import com.pancholi.grabbag.ui.screen.shop.AddShopViewModel

fun NavGraphBuilder.addShopScreen(
    onBackPressed: () -> Unit
) {
    composable(Action.ADD_SHOP.route) {
        val viewModel: AddShopViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        AddShopScreen(
            title = stringResource(id = R.string.add_shop),
            onBackPressed = {
                onBackPressed()
                viewModel.resetViewState()
            },
            onSaveClicked = { shop -> viewModel.onSaveClicked(shop) },
            viewState = viewState.value,
            shopSaved = viewModel.modelSaved
        )
    }
}