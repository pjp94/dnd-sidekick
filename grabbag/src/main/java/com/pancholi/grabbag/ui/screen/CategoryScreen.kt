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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.ui.AddItemButton
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
    onAddClicked: () -> Unit
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

                AddItemButton(
                    onAddClicked = onAddClicked,
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
        shape = RectangleShape,
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
                    .padding(top = 4.dp, start = 4.dp, end = 4.dp)
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
                    .height(50.dp)
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

@Preview
@Composable
fun CardPreview() {
    val npc = Npc(
        name = "Darianna",
        race = "Human",
        gender = "Female",
        clss = null,
        profession = "Innkeep",
        description = """
            An older woman in her 50s with dark brown hair. Her complexion has very obviously been affected by long days out in the sun and sand, and she doesn’t do much to hide it. She’s very stern, having to deal with drunken sailors most days, 
            and runs a tight ship at her inn. Her husband, Jacob, was the head of naval trade within the town. When the ship washed 
            ashore, he was the first to be told. Jacob found the treasure chest in the captain’s quarters and kept it hidden as it 
            was too heavy to be moved. He only told Dariana about it, and they used the treasures sparingly to fund their somewhat 
            exuberant lifestyle. With Jacob overseeing all naval affairs, he was able to make sure no one would search the ship and 
            find the treasure. When he died, Dariana had no way to protect the treasure, so she converted the ship into an inn and 
            made it her permanent residence. She uses the money to fund the inn and buy supplies, as well as to bribe some of the 
            political contacts her husband had. These contacts make sure no one tries to remove the ship, which has been complained 
            about as an eyesore by many of the town’s beachgoers.
        """.trimIndent()
    )
//    CategoryCard(npc = npc)
}