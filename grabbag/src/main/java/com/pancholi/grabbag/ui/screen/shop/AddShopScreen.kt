package com.pancholi.grabbag.ui.screen.shop

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
fun AddShopScreen(
    title: String,
    onBackPressed: () -> Unit,
    viewModel: ShopViewModel = hiltViewModel()
) {
    val showRequired = viewModel.showRequired.collectAsStateWithLifecycle()
    val shopSaved = viewModel.shopSaved.collectAsStateWithLifecycle()

    if (shopSaved.value) {
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
            label = stringResource(id = R.string.type),
            onValueAction = { text -> viewModel.setType(text) },
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.owner),
            onValueAction = { text -> viewModel.setOwner(text) },
            supportingText = { OptionalTextField() }
        )

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueAction = { text -> viewModel.setDescription(text) },
            supportingText = { OptionalTextField() }
        )
    }
}