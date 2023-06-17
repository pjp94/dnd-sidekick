package com.pancholi.grabbag.ui.screen.location

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.pancholi.core.Result
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
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
fun LocationActionScreen(
    modelAction: ModelAction,
    title: String,
    onBackPressed: (CategoryModel?, CategoryModel) -> Unit,
    onBackConfirmed: () -> Unit,
    onSaveClicked: (CategoryModel.Location, ModelAction) -> Unit,
    onModelSaved: () -> Unit,
    onDialogDismissed: () -> Unit,
    viewState: ActionViewModel.ViewState,
    locationSaved: SharedFlow<Unit>
) {
    var location: CategoryModel.Location? = null
    var showContent = modelAction == ModelAction.ADD

    if (modelAction == ModelAction.EDIT) {
        when (viewState.modelToEdit) {
            Result.Loading -> LoadingIndicator()
            is Result.Success<*> -> {
                location = viewState.modelToEdit.value as CategoryModel.Location
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
            viewState = viewState,
            locationSaved = locationSaved,
            location = location
        )
    }
}

@Composable
private fun ActionContent(
    modelAction: ModelAction,
    title: String,
    onBackPressed: (CategoryModel?, CategoryModel) -> Unit,
    onBackConfirmed: () -> Unit,
    onSaveClicked: (CategoryModel.Location, ModelAction) -> Unit,
    onModelSaved: () -> Unit,
    onDialogDismissed: () -> Unit,
    viewState: ActionViewModel.ViewState,
    locationSaved: SharedFlow<Unit>,
    location: CategoryModel.Location?
) {
    var name by rememberSaveable { mutableStateOf(location?.name.orEmpty()) }
    var type by rememberSaveable { mutableStateOf(location?.type.orEmpty()) }
    var description by rememberSaveable { mutableStateOf(location?.description.orEmpty()) }

    ModelActionScreenBase(
        title = title,
        modelSaved = locationSaved,
        onBackPressed = {
            val newModel = CategoryModel.Location(
                id = location?.id ?: 0,
                name = name,
                type = type,
                description = description
            )

            onBackPressed(location, newModel)
        },
        onSaveClicked = {
            val model = CategoryModel.Location(
                id = location?.id ?: 0,
                name = name,
                type = type,
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
            startingText = location?.name.orEmpty(),
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.type),
            onValueChangeAction = { type = it },
            startingText = location?.type.orEmpty(),
            supportingText = requiredSupportingText
        )

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueChangeAction = { description = it },
            startingText = location?.description.orEmpty(),
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