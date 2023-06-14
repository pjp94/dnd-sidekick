package com.pancholi.grabbag.ui.screen.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.ui.OptionalTextField
import com.pancholi.grabbag.ui.PropertyTextBox
import com.pancholi.grabbag.ui.PropertyTextField
import com.pancholi.grabbag.ui.RequiredTextField
import com.pancholi.grabbag.ui.screen.AddScreenBase

@Composable
fun AddLocationScreen(
    title: String,
    onBackPressed: () -> Unit,
    onLocationSaved: () -> Unit,
    onSaveClicked: (CategoryModel.Location) -> Unit,
    locationViewState: LocationViewModel.LocationViewState
) {
    var saveHandled by rememberSaveable { mutableStateOf(false) }

    if (locationViewState.locationSaved && saveHandled.not()) {
        saveHandled = true
        onLocationSaved()
    }

    var name by rememberSaveable { mutableStateOf("") }
    var type by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    AddScreenBase(
        title = title,
        onBackPressed = onBackPressed,
        onSaveClicked = {
            val location = CategoryModel.Location(
                name = name,
                type = type,
                description = description
            )

            onSaveClicked(location)
        }
    ) {
        val requiredSupportingText: @Composable (String) -> Unit = { text ->
            if (locationViewState.showRequired && text.isEmpty()) {
                RequiredTextField()
            }
        }

        PropertyTextField(
            label = stringResource(id = R.string.name),
            onValueChangeAction = { name = it },
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.type),
            onValueChangeAction = { type = it },
            supportingText = requiredSupportingText
        )

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueChangeAction = { description = it },
            supportingText = { OptionalTextField() }
        )
    }
}