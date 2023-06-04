package com.pancholi.grabbag.ui.screen.location

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.Location
import com.pancholi.grabbag.ui.screen.CardPropertyRow

@Composable
fun ColumnScope.LocationCard(
    location: Location
) {
    Text(
        text = location.name,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Divider(thickness = Dp.Hairline)

    CardPropertyRow(
        label = stringResource(id = R.string.type),
        text = location.type
    )

    CardPropertyRow(
        label = stringResource(id = R.string.description),
        text = location.description ?: stringResource(id = R.string.unspecified)
    )
}