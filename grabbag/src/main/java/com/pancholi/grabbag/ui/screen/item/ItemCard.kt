package com.pancholi.grabbag.ui.screen.item

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.Item
import com.pancholi.grabbag.ui.screen.CardPropertyRow

@Composable
fun ColumnScope.ItemCard(
    item: Item
) {
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

    val cost = if (item.cost != null) {
        "${item.cost} ${item.getCurrency()}"
    } else {
        stringResource(id = R.string.unspecified)
    }

    CardPropertyRow(
        label = stringResource(id = R.string.cost),
        text = cost
    )

    CardPropertyRow(
        label = stringResource(id = R.string.description),
        text = item.description ?: stringResource(id = R.string.unspecified)
    )
}