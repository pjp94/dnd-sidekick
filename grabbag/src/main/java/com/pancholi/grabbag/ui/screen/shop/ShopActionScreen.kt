package com.pancholi.grabbag.ui.screen.shop

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
fun ShopActionScreen(
    modelAction: ModelAction,
    title: String,
    onBackPressed: (CategoryModel?, CategoryModel) -> Unit,
    onBackConfirmed: () -> Unit,
    onSaveClicked: (CategoryModel.Shop, ModelAction) -> Unit,
    onModelSaved: () -> Unit,
    onDialogDismissed: () -> Unit,
    viewState: ActionViewModel.ViewState,
    shopSaved: SharedFlow<Unit>
) {
    var shop: CategoryModel.Shop? = null
    var showContent = modelAction == ModelAction.ADD

    if (modelAction == ModelAction.EDIT) {
        when (viewState.modelToEdit) {
            Result.Loading -> LoadingIndicator()
            is Result.Success<*> -> {
                shop = viewState.modelToEdit.value as CategoryModel.Shop
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
            shopSaved = shopSaved,
            shop = shop
        )
    }
}

@Composable
private fun ActionContent(
    modelAction: ModelAction,
    title: String,
    onBackPressed: (CategoryModel?, CategoryModel) -> Unit,
    onBackConfirmed: () -> Unit,
    onSaveClicked: (CategoryModel.Shop, ModelAction) -> Unit,
    onModelSaved: () -> Unit,
    onDialogDismissed: () -> Unit,
    viewState: ActionViewModel.ViewState,
    shopSaved: SharedFlow<Unit>,
    shop: CategoryModel.Shop?
) {
    var name by rememberSaveable { mutableStateOf(shop?.name.orEmpty()) }
    var type by rememberSaveable { mutableStateOf(shop?.type.orEmpty()) }
    var owner by rememberSaveable { mutableStateOf(shop?.owner.orEmpty()) }
    var description by rememberSaveable { mutableStateOf(shop?.description.orEmpty()) }

    ModelActionScreenBase(
        title = title,
        modelSaved = shopSaved,
        onBackPressed = {
            val newModel = CategoryModel.Shop(
                id = shop?.id ?: 0,
                name = name,
                type = type,
                owner = owner,
                description = description
            )

            onBackPressed(shop, newModel)
        },
        onSaveClicked = {
            val model = CategoryModel.Shop(
                id = shop?.id ?: 0,
                name = name,
                type = type,
                owner = owner,
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
            startingText = shop?.name.orEmpty(),
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.type),
            onValueChangeAction = { type = it },
            startingText = shop?.type.orEmpty(),
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.owner),
            onValueChangeAction = { owner = it },
            startingText = shop?.owner.orEmpty(),
            supportingText = { OptionalTextField() }
        )

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueChangeAction = { description = it },
            startingText = shop?.description.orEmpty(),
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