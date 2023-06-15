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
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Category
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GrabBagHome(
    viewModel: GrabBagHomeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    categories: List<Category>,
    onCategoryClicked: (Category) -> Unit
) {
    val result = rememberSaveable { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        result.value = it
        viewModel.onFilePickerClosed()
    }

    result.value?.let {
        viewModel.onFilePicked(it)
        result.value = null
    }

    val viewState = viewModel.viewState.collectAsStateWithLifecycle()

    if (viewState.value.openFilePicker.isNotEmpty()) {
        launcher.launch(viewState.value.openFilePicker.toTypedArray())
    }

    LaunchedEffect(snackbarHostState) {
        viewModel.snackbarVisuals.collectLatest {
            snackbarHostState.showSnackbar(visuals = it)
        }
    }

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
                onImportContentClicked = { viewModel.onImportContentClicked() },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally)
            )

//            SuggestButton(
//                onSuggestClicked = { /*TODO*/ },
//                modifier = Modifier
//                    .fillMaxWidth(0.5f)
//                    .align(Alignment.CenterHorizontally)
//            )
        }
    }
}

@Composable
fun CategoryButtonLayout(
    categories: List<Category>,
    onCategoryClicked: (Category) -> Unit
) {
    categories.forEach { category ->
        CategoryButton(
            category = category,
            onCategoryClicked = { onCategoryClicked(category) },
            modifier = Modifier.fillMaxWidth(0.5f),
        )
    }
}

@Composable
fun CategoryButton(
    category: Category,
    onCategoryClicked: (Category) -> Unit,
    modifier: Modifier
) {
    FilledTonalButton(
        onClick = { onCategoryClicked(category) },
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White
        ),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = category.nameId),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ImportContentButton(
    onImportContentClicked: () -> Unit,
    modifier: Modifier
) {
    OutlinedButton(
        onClick = onImportContentClicked,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.import_content)
        )
    }
}

@Composable
fun SuggestButton(
    onSuggestClicked: () -> Unit,
    modifier: Modifier
) {
    Button(
        onClick = onSuggestClicked,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.suggest),
            modifier = Modifier.padding(8.dp)
        )
    }
}