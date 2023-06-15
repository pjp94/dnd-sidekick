package com.pancholi.grabbag.usecase

import com.pancholi.grabbag.model.ImportedModel

fun interface ContentImporter {

    suspend operator fun invoke(models: List<ImportedModel>?)
}