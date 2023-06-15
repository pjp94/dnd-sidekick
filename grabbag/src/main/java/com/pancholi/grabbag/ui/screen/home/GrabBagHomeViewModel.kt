package com.pancholi.grabbag.ui.screen.home

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.ImportedContent
import com.pancholi.grabbag.usecase.ImportItemUseCase
import com.pancholi.grabbag.usecase.ImportLocationUseCase
import com.pancholi.grabbag.usecase.ImportNpcUseCase
import com.pancholi.grabbag.usecase.ImportShopUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class GrabBagHomeViewModel @Inject constructor(
    private val contentResolver: ContentResolver,
    private val importNpcUseCase: ImportNpcUseCase,
    private val importShopUseCase: ImportShopUseCase,
    private val importLocationUseCase: ImportLocationUseCase,
    private val importItemUseCase: ImportItemUseCase,
    private val resources: Resources,
    private val gson: Gson,
    private val dispatcher: Dispatcher,
) : ViewModel() {

    data class ViewState(
        val openFilePicker: List<String> = emptyList()
    )

    companion object {

        private const val JSON_MIME_TYPE = "application/json"
        private const val TEXT_MIME_TYPE = "text/plain"

        private val mimeTypes = listOf(JSON_MIME_TYPE, TEXT_MIME_TYPE)
    }

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

    private val _snackbarVisuals: MutableSharedFlow<SidekickSnackbarVisuals> = MutableSharedFlow()
    val snackbarVisuals: SharedFlow<SidekickSnackbarVisuals> = _snackbarVisuals

    fun onImportContentClicked() {
        viewModelScope.launch {
            _viewState.update { it.copy(openFilePicker = mimeTypes) }
        }
    }

    fun onFilePickerClosed() {
        _viewState.update { it.copy(openFilePicker = emptyList()) }
    }

    fun onFilePicked(uri: Uri) {
        val builder = StringBuilder()
        var content: ImportedContent? = null
        @StringRes var importMessageId: Int

        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line = bufferedReader.readLine()

            do {
                builder.append(line)
                line = bufferedReader.readLine()
            } while (line != null)

            content = gson.fromJson(builder.toString(), ImportedContent::class.java)
            importMessageId = R.string.import_success
        } catch (exception: Exception) {
            importMessageId = R.string.import_fail
        }

        val visuals = SidekickSnackbarVisuals(message = resources.getString(importMessageId))

        viewModelScope.launch {
            _snackbarVisuals.emit(visuals)

            withContext(dispatcher.io) {
                content?.let {
                    importNpcUseCase(models = it.npcs)
                    importShopUseCase(models = it.shops)
                    importLocationUseCase(models = it.locations)
                    importItemUseCase(models = it.items)
                }
            }
        }
    }
}