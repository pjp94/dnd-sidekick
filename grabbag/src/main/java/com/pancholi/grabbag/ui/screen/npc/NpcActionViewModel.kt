package com.pancholi.grabbag.ui.screen.npc

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.NpcMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.repository.NpcRepository
import com.pancholi.grabbag.ui.screen.modelaction.ActionViewModel
import com.pancholi.grabbag.ui.screen.modelaction.ModelAction
import com.pancholi.grabbag.ui.screen.modelaction.ModelEditor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NpcActionViewModel @Inject constructor(
    private val npcRepository: NpcRepository,
    private val npcMapper: NpcMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : ActionViewModel(), ModelEditor {

    override fun onSaveClicked(
        model: CategoryModel,
        action: ModelAction
    ) {
        val npc = model as CategoryModel.Npc
        val requiredFields = listOf(npc.name, npc.race, npc.gender)

        if (areFieldsMissing(requiredFields)) {
            showRequiredSupportingText()
        } else {
            val entity = npcMapper.toEntity(npc)

            viewModelScope.launch(dispatcher.io) {
                when (action) {
                    ModelAction.ADD -> npcRepository.insertNpc(entity)
                    ModelAction.EDIT -> npcRepository.updateNpc(entity)
                }
            }

            onModelSaved()

            val messageId = when (action) {
                ModelAction.ADD -> R.string.npc_saved
                ModelAction.EDIT -> R.string.npc_updated
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(messageId)
            )

            showSnackbar(visuals)
        }
    }

    override fun getModelById(id: Int) {
        viewModelScope.launch(dispatcher.io) {
            npcRepository
                .getById(id)
                .collect {
                    val npc = npcMapper.fromEntityToEdit(it)
                    onModelToEditLoaded(npc)
                }
        }
    }
}