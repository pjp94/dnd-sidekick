package com.pancholi.grabbag.ui.screen.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.Currency
import com.pancholi.grabbag.ui.OptionalTextField
import com.pancholi.grabbag.ui.PropertyTextBox
import com.pancholi.grabbag.ui.PropertyTextField
import com.pancholi.grabbag.ui.RequiredTextField
import com.pancholi.grabbag.ui.screen.AddScreenBase

@Composable
fun AddItemScreen(
    title: String,
    onBackPressed: () -> Unit,
    viewModel: ItemViewModel = hiltViewModel()
) {
    val showRequired = viewModel.showRequired.collectAsStateWithLifecycle()
    val itemSaved = viewModel.itemSaved.collectAsStateWithLifecycle()

    if (itemSaved.value) {
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            PropertyTextField(
                label = stringResource(id = R.string.cost),
                onValueAction = { text -> viewModel.setCost(text) },
                supportingText = { OptionalTextField() },
                numberOnly = true
            )

            CurrencyField(
                onCurrencyChanged = { viewModel.setCurrency(it) },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 8.dp)
            )
        }

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueAction = { text -> viewModel.setDescription(text) },
            supportingText = { OptionalTextField() }
        )
    }
}

@Composable
fun CurrencyField(
    onCurrencyChanged: (Currency) -> Unit,
    modifier: Modifier
) {
    var text by rememberSaveable { mutableStateOf(Currency.GP.name.lowercase()) }
    var expanded by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        trailingIcon = {
            IconButton(
                onClick = { expanded = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = stringResource(id = R.string.dropdown_icon_description)
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(colorResource(id = R.color.light_gray)),
                ) {
                    Currency.values().forEach {
                        val value = it.name.lowercase()

                        DropdownMenuItem(
                            text = { Text(text = value) },
                            onClick = {
                                onCurrencyChanged(it)
                                text = value
                                expanded = false
                            }
                        )
                    }
                }
            }
        },
        readOnly = true,
        modifier = modifier
    )
}