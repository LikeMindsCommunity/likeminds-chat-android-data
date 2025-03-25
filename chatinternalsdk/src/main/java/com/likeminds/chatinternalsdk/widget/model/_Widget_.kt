package com.likeminds.chatinternalsdk.widget.model

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class _Widget_ private constructor(
    @SerializedName("_id")
    val id: String,
    @SerializedName("parent_entity_id")
    val parentEntityId: String,
    @SerializedName("parent_entity_type")
    val parentEntityType: String,
    @SerializedName("metadata")
    val metadata: JsonElement?,
    @SerializedName("_lm_meta")
    val lmMeta: JsonElement?,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("updated_at")
    val updatedAt: Long
) {
    class Builder {
        private var id: String = ""
        private var parentEntityId: String = ""
        private var parentEntityType: String = ""
        private var metadata: JsonElement? = null
        private var lmMeta: JsonElement? = null
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

        fun metadata(metadata: JsonElement?) = apply {
            this.metadata = metadata
        }

        fun lmMeta(lmMeta: JsonElement?) = apply {
            this.lmMeta = lmMeta
        }

        fun createdAt(createdAt: Long) = apply {
            this.createdAt = createdAt
        }

        fun updatedAt(updatedAt: Long) = apply {
            this.updatedAt = updatedAt
        }

        fun build() = _Widget_(
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
}