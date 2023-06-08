package com.pancholi.grabbag.model

import com.google.gson.annotations.SerializedName

data class ImportedContent(
    @SerializedName("npcs")
    val npcs: List<ImportedModel.ImportedNpc>,
    @SerializedName("shops")
    val shops: List<ImportedModel.ImportedShop>,
    @SerializedName("locations")
    val locations: List<ImportedModel.ImportedLocation>,
    @SerializedName("items")
    val items: List<ImportedModel.ImportedItem>
)

sealed class ImportedModel {

    data class ImportedNpc(
        @SerializedName("name")
        val name: String,
        @SerializedName("race")
        val race: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("class")
        val clss: String? = null,
        @SerializedName("profession")
        val profession: String? = null,
        @SerializedName("description")
        val description: String? = null
    ) : ImportedModel()

    data class ImportedShop(
        @SerializedName("name")
        val name: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("owner")
        val owner: String? = null,
        @SerializedName("description")
        val description: String? = null
    ) : ImportedModel()

    data class ImportedLocation(
        @SerializedName("name")
        val name: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("description")
        val description: String? = null
    ) : ImportedModel()

    data class ImportedItem(
        @SerializedName("name")
        val name: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("cost")
        val cost: String? = null,
        @SerializedName("currency")
        val currency: Currency? = Currency.GP,
        @SerializedName("description")
        val description: String? = null
    ) : ImportedModel()
}