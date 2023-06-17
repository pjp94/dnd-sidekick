package com.pancholi.grabbag.ui.screen.shop

import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.ui.screen.CardPropertyRow

@Composable
fun ShopCard(
    shop: CategoryModel.Shop
) {
    val unspecified = stringResource(id = R.string.unspecified)

    Text(
        text = shop.name,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Divider(thickness = Dp.Hairline)

    CardPropertyRow(
        label = stringResource(id = R.string.type),
        text = shop.type
    )

    CardPropertyRow(
        label = stringResource(id = R.string.owner),
        text = shop.owner.ifEmpty { unspecified }
    )

    CardPropertyRow(
        label = stringResource(id = R.string.description),
        text = shop.description.ifEmpty { unspecified },
        singleField = true
    )
}