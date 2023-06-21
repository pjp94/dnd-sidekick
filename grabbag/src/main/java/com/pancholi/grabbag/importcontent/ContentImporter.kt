package com.pancholi.grabbag.importcontent

import com.pancholi.grabbag.model.ImportedModel

fun interface ContentImporter {

    suspend operator fun invoke(models: List<ImportedModel>?)
}