package com.pancholi.grabbag.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pancholi.grabbag.R
import com.pancholi.grabbag.navigation.Category
import com.pancholi.grabbag.viewmodel.GrabBagHomeViewModel

@Composable
fun GrabBagHome(
    navController: NavHostController = rememberNavController(),
    viewModel: GrabBagHomeViewModel = hiltViewModel(),
    categories: List<Category>,
    onCategoryClicked: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        CategoryButton(
            categories = categories,
            onCategoryClicked = onCategoryClicked,
            modifier = Modifier.align(Alignment.Center)
        )

//        AddItemButton(
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(16.dp),
//            onAddClicked = { viewModel.onAddClicked() }
//        )
    }
}

@Composable
internal fun CategoryButton(
    categories: List<Category>,
    onCategoryClicked: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        categories.forEach { category ->
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                onClick = { onCategoryClicked(category.route) },
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
}