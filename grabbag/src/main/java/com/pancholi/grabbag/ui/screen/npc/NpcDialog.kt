package com.pancholi.grabbag.ui.screen.npc

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.ui.FullScreenDialogColumn
import com.pancholi.grabbag.ui.screen.CardPropertyRow

@Composable
fun NpcDialog(
    npc: CategoryModel.Npc,
    innerPadding: PaddingValues
) {
    FullScreenDialogColumn(
        innerPadding = innerPadding
    ) {
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
            text = npc.clss
        )

        CardPropertyRow(
            label = stringResource(id = R.string.profession),
            text = npc.profession
        )

        CardPropertyRow(
            label = stringResource(id = R.string.description),
            text = npc.description,
            singleField = true
        )
    }
}