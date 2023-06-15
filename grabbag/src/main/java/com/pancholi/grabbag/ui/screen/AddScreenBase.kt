package com.pancholi.grabbag.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pancholi.grabbag.R
import com.pancholi.grabbag.ui.BackableScreen
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun AddScreenBase(
    title: String,
    modelSaved: SharedFlow<Unit>,
    onBackPressed: () -> Unit,
    onSaveClicked: () -> Unit,
    innerContent: @Composable () -> Unit
) {
    BackableScreen(
        title = title,
        onBackPressed = onBackPressed,
        actions = { TopBarSaveAction(onSaveClicked = onSaveClicked) },
        innerContent = { innerPadding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                innerContent()

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    )

    LaunchedEffect(Unit) {
        modelSaved.collect { onBackPressed() }
    }
}

@Composable
fun TopBarSaveAction(
    onSaveClicked: () -> Unit
) {
    IconButton(
        onClick = { onSaveClicked() }
    ) {
        Icon(
            imageVector = Icons.Filled.Done,
            contentDescription = stringResource(id = R.string.save_icon_description)
        )
    }
}