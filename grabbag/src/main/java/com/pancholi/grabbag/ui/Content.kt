package com.pancholi.grabbag.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.pancholi.grabbag.R

@Composable
internal fun EmptyMessage(
    @StringRes categoryNameId: Int
) {
    Box {
        val category = stringResource(id = categoryNameId)
        androidx.compose.material.Text(
            text = stringResource(id = R.string.empty_category, category),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}