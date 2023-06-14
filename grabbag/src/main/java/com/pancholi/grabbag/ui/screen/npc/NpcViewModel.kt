package com.pancholi.grabbag.ui.screen.npc

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.NpcMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.repository.NpcRepository
import com.pancholi.grabbag.viewmodel.CategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NpcViewModel @Inject constructor(
    private val npcRepository: NpcRepository,
    private val npcMapper: NpcMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : CategoryViewModel() {

    data class NpcViewState(
        val showRequired: Boolean = false,
        val npcSaved: Boolean = false
    )

    private val _npcViewState: MutableStateFlow<NpcViewState> = MutableStateFlow(NpcViewState())
    val npcViewState: StateFlow<NpcViewState> = _npcViewState.asStateFlow()

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(dispatcher.io) {
            npcRepository
                .getAllNpcs()
                .distinctUntilChanged()
                .catch { _viewState.emit(Result.Error(it)) }
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val npcs = entities.map { npcMapper.fromEntity(it) }
                        val viewState = ViewState(items = npcs)
                        _viewState.emit(Result.Success(viewState))
                    } else {
                        _viewState.emit(Result.Error(EmptyDatabaseException()))
                    }
                }
        }
    }

    override fun onSaveClicked(model: CategoryModel) {
        val npc = model as CategoryModel.Npc
        val requiredFields = listOf(npc.name, npc.race, npc.gender)

        if (areFieldsMissing(requiredFields)) {
            _npcViewState.update { it.copy(showRequired = true) }
        } else {
            val entity = npcMapper.toEntity(npc)

            viewModelScope.launch(dispatcher.io) {
                npcRepository.insertNpc(entity)
                _npcViewState.update { it.copy(npcSaved = true) }
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(R.string.npc_saved)
            )

            showSnackbar(visuals)
        }
    }

    override fun onModelsImported(models: List<ImportedModel>?) {
        models?.let {
            val npcs = models.map { npcMapper.toEntity(it as ImportedModel.ImportedNpc) }

            viewModelScope.launch(dispatcher.io) {
                npcRepository.insertAllNpcs(npcs)
            }
        }
    }

    override fun onDeleteModel(model: CategoryModel) {
        val npc = model as CategoryModel.Npc
        val entity = npcMapper.toEntity(npc)

        viewModelScope.launch(dispatcher.io) {
            val deleted = npcRepository.deleteNpc(entity)

            if (deleted > 0) {
                val visuals = SidekickSnackbarVisuals(
                    message = resources.getString(R.string.npc_deleted)
                )

                showSnackbar(visuals)
            }
        }
    }

    override fun onModelSaved() {
        _npcViewState.update { it.copy(npcSaved = false) }
    }

    override fun onBackPressed() {
        _npcViewState.update { it.copy(showRequired = false) }
        onModelSaved()
    }
}