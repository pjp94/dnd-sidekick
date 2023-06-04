package com.pancholi.grabbag.ui.screen.npc

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.grabbag.R
import com.pancholi.grabbag.ui.OptionalTextField
import com.pancholi.grabbag.ui.PropertyTextBox
import com.pancholi.grabbag.ui.PropertyTextField
import com.pancholi.grabbag.ui.RequiredTextField
import com.pancholi.grabbag.ui.screen.AddScreenBase

@Composable
fun AddNpcScreen(
    title: String,
    onBackPressed: () -> Unit,
    viewModel: NpcViewModel = hiltViewModel()
) {
    val showRequired = viewModel.showRequired.collectAsStateWithLifecycle()
    val npcSaved = viewModel.npcSaved.collectAsStateWithLifecycle()

    if (npcSaved.value) {
        onBackPressed()
    }

    AddScreenBase(
        title = title,
        onBackPressed = onBackPressed,
        onSaveClicked = { viewModel.onSaveClicked() }
    ) {
        val requiredSupportingText: @Composable (String) -> Unit = { text ->
            if (showRequired.value && text.isEmpty()) {
                RequiredTextField()
            }
        }

        PropertyTextField(
            label = stringResource(id = R.string.name),
            onValueAction = { text -> viewModel.setName(text) },
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.race),
            onValueAction = { text -> viewModel.setRace(text) },
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.gender),
            onValueAction = { text -> viewModel.setGender(text) },
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.clss),
            onValueAction = { text -> viewModel.setClass(text) },
            supportingText = { OptionalTextField() }
        )

        PropertyTextField(
            label = stringResource(id = R.string.profession),
            onValueAction = { text -> viewModel.setProfession(text) },
            supportingText = { OptionalTextField() }
        )

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueAction = { text -> viewModel.setDescription(text) },
            supportingText = { OptionalTextField() }
        )
    }
}