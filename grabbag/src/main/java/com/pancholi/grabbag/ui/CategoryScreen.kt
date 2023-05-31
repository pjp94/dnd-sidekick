package com.pancholi.grabbag.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.core.Result
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.viewmodel.CategoryViewModel

@Composable
fun CategoryScreen(
    title: String,
    errorMessage: String,
    viewModel: CategoryViewModel,
    onBackPressed: () -> Unit
) {
    BackableScreen(
        title = title,
        onBackPressed = onBackPressed,
        innerContent = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                val state = viewModel.viewState.collectAsStateWithLifecycle()

                CategoryBody(
                    result = state.value,
                    errorMessage = errorMessage
                )

                AddItemButton(
                    onAddClicked = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
            }
        }
    )
}

@Composable
fun CategoryBody(
    result: Result,
    errorMessage: String
) {
    when (result) {
        is Result.Loading -> LoadingIndicator()
        is Result.Success<*> -> {
            val viewState = (result.value as Result.Success<*>).value as CategoryViewModel.ViewState<*>

        }
        is Result.Error -> {
            when (result.throwable) {
                is EmptyDatabaseException -> Message(errorMessage)
                else -> {}
            }
        }
    }
}