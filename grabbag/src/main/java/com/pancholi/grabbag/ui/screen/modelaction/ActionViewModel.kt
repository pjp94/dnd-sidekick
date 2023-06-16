package com.pancholi.grabbag.ui.screen.modelaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.CategoryModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ActionViewModel : ViewModel() {

    abstract fun onSaveClicked(
        model: CategoryModel,
        action: ModelAction
    )

    data class ViewState(
        val showRequiredSupportingText: Boolean = false,
        val discardConfirmationDialog: Int? = null,
        val modelToEdit: Result = Result.Loading
    )

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private val _addSnackbarVisuals = Channel<SidekickSnackbarVisuals>()
    val addSnackbarVisuals: Flow<SidekickSnackbarVisuals> = _addSnackbarVisuals.receiveAsFlow()

    private val _modelSaved: MutableSharedFlow<Unit> = MutableSharedFlow()
    val modelSaved: SharedFlow<Unit> = _modelSaved.asSharedFlow()

    fun onBackPressed(
        action: ModelAction,
        oldModel: CategoryModel?,
        newModel: CategoryModel
    ) {
        val messageId = when (action) {
            ModelAction.ADD -> R.string.discard_draft
            ModelAction.EDIT -> R.string.discard_changes
        }

        _viewState.update { it.copy(discardConfirmationDialog = messageId) }
    }

    fun onDialogDismissed() {
        _viewState.update { it.copy(discardConfirmationDialog = null) }
    }

    fun resetViewState() {
        _viewState.value = ViewState()
    }

    protected fun showRequiredSupportingText() {
        _viewState.update { it.copy(showRequiredSupportingText = true) }
    }

    protected fun onModelToEditLoaded(model: CategoryModel) {
        _viewState.update { it.copy(modelToEdit = Result.Success(model)) }
    }

    protected fun showSnackbar(visuals: SidekickSnackbarVisuals) {
        viewModelScope.launch {
            _addSnackbarVisuals.send(visuals)
        }
    }

    protected fun areFieldsMissing(fields: List<String>): Boolean {
        return fields.any { it.isBlank() }
    }

    protected fun onModelSaved() {
        viewModelScope.launch {
            _modelSaved.emit(Unit)
        }
    }
}