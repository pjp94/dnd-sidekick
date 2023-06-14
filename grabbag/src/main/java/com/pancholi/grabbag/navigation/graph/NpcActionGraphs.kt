package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.npc.AddNpcScreen
import com.pancholi.grabbag.ui.screen.npc.NpcViewModel

fun NavGraphBuilder.addNpcScreen(
    onBackPressed: () -> Unit
) {
    composable(Action.ADD_NPC.route) {
        val viewModel: NpcViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val npcViewState = viewModel.npcViewState.collectAsStateWithLifecycle()

        AddNpcScreen(
            title = stringResource(id = R.string.add_npc),
            onBackPressed = { onBackPressed() },
            onNpcSaved = {
                onBackPressed()
                viewModel.onModelSaved()
            },
            onSaveClicked = { npc -> viewModel.onSaveClicked(npc) },
            npcViewState = npcViewState.value
        )
    }
}
