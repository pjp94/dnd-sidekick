package com.pancholi.grabbag.ui.screen.npc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.pancholi.core.Result
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.ui.AutocompletePropertyField
import com.pancholi.grabbag.ui.ConfirmationDialog
import com.pancholi.grabbag.ui.LoadingIndicator
import com.pancholi.grabbag.ui.OptionalTextField
import com.pancholi.grabbag.ui.PropertyTextBox
import com.pancholi.grabbag.ui.PropertyTextField
import com.pancholi.grabbag.ui.RequiredTextField
import com.pancholi.grabbag.ui.screen.modelaction.ActionViewModel
import com.pancholi.grabbag.ui.screen.modelaction.ModelAction
import com.pancholi.grabbag.ui.screen.modelaction.ModelActionScreenBase
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun NpcActionScreen(
    modelAction: ModelAction,
    title: String,
    onBackPressed: (CategoryModel?, CategoryModel) -> Unit,
    onBackConfirmed: () -> Unit,
    onSaveClicked: (CategoryModel.Npc, ModelAction) -> Unit,
    onModelSaved: () -> Unit,
    onDialogDismissed: () -> Unit,
    onRaceChanged: (String) -> Unit,
    onClassChanged: (String) -> Unit,
    onProfessionChanged: (String) -> Unit,
    viewState: ActionViewModel.ViewState,
    npcSaved: SharedFlow<Unit>
) {
    var npc: CategoryModel.Npc? = null
    var showContent = modelAction == ModelAction.ADD

    if (modelAction == ModelAction.EDIT) {
        when (viewState.modelToEdit) {
            Result.Loading -> LoadingIndicator()
            is Result.Success<*> -> {
                npc = viewState.modelToEdit.value as CategoryModel.Npc
                showContent = true
            }
            is Result.Error -> {}
        }
    }

    if (showContent) {
        ActionContent(
            modelAction = modelAction,
            title = title,
            onBackPressed = onBackPressed,
            onBackConfirmed = onBackConfirmed,
            onSaveClicked = onSaveClicked,
            onModelSaved = onModelSaved,
            onDialogDismissed = onDialogDismissed,
            onRaceChanged = onRaceChanged,
            onClassChanged = onClassChanged,
            onProfessionChanged = onProfessionChanged,
            viewState = viewState,
            npcSaved = npcSaved,
            npc = npc
        )
    }
}

@Composable
private fun ActionContent(
    modelAction: ModelAction,
    title: String,
    onBackPressed: (CategoryModel?, CategoryModel) -> Unit,
    onBackConfirmed: () -> Unit,
    onSaveClicked: (CategoryModel.Npc, ModelAction) -> Unit,
    onModelSaved: () -> Unit,
    onDialogDismissed: () -> Unit,
    onRaceChanged: (String) -> Unit,
    onClassChanged: (String) -> Unit,
    onProfessionChanged: (String) -> Unit,
    viewState: ActionViewModel.ViewState,
    npcSaved: SharedFlow<Unit>,
    npc: CategoryModel.Npc?
) {
    var name by rememberSaveable { mutableStateOf(npc?.name.orEmpty()) }
    var race by rememberSaveable { mutableStateOf(npc?.race.orEmpty()) }
    var gender by rememberSaveable { mutableStateOf(npc?.gender.orEmpty()) }
    var clss by rememberSaveable { mutableStateOf(npc?.clss.orEmpty()) }
    var profession by rememberSaveable { mutableStateOf(npc?.profession.orEmpty()) }
    var description by rememberSaveable { mutableStateOf(npc?.description.orEmpty()) }

    ModelActionScreenBase(
        title = title,
        modelSaved = npcSaved,
        onBackPressed = {
            val newModel = CategoryModel.Npc(
                id = npc?.id ?: 0,
                name = name,
                race = race,
                gender = gender,
                clss = clss,
                profession = profession,
                description = description
            )

            onBackPressed(npc, newModel)
        },
        onSaveClicked = {
            val model = CategoryModel.Npc(
                id = npc?.id ?: 0,
                name = name,
                race = race,
                gender = gender,
                clss = clss,
                profession = profession,
                description = description
            )

            onSaveClicked(model, modelAction)
        },
        onModelSaved = onModelSaved
    ) {
        val requiredSupportingText: @Composable (String) -> Unit = { text ->
            if (viewState.showRequiredSupportingText && text.isEmpty()) {
                RequiredTextField()
            }
        }

        PropertyTextField(
            label = stringResource(id = R.string.name),
            onValueChangeAction = { name = it },
            startingText = npc?.name.orEmpty(),
            supportingText = requiredSupportingText
        )

//        PropertyTextField(
//            label = stringResource(id = R.string.race),
//            onValueChangeAction = { race = it },
//            startingText = npc?.race.orEmpty(),
//            supportingText = requiredSupportingText
//        )

        AutocompletePropertyField(
            label = stringResource(id = R.string.race),
            onValueChangeAction = {
                race = it
                onRaceChanged(it)
            },
            startingText = npc?.race.orEmpty(),
            supportingText = requiredSupportingText,
            filteredOptions = viewState.filteredOptions
        )

        PropertyTextField(
            label = stringResource(id = R.string.gender),
            onValueChangeAction = { gender = it },
            startingText = npc?.gender.orEmpty(),
            supportingText = requiredSupportingText
        )

//        PropertyTextField(
//            label = stringResource(id = R.string.clss),
//            onValueChangeAction = { clss = it },
//            startingText = npc?.clss.orEmpty(),
//            supportingText = { OptionalTextField() }
//        )

        AutocompletePropertyField(
            label = stringResource(id = R.string.clss),
            onValueChangeAction = {
                clss = it
                onClassChanged(it)
            },
            startingText = npc?.clss.orEmpty(),
            supportingText = { OptionalTextField() },
            filteredOptions = viewState.filteredOptions
        )

        AutocompletePropertyField(
            label = stringResource(id = R.string.profession),
            onValueChangeAction = {
                profession = it
                onProfessionChanged(it)
            },
            startingText = npc?.profession.orEmpty(),
            supportingText = { OptionalTextField() },
            filteredOptions = viewState.filteredOptions
        )

//        PropertyTextField(
//            label = stringResource(id = R.string.profession),
//            onValueChangeAction = { profession = it },
//            startingText = npc?.profession.orEmpty(),
//            supportingText = { OptionalTextField() }
//        )

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueChangeAction = { description = it },
            startingText = npc?.description.orEmpty(),
            supportingText = { OptionalTextField() }
        )
    }

    viewState.discardConfirmationDialog?.let { messageId ->
        ConfirmationDialog(
            model = null,
            messageId = messageId,
            confirmTextId = R.string.discard,
            onConfirmClicked = { onBackConfirmed() },
            onDismissRequest = onDialogDismissed
        )
    }
}