package com.pancholi.grabbag.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.grabbag.model.CategoryModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class CategoryViewModel : ViewModel() {

    abstract fun loadData()
    abstract fun onDeleteModel(model: CategoryModel)

    data class ViewState(
        val result: Result = Result.Loading,
        val showDetailDialogForModel: CategoryModel? = null,
        val showDeleteConfirmation: Boolean = false
    )

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private val _snackbarVisuals: MutableSharedFlow<SidekickSnackbarVisuals> = MutableSharedFlow()
    val snackbarVisuals: SharedFlow<SidekickSnackbarVisuals> = _snackbarVisuals.asSharedFlow()

    fun onShowDetailDialog(model: CategoryModel) {
        _viewState.update { it.copy(showDetailDialogForModel = model) }
    }

    fun onDetailDialogDismissed() {
        _viewState.update { it.copy(showDetailDialogForModel = null) }
    }

    fun onDeleteClicked() {
        _viewState.update { it.copy(showDeleteConfirmation = true) }
    }

    fun onDeleteDialogDismissed() {
        _viewState.update { it.copy(showDeleteConfirmation = false) }
    }

    protected fun onResultChanged(result: Result) {
        _viewState.update { it.copy(result = result) }
    }

    protected fun showSnackbar(visuals: SidekickSnackbarVisuals) {
        viewModelScope.launch {
            _snackbarVisuals.emit(visuals)
        }
    }

    protected fun updateModelForDialog(models: List<CategoryModel>) {
        _viewState.value.showDetailDialogForModel?.let { model ->
            models.find { it.id == model.id }?.let { updatedModel ->
                onShowDetailDialog(updatedModel)
            }
        }
    }
}