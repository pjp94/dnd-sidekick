package com.pancholi.grabbag

import com.pancholi.grabbag.model.Currency

fun String?.requireString(
    defaultValue: () -> String
): String {
    return if (this.isNullOrBlank()) {
        defaultValue()
    } else {
        this
    }
}

fun String?.getOrEmptyForEdit(
    unspecified: String
): String {
    return if (this.isNullOrBlank() || this == unspecified) {
        ""
    } else {
        this
    }
}

fun String?.ifEmptyOrNull(
    defaultValue: () -> String
): String {
    return if (this.isNullOrEmpty()) {
        defaultValue()
    } else {
        this
    }
}

fun Currency.getOrNull(
    cost: String?
): Currency? {
    return if (cost.isNullOrEmpty().not()) {
        this
    } else {
        null
    }
}