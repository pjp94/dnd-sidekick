package com.pancholi.grabbag.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.navigation.Category

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun Message(
    message: String
) {
    val color = if (isSystemInDarkTheme()) {
        colorResource(id = R.color.dark_message_on_background)
    } else {
        colorResource(id = R.color.light_message_on_background)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = message,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = color,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AddButton(
    category: Category,
    onAddClicked: (Action) -> Unit,
    modifier: Modifier
) {
    FloatingActionButton(
        modifier = modifier,
        content = {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(id = R.string.add_icon_description)
            )
        },
        onClick = { onAddClicked(category.addAction) },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onSurface
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackableScreen(
    title: String,
    onBackPressed: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    innerContent: @Composable (PaddingValues) -> Unit,
) {
    BackHandler { onBackPressed() }

    Scaffold(
        topBar = {
            Surface {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        BackButton(
                            onClick = onBackPressed
                        )
                    },
                    actions = actions,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    ) { innerPadding -> innerContent(innerPadding) }
}

@Composable
fun BackButton(
    onClick: () -> Unit
) {
    var handled by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            if (handled.not()) {
                handled = true
                onClick()
            }
        }
    ) {

        Icon(
            painter = painterResource(id = R.drawable.icon_back),
            contentDescription = stringResource(id = R.string.back_icon_description)
        )
    }
}

@Composable
fun PropertyTextField(
    label: String,
    modifier: Modifier = Modifier,
    onValueChangeAction: (String) -> Unit = {},
    supportingText: @Composable ((String) -> Unit) = {},
    numberOnly: Boolean = false,
) {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        singleLine = true,
        onValueChange = {
            onValueChangeAction(it)
            text = it
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            keyboardType = if (numberOnly) {
                KeyboardType.Number
            } else {
                KeyboardType.Text
            }
        ),
        supportingText = { supportingText(text) },
        modifier = modifier
    )
}

@Composable
fun PropertyTextBox(
    label: String,
    onValueChangeAction: (String) -> Unit = {},
    supportingText: @Composable ((String) -> Unit) = {}
) {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueChangeAction(it)
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences
        ),
        supportingText = { supportingText(text) },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun OptionalTextField() {
    Text(
        text = stringResource(id = R.string.optional),
        style = TextStyle(
            fontStyle = FontStyle.Italic
        )
    )
}

@Composable
fun RequiredTextField() {
    Text(
        text = stringResource(id = R.string.required),
        style = TextStyle(
            fontStyle = FontStyle.Italic
        ),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun TopBarButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}

@Composable
fun ConfirmDeleteDialog(
    name: String,
    onConfirmDeleteClicked: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(id = R.string.delete_dialog_title, name)
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.delete_dialog_message)
            )
       },
        confirmButton = {
            TextButton(
                onClick = onConfirmDeleteClicked
            ) {
                Text(
                    text = stringResource(id = R.string.delete)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(
                    text = stringResource(id = R.string.cancel)
                )
            }
        }
    )
}

@Composable
fun FullScreenDialogColumn(
    innerPadding: PaddingValues,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .wrapContentSize()
            .padding(innerPadding)
            .padding(horizontal = 24.dp)
            .padding(vertical = 8.dp)
    ) {
        content()
    }
}