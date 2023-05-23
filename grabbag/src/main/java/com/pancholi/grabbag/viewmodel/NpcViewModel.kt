package com.pancholi.grabbag.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.NpcMapper
import com.pancholi.grabbag.model.Npc
import com.pancholi.grabbag.repository.NpcRepository
import com.pancholi.grabbag.ui.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class NpcViewModel @Inject constructor(
    val npcRepository: NpcRepository,
    val npcMapper: NpcMapper,
    val dispatcher: Dispatcher
) : ViewModel() {

    private val _viewState: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    val viewState: StateFlow<Result> = _viewState

    init {
        loadNpcs()
    }

    private fun loadNpcs() {
        viewModelScope.launch(dispatcher.io) {
            npcRepository
                .getAllNpcs()
                .distinctUntilChanged()
                .catch { _viewState.value = Result.Error(it) }
                .collect { entities ->
                    if (entities.isNotEmpty()) {
                        val npcs = entities.map { npcMapper.fromEntity(it) }
                        val viewState = ViewState(items = npcs)
                        _viewState.value = Result.Success(viewState)
                    } else {
                        val viewState = ViewState(
                            items = emptyList<Npc>(),
                            categoryNameId = R.string.npcs
                        )
                        _viewState.value = Result.Success(viewState)
                    }
                }
        }
    }
}