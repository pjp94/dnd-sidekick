package com.pancholi.grabbag.ui.screen.npc

import androidx.lifecycle.viewModelScope
import com.pancholi.core.Result
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.core.database.EmptyDatabaseException
import com.pancholi.grabbag.mapper.NpcMapper
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

    private val _viewState: MutableStateFlow<Result> = MutableStateFlow(Result.Loading)
    override val viewState: StateFlow<Result> = _viewState.asStateFlow()

    private val _showRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showRequired: StateFlow<Boolean> = _showRequired.asStateFlow()

    private val _npcSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val npcSaved: StateFlow<Boolean> = _npcSaved.asStateFlow()

    private var name: String? = null
    private var race: String? = null
    private var gender: String? = null
    private var clss: String? = null
    private var profession: String? = null
    private var description: String? = null

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

    override fun onSaveClicked() {
        if (areFieldsMissing(listOf(name, race, gender))) {
            viewModelScope.launch { _showRequired.emit(true) }
        } else {
            val npc = Npc(
                name = name.orEmpty(),
                race = race.orEmpty(),
                gender = gender.orEmpty(),
                clss = clss,
                profession = profession,
                description = description
            )
            val entity = npcMapper.toEntity(npc)

            viewModelScope.launch(dispatcher.io) {
                npcRepository
                    .insertNpc(entity)

                _npcSaved.emit(true)
            }
        }
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setRace(race: String) {
        this.race = race
    }

    fun setGender(gender: String) {
        this.gender = gender
    }

    fun setClass(clss: String) {
        this.clss = clss
    }

    fun setProfession(profession: String) {
        this.profession = profession
    }

    fun setDescription(description: String) {
        this.description = description
    }
}