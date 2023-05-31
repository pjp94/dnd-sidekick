package com.pancholi.grabbag.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
        onClick = { onAddClicked() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackableScreen(
    title: String,
    onBackPressed: () -> Unit,
    innerContent: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    BackButton(
                        onClick = onBackPressed
                    )
                }
            )
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
            contentDescription = stringResource(id = R.string.back_icon_description)
        )
    }
}