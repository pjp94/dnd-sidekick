package com.pancholi.grabbag.ui.screen.location

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.ui.FullScreenDialogColumn
import com.pancholi.grabbag.ui.screen.CardPropertyRow

@Composable
fun LocationDialog(
    location: CategoryModel.Location,
    innerPadding: PaddingValues
) {
    FullScreenDialogColumn(
        innerPadding = innerPadding
    ) {
        CardPropertyRow(
            label = stringResource(id = R.string.type),
            text = location.type
        )

        CardPropertyRow(
            label = stringResource(id = R.string.description),
            text = location.description,
            singleField = true
        )
    }
}