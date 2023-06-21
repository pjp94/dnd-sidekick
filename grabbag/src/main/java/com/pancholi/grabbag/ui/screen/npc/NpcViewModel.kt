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
import com.pancholi.grabbag.repository.NpcRepository
import com.pancholi.grabbag.ui.screen.category.CategoryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NpcViewModel @Inject constructor(
    private val npcRepository: NpcRepository,
    private val npcMapper: NpcMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : CategoryViewModel() {

    init {
        loadData()
    }

    override fun loadData() {
        viewModelScope.launch(dispatcher.io) {
            npcRepository
                .getAllNpcs()
                .distinctUntilChanged()
                .catch { onResultChanged(Result.Error(it)) }
                .collect { entities ->
                    val result = if (entities.isNotEmpty()) {
                        val npcs = entities.map { npcMapper.fromEntity(it) }
                        updateModelForDialog(models = npcs)

                        Result.Success(npcs)
                    } else {
                        Result.Error(EmptyDatabaseException())
                    }

                    onResultChanged(result)
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
}