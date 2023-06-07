package com.pancholi.grabbag.viewmodel

import androidx.lifecycle.ViewModel
import com.pancholi.core.Result
import com.pancholi.grabbag.model.CategoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class CategoryViewModel : ViewModel() {

    abstract fun loadData()
    abstract fun onSaveClicked(model: CategoryModel)

    data class ViewState<T>(
        val items: List<T>,
        val showDialogForModel: CategoryModel? = null
    )

    protected val _viewState: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    val viewState: StateFlow<Result> = _viewState.asStateFlow()

    fun areFieldsMissing(fields: List<String>): Boolean {
        return fields.any { it.isBlank() }
    }

    fun onCardClicked(model: CategoryModel) {
        _viewState.update {
            when (it) {
                is Result.Success<*> -> Result.Success(
                    (it.value as ViewState<*>).copy(
                        showDialogForModel = model
                    )
                )
                else -> it
            }
        }
    }

    fun onDialogDismissed() {
        _viewState.update {
            when (it) {
                is Result.Success<*> -> Result.Success(
                    (it.value as ViewState<*>).copy(
                        showDialogForModel = null
                    )
                )
                else -> it
            }
        }
    }
}