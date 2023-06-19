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
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NpcActionViewModel @Inject constructor(
    private val npcRepository: NpcRepository,
    private val npcMapper: NpcMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : ActionViewModel(), ModelEditor {

    private val races = resources.getStringArray(R.array.races)
    private val classes = resources.getStringArray(R.array.classes)
    private val professions = resources.getStringArray(R.array.professions)

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
                    ModelAction.ADD -> {
                        npcRepository.insertNpc(entity)
                        val visuals = SidekickSnackbarVisuals(
                            message = resources.getString(R.string.npc_saved)
                        )

                        showSnackbar(visuals)
                    }
                    ModelAction.EDIT -> npcRepository.updateNpc(entity)
                }
            }

            onModelSaved()
            resetViewState()
        }
    }

    override fun getModelById(id: Int) {
        viewModelScope.launch(dispatcher.io) {
            npcRepository
                .getById(id)
                .filterNotNull()
                .collect {
                    val npc = npcMapper.fromEntityToEdit(it)
                    onModelToEditLoaded(npc)
                }
        }
    }

    fun onRaceChanged(text: String) {
        onPropertyFieldTextChanged(
            text = text,
            options = races
        )
    }

    fun onClassChanged(text: String) {
        onPropertyFieldTextChanged(
            text = text,
            options = classes
        )
    }

    fun onProfessionChanged(text: String) {
        onPropertyFieldTextChanged(
            text = text,
            options = professions
        )
    }
}