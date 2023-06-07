package com.pancholi.grabbag.ui.screen.shop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.Shop
import com.pancholi.grabbag.ui.screen.CardPropertyRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopDialog(
    shop: Shop,
    onDismissRequest: () -> Unit
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
                        title = { Text(shop.name) },
                        navigationIcon = {
                            IconButton(
                                onClick = { onDismissRequest() }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Close Button"
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "")
                            }

                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .wrapContentSize()
                        .padding(innerPadding)
                        .padding(8.dp)
                ) {
                    CardPropertyRow(
                        label = stringResource(id = R.string.type),
                        text = shop.type
                    )

                    CardPropertyRow(
                        label = stringResource(id = R.string.owner),
                        text = shop.owner
                    )

                    CardPropertyRow(
                        label = stringResource(id = R.string.description),
                        text = shop.description,
                        singleField = true
                    )
                }
            }
        }
    }
}