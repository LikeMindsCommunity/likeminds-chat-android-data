package com.likeminds.likemindschat.community.model

import org.json.JSONObject

class Configuration private constructor(
    val type: ConfigurationType,
    val description: String,
    val value: JSONObject
) {
    class Builder {
        private var type: ConfigurationType = ConfigurationType.NONE
        private var description: String = ""
        private var value: JSONObject = JSONObject()

        fun type(type: ConfigurationType) = apply { this.type = type }
        fun description(description: String) = apply { this.description = description }
        fun value(value: JSONObject) = apply { this.value = value }

        fun build() = Configuration(type, description, value)
    }

    fun toBuilder(): Builder {
        return Builder().type(type)
            .description(description)
            .value(value)
    }

    override fun toString(): String {
        return "Configuration received as type: $type description: $description value: $value"
    }
}