package com.pancholi.grabbag.viewmodel

import androidx.lifecycle.ViewModel
import com.pancholi.core.Result
import kotlinx.coroutines.flow.StateFlow

abstract class CategoryViewModel : ViewModel() {

    abstract val viewState: StateFlow<Result>
    abstract fun loadData()

    data class ViewState<T>(
        val items: List<T>
    )
}