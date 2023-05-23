package com.pancholi.core

sealed class Result {

    data class Success<T>(
        val value: T
    ) : Result()

    data class Error(
        val throwable: Throwable
    ) : Result()

    object Loading : Result()
}