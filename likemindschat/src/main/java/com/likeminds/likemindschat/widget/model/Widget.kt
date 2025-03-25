package com.likeminds.likemindschat.widget.model

import org.json.JSONObject

class Widget private constructor(
    val id: String,
    val parentEntityId: String,
    val parentEntityType: String,
    val metadata: JSONObject?,
    val lmMeta: JSONObject?,
    val createdAt: Long,
    val updatedAt: Long
) {
    class Builder {
        private var id: String = ""
        private var parentEntityId: String = ""
        private var parentEntityType: String = ""
        private var metadata: JSONObject? = null
        private var lmMeta: JSONObject? = null
        private var createdAt: Long = 0L
        private var updatedAt: Long = 0L

        fun id(id: String) = apply {
            this.id = id
        }

        fun parentEntityId(parentEntityId: String) = apply {
            this.parentEntityId = parentEntityId
        }

        fun parentEntityType(parentEntityType: String) = apply {
            this.parentEntityType = parentEntityType
        }

        fun metadata(metadata: JSONObject?) = apply {
            this.metadata = metadata
        }

        fun lmMeta(lmMeta: JSONObject?) = apply {
            this.lmMeta = lmMeta
        }

        fun createdAt(createdAt: Long) = apply {
            this.createdAt = createdAt
        }

        fun updatedAt(updatedAt: Long) = apply {
            this.updatedAt = updatedAt
        }

        fun build() = Widget(
            id,
            parentEntityId,
            parentEntityType,
            metadata,
            lmMeta,
            createdAt,
            updatedAt
        )
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .parentEntityId(parentEntityId)
            .parentEntityType(parentEntityType)
            .metadata(metadata)
            .lmMeta(lmMeta)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
    }

    override fun toString(): String {
        return "Widget: id -> $id metadata -> ${metadata.toString()} lmMeta -> ${lmMeta.toString()} parentEntityType:$parentEntityType"
    }
}