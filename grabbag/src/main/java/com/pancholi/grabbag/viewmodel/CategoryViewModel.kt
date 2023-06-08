package com.pancholi.grabbag.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class CategoryViewModel : ViewModel() {

    abstract fun loadData()
    abstract fun onSaveClicked(model: CategoryModel)
    abstract fun onModelsImported(models: List<ImportedModel>?)
    abstract fun onDeleteModel(model: CategoryModel)

    data class ViewState(
        val items: List<CategoryModel>,
        val showDetailDialogForModel: CategoryModel? = null,
        val showDeleteDialogForModel: Boolean = false
    )

    protected val _viewState: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    val viewState: StateFlow<Result> = _viewState.asStateFlow()

    private val _snackbarVisuals: MutableStateFlow<SidekickSnackbarVisuals?> = MutableStateFlow(null)
    val snackbarVisuals: StateFlow<SidekickSnackbarVisuals?> = _snackbarVisuals.asStateFlow()

    fun areFieldsMissing(fields: List<String>): Boolean {
        return fields.any { it.isBlank() }
    }

    fun onCardClicked(model: CategoryModel) {
        _viewState.update {
            when (it) {
                is Result.Success<*> -> Result.Success(
                    (it.value as ViewState).copy(
                        showDetailDialogForModel = model
                    )
                )
                else -> it
            }
        }
    }

    fun onDetailDialogDismissed() {
        _viewState.update {
            when (it) {
                is Result.Success<*> -> Result.Success(
                    (it.value as ViewState).copy(
                        showDetailDialogForModel = null
                    )
                )
                else -> it
            }
        }
    }

    fun onDeleteClicked() {
        _viewState.update {
            when (it) {
                is Result.Success<*> -> Result.Success(
                    (it.value as ViewState).copy(
                        showDeleteDialogForModel = true
                    )
                )
                else -> it
            }
        }
    }

    fun onDeleteDialogDismissed() {
        _viewState.update {
            when (it) {
                is Result.Success<*> -> Result.Success(
                    (it.value as ViewState).copy(
                        showDeleteDialogForModel = false
                    )
                )
                else -> it
            }
        }
    }

    protected fun showSnackbar(
        visuals: SidekickSnackbarVisuals
    ) {
        viewModelScope.launch {
            _snackbarVisuals.emit(visuals)
        }
    }

    fun onSnackbarShown() {
        viewModelScope.launch {
            _snackbarVisuals.emit(null)
        }
    }
}