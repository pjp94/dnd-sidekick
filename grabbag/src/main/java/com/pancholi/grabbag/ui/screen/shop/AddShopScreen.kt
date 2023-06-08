package com.pancholi.grabbag.ui.screen.shop

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
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

    var name by rememberSaveable { mutableStateOf("") }
    var type by rememberSaveable { mutableStateOf("") }
    var owner by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    AddScreenBase(
        title = title,
        onBackPressed = onBackPressed,
        onSaveClicked = {
            val shop = CategoryModel.Shop(
                name = name,
                type = type,
                owner = owner,
                description = description
            )

            viewModel.onSaveClicked(shop)
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
            label = stringResource(id = R.string.type),
            onValueChangeAction = { type = it },
            supportingText = requiredSupportingText
        )

        PropertyTextField(
            label = stringResource(id = R.string.owner),
            onValueChangeAction = { owner = it },
            supportingText = { OptionalTextField() }
        )

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueChangeAction = { description = it },
            supportingText = { OptionalTextField() }
        )
    }
}