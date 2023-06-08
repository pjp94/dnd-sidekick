package com.pancholi.grabbag.ui.screen.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.pancholi.grabbag.model.CategoryModel
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

    var name by rememberSaveable { mutableStateOf("") }
    var type by rememberSaveable { mutableStateOf("") }
    var cost by rememberSaveable { mutableStateOf("") }
    var currency by rememberSaveable { mutableStateOf(Currency.GP) }
    var description by rememberSaveable { mutableStateOf("") }

    AddScreenBase(
        title = title,
        onBackPressed = onBackPressed,
        onSaveClicked = {
            val item = CategoryModel.Item(
                name = name,
                type = type,
                cost = cost,
                currency = currency,
                description = description
            )

            viewModel.onSaveClicked(item)
        }
    ) {
        Box(modifier = Modifier
            .width(OutlinedTextFieldDefaults.MinWidth)
        ) {
            val requiredSupportingText: @Composable (String) -> Unit = { text ->
                if (showRequired.value && text.isEmpty()) {
                    RequiredTextField()
                }
            }

            Column {
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

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    PropertyTextField(
                        label = stringResource(id = R.string.cost),
                        onValueChangeAction = { cost = it },
                        supportingText = { OptionalTextField() },
                        numberOnly = true,
                        modifier = Modifier.weight(0.6f)
                    )

                    CurrencyField(
                        onCurrencyChanged = { currency = it },
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 8.dp)
                            .weight(0.4f)
                    )
                }
            }
        }

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueChangeAction = { description = it },
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