package com.pancholi.grabbag.viewmodel

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.ItemMapper
import com.pancholi.grabbag.mapper.LocationMapper
import com.pancholi.grabbag.mapper.NpcMapper
import com.pancholi.grabbag.mapper.ShopMapper
import com.pancholi.grabbag.model.CategoryModel
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
    private val npcMapper: NpcMapper,
    private val shopMapper: ShopMapper,
    private val locationMapper: LocationMapper,
    private val itemMapper: ItemMapper,
    private val resources: Resources,
    private val gson: Gson
) : ViewModel() {

    data class ViewState(
        val openFilePicker: List<String> = emptyList(),
        val importSnackbarVisuals: ImportSnackbarVisuals? = null
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
        viewModelScope.launch {
            _viewState.update { it.copy(openFilePicker = emptyList()) }
        }
    }

    fun onFilePicked(uri: Uri) {
        val builder = StringBuilder()
        @StringRes var importMessageId: Int

        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line = bufferedReader.readLine()

            do {
                builder.append(line)
                line = bufferedReader.readLine()
            } while (line != null)

            Log.d("FILE_TAG", builder.toString())
            importMessageId = R.string.import_success
        } catch (exception: Exception) {
            importMessageId = R.string.import_fail
        }

        viewModelScope.launch {
            _viewState.update {
                val visuals = ImportSnackbarVisuals(message = resources.getString(importMessageId))
                it.copy(importSnackbarVisuals = visuals)
            }
        }
    }

    fun onImportMessageShown() {
        viewModelScope.launch {
            _viewState.update { it.copy(importSnackbarVisuals = null) }
        }
    }

    private fun saveModels(models: List<CategoryModel>) {

    }
}

data class ImportSnackbarVisuals(
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val message: String,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals