package com.pancholi.grabbag.ui.screen.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.viewmodel.GrabBagHomeViewModel

@Composable
fun GrabBagHome(
    viewModel: GrabBagHomeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    categories: List<Category>,
    onCategoryClicked: (Category) -> Unit
) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.Center)
        ) {
            CategoryButtonLayout(
                categories = categories,
                onCategoryClicked = onCategoryClicked
            )

            ImportContentButton(
                snackbarHostState = snackbarHostState,
                viewState = viewState.value,
                onImportContentClicked = { viewModel.onImportContentClicked() },
                onFilePickerClosed = { viewModel.onFilePickerClosed() },
                onFilePicked = { viewModel.onFilePicked(it) },
                onImportMessageShown = { viewModel.onImportMessageShown() }
            )
        }
    }
}

@Composable
fun CategoryButtonLayout(
    categories: List<Category>,
    onCategoryClicked: (Category) -> Unit,
) {
    categories.forEach { category ->
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f),
            onClick = { onCategoryClicked(category) },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.red)
            )
        ) {
            Text(
                text = stringResource(id = category.nameId),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ImportContentButton(
    snackbarHostState: SnackbarHostState,
    viewState: GrabBagHomeViewModel.ViewState,
    onImportContentClicked: () -> Unit,
    onFilePickerClosed: () -> Unit,
    onFilePicked: (Uri) -> Unit,
    onImportMessageShown: () -> Unit
) {
    val result = rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        result.value = it
        onFilePickerClosed()
    }

    OutlinedButton(
        onClick = onImportContentClicked,
        modifier = Modifier.fillMaxWidth(0.5f),
    ) {
        Text(
            text = stringResource(id = R.string.import_content)
        )
    }

    if (viewState.openFilePicker.isNotEmpty()) {
        launcher.launch(viewState.openFilePicker.toTypedArray())
    }

    result.value?.let {
        onFilePicked(it)
        result.value = null
    }

    viewState.importSnackbarVisuals?.let {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(visuals = it)
            onImportMessageShown()
        }
    }
}