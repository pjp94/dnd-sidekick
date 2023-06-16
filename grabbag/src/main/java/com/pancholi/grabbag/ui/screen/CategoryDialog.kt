package com.pancholi.grabbag.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.ConfirmationDialog
import com.pancholi.grabbag.ui.TopBarButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDialog(
    category: Category,
    model: CategoryModel,
    name: String,
    content: @Composable (PaddingValues) -> Unit,
    showDeleteDialogForModel: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteClicked: () -> Unit,
    onConfirmDeleteClicked: (CategoryModel?) -> Unit,
    onDeleteDialogDismissed: () -> Unit,
    onEditClicked: (Action, String?) -> Unit
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = name)
                        },
                        navigationIcon = {
                            TopBarButton(
                                onClick = onDismissRequest,
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(id = R.string.close_icon_description)
                            )
                        },
                        actions = {
                            TopBarButton(
                                onClick = onDeleteClicked,
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = stringResource(id = R.string.delete_icon_description)
                            )

                            TopBarButton(
                                onClick = { onEditClicked(category.editAction, model.id.toString()) },
                                imageVector = Icons.Filled.Edit,
                                contentDescription = stringResource(id = R.string.edit_icon_description)
                            )
                        },
                    )
                }
            ) { innerPadding ->
                content(innerPadding)
            }
        }
    }

    if (showDeleteDialogForModel) {
        ConfirmationDialog(
            model = model,
            title = { Text(text = stringResource(id = R.string.delete_dialog_title, name)) },
            messageId = R.string.delete_dialog_message,
            confirmTextId = R.string.delete,
            onConfirmClicked = onConfirmDeleteClicked,
            onDismissRequest = onDeleteDialogDismissed
        )
    }
}