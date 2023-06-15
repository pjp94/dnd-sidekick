package com.pancholi.grabbag.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pancholi.core.SidekickSnackbarVisuals
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

abstract class AddViewModel : ViewModel() {

    abstract fun onSaveClicked(model: CategoryModel)

    data class ViewState(
        val showRequiredSupportingText: Boolean = false
    )

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private val _addSnackbarVisuals = Channel<SidekickSnackbarVisuals>()
    val addSnackbarVisuals: Flow<SidekickSnackbarVisuals> = _addSnackbarVisuals.receiveAsFlow()

    private val _modelSaved: MutableSharedFlow<Unit> = MutableSharedFlow()
    val modelSaved: SharedFlow<Unit> = _modelSaved.asSharedFlow()

    fun resetViewState() {
        _viewState.value = ViewState()
    }

    protected fun showRequiredSupportingText() {
        _viewState.update { it.copy(showRequiredSupportingText = true) }
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