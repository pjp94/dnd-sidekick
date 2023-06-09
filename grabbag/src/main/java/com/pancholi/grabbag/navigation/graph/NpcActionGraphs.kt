package com.pancholi.grabbag.navigation.graph

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pancholi.core.viewModelScopedTo
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.ui.screen.modelaction.ModelAction
import com.pancholi.grabbag.ui.screen.npc.NpcActionScreen
import com.pancholi.grabbag.ui.screen.npc.NpcActionViewModel

fun NavGraphBuilder.addNpcScreen(
    onBackPressed: () -> Unit,
    onModelSaved: () -> Unit
) {
    composable(Action.ADD_NPC.route) {
        val viewModel: NpcActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        NpcActionScreen(
            modelAction = ModelAction.ADD,
            title = stringResource(id = R.string.add_npc),
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
            onSaveClicked = { npc, action -> viewModel.onSaveClicked(npc, action) },
            onModelSaved = onModelSaved,
            onDialogDismissed = { viewModel.onDialogDismissed() },
            onRaceChanged = { race -> viewModel.onRaceChanged(race) },
            onClassChanged = { clss -> viewModel.onClassChanged(clss) },
            onProfessionChanged =  { profession -> viewModel.onProfessionChanged(profession) },
            viewState = viewState.value,
            npcSaved = viewModel.modelSaved
        )
    }
}

fun NavGraphBuilder.editNpcScreen(
    onBackPressed: () -> Unit,
    onModelSaved: () -> Unit
) {
    composable("${Action.EDIT_NPC.route}{id}") {
        val viewModel: NpcActionViewModel = it.viewModelScopedTo(route = "grab_bag_home")
        val viewState = viewModel.viewState.collectAsStateWithLifecycle()

        it.arguments?.getString("id")?.let { id -> viewModel.getModelById(id = id.toInt()) }

        NpcActionScreen(
            modelAction = ModelAction.EDIT,
            title = stringResource(id = R.string.edit_npc),
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
            onSaveClicked = { npc, action -> viewModel.onSaveClicked(npc, action) },
            onModelSaved = onModelSaved,
            onDialogDismissed = { viewModel.onDialogDismissed() },
            onRaceChanged = { race -> viewModel.onRaceChanged(race) },
            onClassChanged = { clss -> viewModel.onClassChanged(clss) },
            onProfessionChanged =  { profession -> viewModel.onProfessionChanged(profession) },
            viewState = viewState.value,
            npcSaved = viewModel.modelSaved
        )
    }
}