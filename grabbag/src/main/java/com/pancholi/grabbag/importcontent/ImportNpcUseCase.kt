package com.pancholi.grabbag.importcontent

import com.pancholi.grabbag.mapper.NpcMapper
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.repository.NpcRepository
import javax.inject.Inject

class ImportNpcUseCase @Inject constructor(
    private val npcRepository: NpcRepository,
    private val npcMapper: NpcMapper,
) : ContentImporter {

    override suspend operator fun invoke(models: List<ImportedModel>?) {
        models?.let {
            val npcs = models.map { npcMapper.toEntity(it as ImportedModel.ImportedNpc) }
            npcRepository.insertAllNpcs(npcs)
        }
    }
}