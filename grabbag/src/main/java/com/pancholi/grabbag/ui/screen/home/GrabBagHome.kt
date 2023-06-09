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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Category
import kotlinx.coroutines.flow.collectLatest

@Composable
fun GrabBagHome(
    viewModel: GrabBagHomeViewModel = hiltViewModel(),
    showSnackbar: (SidekickSnackbarVisuals) -> Unit,
    categories: List<Category>,
    onCategoryClicked: (Category) -> Unit,
    onSuggestClicked: () -> Unit
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

    LaunchedEffect(Unit) {
        viewModel.snackbarVisuals.collectLatest {
            showSnackbar(it)
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
//                    .padding(top = 24.dp)
            )

            SuggestButton(
                onSuggestClicked = onSuggestClicked,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun CategoryButtonLayout(
    categories: List<Category>,
    onCategoryClicked: (Category) -> Unit
) {
//    for (i in 0..categories.lastIndex step 2) {
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(8.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 8.dp)
//        ) {
//            val category1 = categories[i]
//            val category2 = if (i + 1 <= categories.lastIndex) {
//                categories[i + 1]
//            } else {
//                null
//            }
//
//            val maxWidthFraction = category2?.let { 0.5f } ?: 1.0f
//
//            CategoryButton(
//                category = category1,
//                onCategoryClicked = { onCategoryClicked(category1) },
//                modifier = Modifier
//                    .fillMaxWidth(maxWidthFraction)
//                    .weight(1.0f)
//            )
//
//            category2?.let { category ->
//                CategoryButton(
//                    category = category,
//                    onCategoryClicked = { onCategoryClicked(category) },
//                    modifier = Modifier
//                        .fillMaxWidth(maxWidthFraction)
//                        .weight(1.0f)
//                )
//            }
//        }
//    }
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
    Button(
        onClick = { onCategoryClicked(category) },
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer // TODO change to Color.White?
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
            text = stringResource(id = R.string.import_content),
//            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun SuggestButton(
    onSuggestClicked: () -> Unit,
    modifier: Modifier
) {
    OutlinedButton(
        onClick = onSuggestClicked,
//        colors = ButtonDefaults.buttonColors(
//            contentColor = MaterialTheme.colorScheme.onTertiaryContainer // TODO change to Color.White?
//        ),
        modifier = modifier,
    ) {
        Text(
            text = stringResource(id = R.string.suggest),
//            modifier = Modifier.padding(8.dp)
        )
    }
}