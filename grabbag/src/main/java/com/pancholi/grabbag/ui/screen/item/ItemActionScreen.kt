package com.pancholi.grabbag.ui.screen.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pancholi.core.Result
import com.pancholi.grabbag.R
import com.pancholi.grabbag.getOrNull
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.Currency
import com.pancholi.grabbag.ui.AutocompletePropertyField
import com.pancholi.grabbag.ui.ConfirmationDialog
import com.pancholi.grabbag.ui.ITEM_COST_FIELD_COMPACT_WEIGHT
import com.pancholi.grabbag.ui.ITEM_CURRENCY_FIELD_COMPACT_WEIGHT
import com.pancholi.grabbag.ui.LoadingIndicator
import com.pancholi.grabbag.ui.OptionalTextField
import com.pancholi.grabbag.ui.PropertyTextBox
import com.pancholi.grabbag.ui.PropertyTextField
import com.pancholi.grabbag.ui.RequiredTextField
import com.pancholi.grabbag.ui.TEXT_FIELD_COMPACT_WIDTH_FRACTION
import com.pancholi.grabbag.ui.screen.modelaction.ActionViewModel
import com.pancholi.grabbag.ui.screen.modelaction.ModelAction
import com.pancholi.grabbag.ui.screen.modelaction.ModelActionScreenBase
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ItemActionScreen(
    modelAction: ModelAction,
    title: String,
    onBackPressed: (CategoryModel?, CategoryModel) -> Unit,
    onBackConfirmed: () -> Unit,
    onSaveClicked: (CategoryModel.Item, ModelAction) -> Unit,
    onModelSaved: () -> Unit,
    onDialogDismissed: () -> Unit,
    onTypeChanged: (String) -> Unit,
    viewState: ActionViewModel.ViewState,
    itemSaved: SharedFlow<Unit>
) {
    var item: CategoryModel.Item? = null
    var showContent = modelAction == ModelAction.ADD

    if (modelAction == ModelAction.EDIT) {
        when (viewState.modelToEdit) {
            Result.Loading -> LoadingIndicator()
            is Result.Success<*> -> {
                item = viewState.modelToEdit.value as CategoryModel.Item
                showContent = true
            }
            is Result.Error -> {}
        }
    }

    if (showContent) {
        ActionScreen(
            modelAction = modelAction,
            title = title,
            onBackPressed = onBackPressed,
            onBackConfirmed = onBackConfirmed,
            onSaveClicked = onSaveClicked,
            onModelSaved = onModelSaved,
            onDialogDismissed = onDialogDismissed,
            onTypeChanged = onTypeChanged,
            viewState = viewState,
            itemSaved = itemSaved,
            item = item
        )
    }
}

@Composable
private fun ActionScreen(
    modelAction: ModelAction,
    title: String,
    onBackPressed: (CategoryModel?, CategoryModel) -> Unit,
    onBackConfirmed: () -> Unit,
    onSaveClicked: (CategoryModel.Item, ModelAction) -> Unit,
    onModelSaved: () -> Unit,
    onDialogDismissed: () -> Unit,
    onTypeChanged: (String) -> Unit,
    viewState: ActionViewModel.ViewState,
    itemSaved: SharedFlow<Unit>,
    item: CategoryModel.Item?
) {
    var name by rememberSaveable { mutableStateOf(item?.name.orEmpty()) }
    var type by rememberSaveable { mutableStateOf(item?.type.orEmpty()) }
    var cost by rememberSaveable { mutableStateOf(item?.cost.orEmpty()) }
    var currency by rememberSaveable { mutableStateOf(item?.currency ?: Currency.GP) }
    var description by rememberSaveable { mutableStateOf(item?.description.orEmpty()) }

    ModelActionScreenBase(
        title = title,
        modelSaved = itemSaved,
        onBackPressed =  {
            val newModel = CategoryModel.Item(
                id = item?.id ?: 0,
                name = name,
                type = type,
                cost = cost,
                currency = currency.getOrNull(cost),
                description = description
            )

            onBackPressed(item, newModel)
        },
        onSaveClicked = {
            val model = CategoryModel.Item(
                id = item?.id ?: 0,
                name = name,
                type = type,
                cost = cost,
                currency = currency,
                description = description
            )

            onSaveClicked(model, modelAction)
        },
        onModelSaved = onModelSaved
    ) {
        Box(modifier = Modifier
            .fillMaxWidth(TEXT_FIELD_COMPACT_WIDTH_FRACTION)
        ) {
            val requiredSupportingText: @Composable (String) -> Unit = { text ->
                if (viewState.showRequiredSupportingText && text.isEmpty()) {
                    RequiredTextField()
                }
            }

            Column {
                PropertyTextField(
                    label = stringResource(id = R.string.name),
                    onValueChangeAction = { name = it },
                    startingText = item?.name.orEmpty(),
                    supportingText = requiredSupportingText,
                    widthFaction = 1.0f
                )

                AutocompletePropertyField(
                    label = stringResource(id = R.string.type),
                    onValueChangeAction = {
                        type = it
                        onTypeChanged(it)
                    },
                    startingText = item?.type.orEmpty(),
                    supportingText = requiredSupportingText,
                    filteredOptions = viewState.filteredOptions,
                    widthFaction = 1.0f
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    PropertyTextField(
                        label = stringResource(id = R.string.cost),
                        onValueChangeAction = { cost = it },
                        startingText = item?.cost.orEmpty(),
                        supportingText = { OptionalTextField() },
                        numberOnly = true,
                        modifier = Modifier.weight(ITEM_COST_FIELD_COMPACT_WEIGHT)
                    )

                    CurrencyField(
                        startingCurrency = item?.currency ?: Currency.GP,
                        onCurrencyChanged = { currency = it },
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 8.dp)
                            .weight(ITEM_CURRENCY_FIELD_COMPACT_WEIGHT)
                    )
                }
            }
        }

        PropertyTextBox(
            label = stringResource(id = R.string.description),
            onValueChangeAction = { description = it },
            startingText = item?.description.orEmpty(),
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

@Composable
private fun CurrencyField(
    startingCurrency: Currency,
    onCurrencyChanged: (Currency) -> Unit,
    modifier: Modifier
) {
    var text by rememberSaveable { mutableStateOf(startingCurrency.name.lowercase()) }
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
                    onDismissRequest = { expanded = false }
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