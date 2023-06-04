package com.pancholi.grabbag.ui.screen.npc

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.Npc
import com.pancholi.grabbag.ui.screen.CardPropertyRow

@Composable
fun ColumnScope.NpcCard(
    npc: Npc
) {
    Text(
        text = npc.name,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    Divider(thickness = Dp.Hairline)

    CardPropertyRow(
        label = stringResource(id = R.string.race),
        text = npc.race
    )

    CardPropertyRow(
        label = stringResource(id = R.string.gender),
        text = npc.gender
    )

    CardPropertyRow(
        label = stringResource(id = R.string.clss),
        text = npc.clss ?: stringResource(id = R.string.unspecified)
    )

    CardPropertyRow(
        label = stringResource(id = R.string.profession),
        text = npc.profession ?: stringResource(id = R.string.unspecified)
    )

    CardPropertyRow(
        label = stringResource(id = R.string.description),
        text = npc.description ?: stringResource(id = R.string.unspecified)
    )
}