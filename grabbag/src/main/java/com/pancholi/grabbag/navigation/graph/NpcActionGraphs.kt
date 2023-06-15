package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.npc.AddNpcScreen
import com.pancholi.grabbag.ui.screen.npc.AddNpcViewModel

fun NavGraphBuilder.addNpcScreen(
    onBackPressed: () -> Unit
) {
    composable(Action.ADD_NPC.route) {
        val viewModel: AddNpcViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        AddNpcScreen(
            title = stringResource(id = R.string.add_npc),
            onBackPressed = {
                onBackPressed()
                viewModel.resetViewState()
            },
            onSaveClicked = { npc -> viewModel.onSaveClicked(npc) },
            viewState = viewState.value,
            npcSaved = viewModel.modelSaved
        )
    }
}
