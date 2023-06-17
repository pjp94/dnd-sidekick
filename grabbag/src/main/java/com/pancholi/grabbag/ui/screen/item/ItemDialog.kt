package com.pancholi.grabbag.ui.screen.item

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.ui.FullScreenDialogColumn
import com.pancholi.grabbag.ui.screen.CardPropertyRow

@Composable
fun ItemDialog(
    item: CategoryModel.Item,
    innerPadding: PaddingValues
) {
    val unspecified = stringResource(id = R.string.unspecified)

    FullScreenDialogColumn(
        innerPadding = innerPadding
    ) {
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
}