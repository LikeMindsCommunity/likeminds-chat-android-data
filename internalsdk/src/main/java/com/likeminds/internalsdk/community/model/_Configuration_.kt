package com.likeminds.internalsdk.community.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class _Configuration_ private constructor(
    @SerializedName("type")
    val type: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("value")
    val value: JsonObject
) {
    class Builder {
        private var type: String = ""
        private var description: String = ""
        private var value: JsonObject = JsonObject()

        fun type(type: String) = apply { this.type = type }
        fun description(description: String) = apply { this.description = description }
        fun value(value: JsonObject) = apply { this.value = value }

        fun build() = _Configuration_(type, description, value)
    }

    fun toBuilder(): Builder {
        return Builder().type(type)
            .description(description)
            .value(value)
    }
}
