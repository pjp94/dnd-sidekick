package com.pancholi.grabbag.ui.screen.npc

import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.mapper.NpcMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.model.Npc
import com.pancholi.grabbag.repository.NpcRepository
import com.pancholi.grabbag.viewmodel.CategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NpcViewModel @Inject constructor(
    private val npcRepository: NpcRepository,
    private val npcMapper: NpcMapper,
    private val dispatcher: Dispatcher
) : CategoryViewModel() {

    private val _showRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showRequired: StateFlow<Boolean> = _showRequired.asStateFlow()

    private val _npcSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val npcSaved: StateFlow<Boolean> = _npcSaved.asStateFlow()

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
        val npc = model as Npc
        val requiredFields = listOf(npc.name, npc.race, npc.gender)

        if (areFieldsMissing(requiredFields)) {
            viewModelScope.launch { _showRequired.emit(true) }
        } else {
            val entity = npcMapper.toEntity(npc)

            viewModelScope.launch(dispatcher.io) {
                npcRepository.insertNpc(entity)
                _npcSaved.emit(true)
            }
        }
    }
}