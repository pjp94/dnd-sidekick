package com.pancholi.grabbag.ui.screen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.core.Result
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.AddButton
import com.pancholi.grabbag.ui.BackableScreen
import com.pancholi.grabbag.ui.LoadingIndicator
import com.pancholi.grabbag.ui.Message
import com.pancholi.grabbag.ui.screen.item.ItemCard
import com.pancholi.grabbag.ui.screen.item.ItemDialog
import com.pancholi.grabbag.ui.screen.location.LocationCard
import com.pancholi.grabbag.ui.screen.location.LocationDialog
import com.pancholi.grabbag.ui.screen.modelaction.ActionViewModel
import com.pancholi.grabbag.ui.screen.npc.NpcCard
import com.pancholi.grabbag.ui.screen.npc.NpcDialog
import com.pancholi.grabbag.ui.screen.shop.ShopCard
import com.pancholi.grabbag.ui.screen.shop.ShopDialog
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CategoryScreen(
    category: Category,
    title: String,
    showSnackbar: (SidekickSnackbarVisuals) -> Unit,
    errorMessage: String,
    viewModel: CategoryViewModel,
    actionViewModel: ActionViewModel,
    onBackPressed: () -> Unit,
    onNavigateAction: (Action, String?) -> Unit
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle()
    val viewState = state.value

    LaunchedEffect(Unit) {
        viewModel.snackbarVisuals.collectLatest {
            showSnackbar(it)
        }
    }

    LaunchedEffect(Unit) {
        actionViewModel.addSnackbarVisuals.collectLatest {
            showSnackbar(it)
        }
    }

    BackableScreen(
        title = title,
        backSingleClick = true,
        onBackPressed = onBackPressed
    ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                CategoryBody(
                    category = category,
                    result = viewState.result,
                    errorMessage = errorMessage,
                    onCardClicked = { viewModel.onShowDetailDialog(it) },
                    showDetailDialogForModel = viewState.showDetailDialogForModel,
                    showDeleteDialogForModel = viewState.showDeleteConfirmation,
                    onDismissRequest = { viewModel.onDetailDialogDismissed() },
                    onDeleteClicked = { viewModel.onDeleteClicked() },
                    onConfirmDeleteClicked = { model ->
                        viewModel.apply {
                            model?.let { onDeleteModel(model) }
                            onDeleteDialogDismissed()
                            onDetailDialogDismissed()
                        }
                    },
                    onDeleteDialogDismissed = { viewModel.onDeleteDialogDismissed() },
                    onEditClicked = onNavigateAction
                )

                AddButton(
                    category = category,
                    onAddClicked = onNavigateAction,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
            }
        }
}

@Suppress("UNCHECKED_CAST")
@Composable
fun CategoryBody(
    category: Category,
    result: Result,
    errorMessage: String,
    onCardClicked: (CategoryModel) -> Unit,
    showDetailDialogForModel: CategoryModel?,
    showDeleteDialogForModel: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteClicked: () -> Unit,
    onConfirmDeleteClicked: (CategoryModel?) -> Unit,
    onDeleteDialogDismissed: () -> Unit,
    onEditClicked: (Action, String?) -> Unit
) {
    when (result) {
        is Result.Loading -> LoadingIndicator()
        is Result.Success<*> -> {
            val models = result.value as List<CategoryModel>

            CardColumn(
                category = category,
                models = models,
                onCardClicked = onCardClicked,
                showDetailDialogForModel = showDetailDialogForModel,
                showDeleteDialogForModel = showDeleteDialogForModel,
                onDismissRequest = onDismissRequest,
                onDeleteClicked = onDeleteClicked,
                onConfirmDeleteClicked = onConfirmDeleteClicked,
                onDeleteDialogDismissed = onDeleteDialogDismissed,
                onEditClicked = onEditClicked
            )
        }
        is Result.Error -> {
            when (result.throwable) {
                is EmptyDatabaseException -> Message(errorMessage)
                else -> {}
            }
        }
    }
}

@Composable
fun CardColumn(
    category: Category,
    models: List<CategoryModel>,
    onCardClicked: (CategoryModel) -> Unit,
    showDetailDialogForModel: CategoryModel?,
    showDeleteDialogForModel: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteClicked: () -> Unit,
    onConfirmDeleteClicked: (CategoryModel?) -> Unit,
    onDeleteDialogDismissed: () -> Unit,
    onEditClicked: (Action, String?) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        models.forEach { model ->
            CategoryCard(
                category = category,
                model = model,
                onCardClicked = onCardClicked
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }

    showDetailDialogForModel?.let {
        ModelDialog(
            category = category,
            model = it,
            showDeleteDialogForModel = showDeleteDialogForModel,
            onDismissRequest = onDismissRequest,
            onDeleteClicked = onDeleteClicked,
            onConfirmDeleteClicked = onConfirmDeleteClicked,
            onDeleteDialogDismissed = onDeleteDialogDismissed,
            onEditClicked = onEditClicked
        )
    }
}

@Composable
fun CategoryCard(
    category: Category,
    model: CategoryModel,
    onCardClicked: (CategoryModel) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onCardClicked(model) }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                when (category) {
                    Category.NPC -> NpcCard(npc = model as CategoryModel.Npc)
                    Category.SHOP -> ShopCard(shop = model as CategoryModel.Shop)
                    Category.LOCATION -> LocationCard(location = model as CategoryModel.Location)
                    Category.ITEM -> ItemCard(item = model as CategoryModel.Item)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .align(Alignment.BottomStart)
                    .background(
                        brush = Brush.verticalGradient(
                            0.0F to Color.Transparent,
                            1.0F to MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
            )
        }
    }
}

@Composable
fun CardPropertyRow(
    label: String,
    text: String,
    singleField: Boolean = false
) {
    if (singleField) {
        val spanStyles = listOf(
            AnnotatedString.Range(
                item = SpanStyle(fontWeight = FontWeight.SemiBold),
                start = 0,
                end = label.length + 1
            )
        )

        Text(
            text = AnnotatedString(
                text = "$label: $text",
                spanStyles = spanStyles
            )
        )
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "$label:",
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = text,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun ModelDialog(
    category: Category,
    model: CategoryModel,
    showDeleteDialogForModel: Boolean,
    onDismissRequest: () -> Unit,
    onDeleteClicked: () -> Unit,
    onConfirmDeleteClicked: (CategoryModel?) -> Unit,
    onDeleteDialogDismissed: () -> Unit,
    onEditClicked: (Action, String?) -> Unit
) {
    val name: String
    val content: @Composable (PaddingValues) -> Unit

    when (category) {
        Category.NPC -> {
            val npc = model as CategoryModel.Npc
            name = npc.name
            content = {
                NpcDialog(
                    npc = npc,
                    innerPadding = it
                )
            }
        }
        Category.SHOP -> {
            val shop = model as CategoryModel.Shop
            name = shop.name
            content = {
                ShopDialog(
                    shop = shop,
                    innerPadding = it
                )
            }
        }
        Category.LOCATION -> {
            val location = model as CategoryModel.Location
            name = location.name
            content = {
                LocationDialog(
                    location = location,
                    innerPadding = it
                )
            }
        }
        Category.ITEM -> {
            val item = model as CategoryModel.Item
            name = item.name
            content = {
                ItemDialog(
                    item = item,
                    innerPadding = it
                )
            }
        }
    }

    CategoryDialog(
        category = category,
        model = model,
        name = name,
        content = content,
        showDeleteDialogForModel = showDeleteDialogForModel,
        onDismissRequest = onDismissRequest,
        onDeleteClicked = onDeleteClicked,
        onConfirmDeleteClicked = onConfirmDeleteClicked,
        onDeleteDialogDismissed = onDeleteDialogDismissed,
        onEditClicked = onEditClicked
    )
}