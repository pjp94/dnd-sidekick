package com.pancholi.grabbag.viewmodel

import androidx.lifecycle.ViewModel
import com.pancholi.core.Result
import kotlinx.coroutines.flow.StateFlow

abstract class CategoryViewModel : ViewModel() {

    abstract val viewState: StateFlow<Result>
    abstract fun loadData()
    abstract fun onSaveClicked()

    data class ViewState<T>(
        val items: List<T>
    )

    fun areFieldsMissing(fields: List<String?>): Boolean {
        return fields.any { it.isNullOrBlank() }
    }
}