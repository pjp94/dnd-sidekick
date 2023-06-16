package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.modelaction.ModelAction
import com.pancholi.grabbag.ui.screen.shop.ShopActionScreen
import com.pancholi.grabbag.ui.screen.shop.ShopActionViewModel

fun NavGraphBuilder.addShopScreen(
    onBackPressed: () -> Unit,
    onModelSaved: () -> Unit
) {
    composable(Action.ADD_SHOP.route) {
        val viewModel: ShopActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        ShopActionScreen(
            modelAction = ModelAction.ADD,
            title = stringResource(id = R.string.add_shop),
            onBackPressed = { viewModel.onBackPressed(ModelAction.ADD) },
            onBackConfirmed = {
                onBackPressed()
                viewModel.resetViewState()
            },
            onSaveClicked = { shop, action -> viewModel.onSaveClicked(shop, action) },
            onModelSaved = onModelSaved,
            onDialogDismissed = { viewModel.onDialogDismissed() },
            viewState = viewState.value,
            shopSaved = viewModel.modelSaved
        )
    }
}

fun NavGraphBuilder.editShopScreen(
    onBackPressed: () -> Unit,
    onModelSaved: () -> Unit
) {
    composable("${Action.EDIT_SHOP.route}{id}") {
        val viewModel: ShopActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        it.arguments?.getString("id")?.let { id -> viewModel.getModelById(id = id.toInt()) }

        ShopActionScreen(
            modelAction = ModelAction.EDIT,
            title = stringResource(id = R.string.edit_shop),
            onBackPressed = { viewModel.onBackPressed(ModelAction.EDIT) },
            onBackConfirmed = {
                onBackPressed()
                viewModel.resetViewState()
            },
            onSaveClicked = { shop, action -> viewModel.onSaveClicked(shop, action) },
            onModelSaved = onModelSaved,
            onDialogDismissed = { viewModel.onDialogDismissed() },
            viewState = viewState.value,
            shopSaved = viewModel.modelSaved
        )
    }
}