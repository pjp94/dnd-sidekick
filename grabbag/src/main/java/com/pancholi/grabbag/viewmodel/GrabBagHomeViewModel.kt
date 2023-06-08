package com.pancholi.grabbag.viewmodel

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.grabbag.R
import com.pancholi.grabbag.model.ImportedContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class GrabBagHomeViewModel @Inject constructor(
    private val contentResolver: ContentResolver,
    private val resources: Resources,
    private val gson: Gson
) : ViewModel() {

    data class ViewState(
        val openFilePicker: List<String> = emptyList(),
        val snackbarVisuals: SidekickSnackbarVisuals? = null,
        val importedContent: ImportedContent? = null
    )

    companion object {

        private const val JSON_MIME_TYPE = "application/json"
        private const val TEXT_MIME_TYPE = "text/plain"

        private val mimeTypes = listOf(JSON_MIME_TYPE, TEXT_MIME_TYPE)
    }

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()

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


        _viewState.update {
            val visuals = SidekickSnackbarVisuals(message = resources.getString(importMessageId))
            it.copy(
                snackbarVisuals = visuals,
                importedContent = content
            )
        }
    }

    fun onSnackbarShown() {
        _viewState.update { it.copy(snackbarVisuals = null) }
    }

    fun onImportComplete() {
        _viewState.update { it.copy(importedContent = null) }
    }
}