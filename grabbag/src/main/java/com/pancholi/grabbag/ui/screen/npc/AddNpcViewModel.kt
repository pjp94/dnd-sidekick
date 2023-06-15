package com.pancholi.grabbag.ui.screen.npc

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.pancholi.core.SidekickSnackbarVisuals
import com.pancholi.core.coroutines.Dispatcher
import com.pancholi.grabbag.R
import com.pancholi.grabbag.mapper.NpcMapper
import com.pancholi.grabbag.model.CategoryModel
import com.pancholi.grabbag.repository.NpcRepository
import com.pancholi.grabbag.ui.screen.AddViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNpcViewModel @Inject constructor(
    private val npcRepository: NpcRepository,
    private val npcMapper: NpcMapper,
    private val dispatcher: Dispatcher,
    private val resources: Resources
) : AddViewModel() {

    override fun onSaveClicked(model: CategoryModel) {
        val npc = model as CategoryModel.Npc
        val requiredFields = listOf(npc.name, npc.race, npc.gender)

        if (areFieldsMissing(requiredFields)) {
            showRequiredSupportingText()
        } else {
            val entity = npcMapper.toEntity(npc)

            viewModelScope.launch(dispatcher.io) {
                npcRepository.insertNpc(entity)
                onModelSaved()
            }

            val visuals = SidekickSnackbarVisuals(
                message = resources.getString(R.string.npc_saved)
            )

            showSnackbar(visuals)
        }
    }
}