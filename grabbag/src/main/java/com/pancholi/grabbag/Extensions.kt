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