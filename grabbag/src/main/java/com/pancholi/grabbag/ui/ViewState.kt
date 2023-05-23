package com.pancholi.grabbag.ui

import androidx.annotation.StringRes

data class ViewState<T>(
    val items: List<T>,
    @StringRes val categoryNameId: Int? = null
)