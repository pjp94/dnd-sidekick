package com.pancholi.grabbag.mapper

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.pancholi.grabbag.model.CategoryModel
import java.io.IOException

class CategoryTypeAdapter : TypeAdapter<List<CategoryModel>>() {

    override fun write(out: JsonWriter?, value: List<CategoryModel>?) = Unit

    @Throws(IOException::class)
    override fun read(json: JsonReader?): List<CategoryModel> {
        if (json == null || json.peek() == JsonToken.NULL) {
            return emptyList()
        }

        val models = listOf<CategoryModel>()
        var currentLine = json

        while (currentLine.peek() != null) {
            when (currentLine.peek()) {
                JsonToken.BEGIN_ARRAY -> TODO()
                JsonToken.END_ARRAY -> TODO()
                JsonToken.BEGIN_OBJECT -> TODO()
                JsonToken.END_OBJECT -> TODO()
                JsonToken.NAME -> TODO()
                JsonToken.STRING -> TODO()
                JsonToken.NUMBER -> TODO()
                JsonToken.BOOLEAN -> TODO()
                JsonToken.NULL -> TODO()
                JsonToken.END_DOCUMENT -> TODO()
                else -> throw IOException()
            }
        }

        return models
    }
}