package com.pancholi.grabbag.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.pancholi.grabbag.CategoryScreen
import com.pancholi.grabbag.viewmodel.GrabBagHomeViewModel

@Composable
fun GrabBagHome(
    navController: NavHostController,
    viewModel: GrabBagHomeViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {

        CategoryButton(
            modifier = Modifier.align(Alignment.Center),
            onCategoryClicked = { category -> viewModel.onCategoryClicked(category) }
        )

        AddItemButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onAddClicked = { viewModel.onAddClicked() }
        )
    }

    val navigationState = viewModel.navigationRoute.collectAsStateWithLifecycle("")
    Log.d("NPC_HOME_TAG", "GrabBagHome recomposing")
    NavigationRoute(
        navController = navController,
        navigationState = navigationState
    )
}

@Composable
internal fun CategoryButton(
    modifier: Modifier,
    onCategoryClicked: (CategoryScreen) -> Unit
) {
    val categories = listOf(
        CategoryScreen.Npc,
        CategoryScreen.Shop,
        CategoryScreen.Location,
        CategoryScreen.Item
    )

    Column(
        modifier = modifier
    ) {
        categories.forEach { category ->
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(8.dp),
                onClick = { onCategoryClicked(category) }
            ) {
                Text(
                    text = stringResource(id = category.nameId),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun AddItemButton(
    modifier: Modifier,
    onAddClicked: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier,
        content = { Icon(imageVector = Icons.Filled.Add, contentDescription = "") },
        onClick = { onAddClicked() }
    )
}

@Composable
fun NavigationRoute(
    navController: NavHostController,
    navigationState: State<String>
) {
    Log.d("NPC_HOME_TAG", "NavigationRoute recomposing")
    val route = navigationState.value
    Log.d("NPC_HOME_TAG", "New route: $route")

    if (route.isNotEmpty()) {
        navController.navigate(route)
    }
}
