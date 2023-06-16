package com.pancholi.grabbag

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