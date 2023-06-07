package com.pancholi.grabbag.ui.screen.npc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.Npc
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

    var name by rememberSaveable { mutableStateOf("") }
    var race by rememberSaveable { mutableStateOf("") }
    var gender by rememberSaveable { mutableStateOf("") }
    var clss by rememberSaveable { mutableStateOf("") }
    var profession by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    AddScreenBase(
        title = title,
        onBackPressed = onBackPressed,
        onSaveClicked = {
            val npc = Npc(
                name = name,
                race = race,
                gender = gender,
                clss = clss,
                profession = profession,
                description = description
            )

            viewModel.onSaveClicked(npc)
        }
    ) {
        val requiredSupportingText: @Composable (String) -> Unit = { text ->
            if (showRequired.value && text.isEmpty()) {
                RequiredTextField()
            }
        }

        PropertyTextField(
            label = stringResource(id = R.string.name),
            onValueChangeAction = { name = it },
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.race),
            onValueChangeAction = { race = it },
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.gender),
            onValueChangeAction = { gender = it },
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.clss),
            onValueChangeAction = { clss = it },
            supportingText = { OptionalTextField() }
        )

        PropertyTextField(
            label = stringResource(id = R.string.profession),
            onValueChangeAction = { profession = it },
            supportingText = { OptionalTextField() }
        )

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueChangeAction = { description = it },
            supportingText = { OptionalTextField() }
        )
    }
}