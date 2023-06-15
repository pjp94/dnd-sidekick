package com.pancholi.grabbag.ui.screen.shop

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.ui.FullScreenDialogColumn
import com.pancholi.grabbag.ui.screen.CardPropertyRow

@Composable
fun ShopDialog(
    shop: CategoryModel.Shop,
    innerPadding: PaddingValues
) {
    FullScreenDialogColumn(
        innerPadding = innerPadding
    ) {
        CardPropertyRow(
            label = stringResource(id = R.string.type),
            text = shop.type
        )

        CardPropertyRow(
            label = stringResource(id = R.string.owner),
            text = shop.owner
        )

        CardPropertyRow(
            label = stringResource(id = R.string.description),
            text = shop.description,
            singleField = true
        )
    }
}