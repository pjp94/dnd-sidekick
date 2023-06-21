package com.pancholi.grabbag.importcontent

import com.pancholi.grabbag.mapper.ShopMapper
import com.pancholi.grabbag.model.ImportedModel
import com.pancholi.grabbag.repository.ShopRepository
import javax.inject.Inject

class ImportShopUseCase @Inject constructor(
    private val shopRepository: ShopRepository,
    private val shopMapper: ShopMapper
) : ContentImporter {

    override suspend operator fun invoke(models: List<ImportedModel>?) {
        models?.let {
            val shops = models.map { shopMapper.toEntity(it as ImportedModel.ImportedShop) }
            shopRepository.insertAllShops(shops)
        }
    }
}