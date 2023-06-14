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
    abstract fun onModelSaved()
    abstract fun onBackPressed()

    data class ViewState(
        val items: List<CategoryModel>,
        val showDetailDialogForModel: CategoryModel? = null,
        val showDeleteConfirmation: Boolean = false
    )

    protected val _viewState: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    val viewState: StateFlow<Result> = _viewState.asStateFlow()

    private val _snackbarVisuals: MutableStateFlow<SidekickSnackbarVisuals?> = MutableStateFlow(null)
    val snackbarVisuals = _snackbarVisuals.asStateFlow()

    fun areFieldsMissing(fields: List<String>): Boolean {
        return fields.any { it.isBlank() }
    }

    fun onCardClicked(model: CategoryModel) {
        updateViewState { it.copy(showDetailDialogForModel = model) }
    }

    fun onDetailDialogDismissed() {
        updateViewState { it.copy(showDetailDialogForModel = null) }
    }

    fun onDeleteClicked() {
        updateViewState { it.copy(showDeleteConfirmation = true) }
    }

    fun onDeleteDialogDismissed() {
        updateViewState { it.copy(showDeleteConfirmation = false) }
    }

    protected fun showSnackbar(visuals: SidekickSnackbarVisuals) {
        viewModelScope.launch {
            _snackbarVisuals.emit(visuals)
        }
    }

    fun onSnackbarShown() {
        viewModelScope.launch {
            _snackbarVisuals.emit(null)
        }
    }

    private fun updateViewState(
        updateAction: (ViewState) -> ViewState
    ) {
        _viewState.update {
            when (it) {
                is Result.Success<*> -> {
                    val viewState = (_viewState.value as Result.Success<*>).value as ViewState
                    Result.Success(updateAction(viewState))
                }
                else -> it
            }
        }
    }
}