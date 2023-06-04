package com.pancholi.grabbag.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.pancholi.grabbag.R

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
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = message,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AddItemButton(
    onAddClicked: () -> Unit,
    modifier: Modifier
) {
    FloatingActionButton(
        modifier = modifier,
        content = { Icon(imageVector = Icons.Filled.Add, contentDescription = "") },
        onClick = { onAddClicked() },
        containerColor = colorResource(id = R.color.red)
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
                        titleContentColor = Color.White,
                        containerColor = colorResource(id = R.color.red)
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
    IconButton(
        onClick = onClick
    ) {

        Icon(
            painterResource(id = R.drawable.icon_back),
            contentDescription = stringResource(id = R.string.back_icon_description),
            tint = Color.White
        )
    }
}

@Composable
fun PropertyTextField(
    label: String,
    onValueAction: (String) -> Unit = {},
    supportingText: @Composable ((String) -> Unit) = {},
    numberOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            onValueAction(it)
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
    onValueAction: (String) -> Unit = {},
    supportingText: @Composable ((String) -> Unit) = {}
) {
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onValueAction(it)
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
