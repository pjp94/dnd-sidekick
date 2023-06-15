package com.pancholi.grabbag.usecase

import com.pancholi.grabbag.mapper.ItemMapper
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.repository.ItemRepository
import javax.inject.Inject

class ImportItemUseCase @Inject constructor(
    private val itemRepository: ItemRepository,
    private val itemMapper: ItemMapper
) : ContentImporter {

    override suspend operator fun invoke(models: List<ImportedModel>?) {
        models?.let {
            val items = models.map { itemMapper.toEntity(it as ImportedModel.ImportedItem) }
            itemRepository.insertAllItems(items)
        }
    }
}