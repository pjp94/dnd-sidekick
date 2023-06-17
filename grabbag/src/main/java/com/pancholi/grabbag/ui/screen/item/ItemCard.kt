package com.pancholi.grabbag.ui.screen.item

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
fun ItemCard(
    item: CategoryModel.Item
) {
    val unspecified = stringResource(id = R.string.unspecified)

    Text(
        text = item.name,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Divider(thickness = Dp.Hairline)

    CardPropertyRow(
        label = stringResource(id = R.string.type),
        text = item.type
    )

    CardPropertyRow(
        label = stringResource(id = R.string.cost),
        text = "${item.cost} ${item.currency?.toString() ?: unspecified}".trim()
    )

    CardPropertyRow(
        label = stringResource(id = R.string.description),
        text = item.description.ifEmpty { unspecified },
        singleField = true
    )
}