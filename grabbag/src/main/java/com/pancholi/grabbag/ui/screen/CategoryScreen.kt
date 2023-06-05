package com.pancholi.grabbag.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pancholi.core.Result
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.Item
import com.pancholi.grabbag.model.Location
import com.pancholi.grabbag.model.Npc
import com.pancholi.grabbag.model.Shop
import com.pancholi.grabbag.navigation.Action
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.AddButton
import com.pancholi.grabbag.ui.BackableScreen
import com.pancholi.grabbag.ui.LoadingIndicator
import com.pancholi.grabbag.ui.Message
import com.pancholi.grabbag.ui.screen.item.ItemCard
import com.pancholi.grabbag.ui.screen.location.LocationCard
import com.pancholi.grabbag.ui.screen.npc.NpcCard
import com.pancholi.grabbag.ui.screen.shop.ShopCard
import com.pancholi.grabbag.viewmodel.CategoryViewModel

@Composable
fun CategoryScreen(
    category: Category,
    title: String,
    errorMessage: String,
    viewModel: CategoryViewModel,
    onBackPressed: () -> Unit,
    onAddClicked: (Action) -> Unit
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
                    category = category,
                    result = state.value,
                    errorMessage = errorMessage
                )

                AddButton(
                    category = category,
                    onAddClicked = onAddClicked,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
            }
        }
    )
}

@Suppress("UNCHECKED_CAST")
@Composable
fun CategoryBody(
    category: Category,
    result: Result,
    errorMessage: String
) {
    when (result) {
        is Result.Loading -> LoadingIndicator()
        is Result.Success<*> -> {
            val viewState = result.value as CategoryViewModel.ViewState<*>
            val models = viewState.items as List<CategoryModel>

            CardColumn(
                category = category,
                models = models
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
    models: List<CategoryModel>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        models.forEach {
            CategoryCard(
                category = category,
                model = it
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun CategoryCard(
    category: Category,
    model: CategoryModel
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            ) {
                when (category) {
                    Category.NPC -> NpcCard(npc = model as Npc)
                    Category.SHOP -> ShopCard(shop = model as Shop)
                    Category.LOCATION -> LocationCard(location = model as Location)
                    Category.ITEM -> ItemCard(item = model as Item)
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
                            1.0F to Color.White
                        )
                    )
            )
        }
    }
}

@Composable
fun CardPropertyRow(
    label: String,
    text: String
) {
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