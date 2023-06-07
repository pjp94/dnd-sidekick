package com.pancholi.grabbag

import com.pancholi.grabbag.model.Currency

fun String.ifCostNotBlank(
    currency: Currency,
    defaultValue: () -> String
): String {
    return if (this.isNotBlank()) {
        "$this $currency"
    } else {
        defaultValue()
    }
}