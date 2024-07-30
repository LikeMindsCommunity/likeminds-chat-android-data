package com.likeminds.chatinternalsdk.conversation.model

import com.google.gson.annotations.SerializedName

class _PutMultimediaRequest_ private constructor(
    @SerializedName("conversation_id")
    val conversationId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("type")
    val type: String,
    @SerializedName("files_count")
    val filesCount: Int?,
    @SerializedName("index")
    val index: Int?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("meta")
    val meta: _AttachmentMeta_?
) {

    class Builder {

        private var conversationId: String = ""
        private var name: String = ""
        private var url: String? = null
        private var thumbnailUrl: String? = null
        private var type: String = ""
        private var filesCount: Int? = null
        private var index: Int? = null
        private var width: Int? = null
        private var height: Int? = null
        private var meta: _AttachmentMeta_? = null

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun name(name: String) = apply { this.name = name }
        fun url(url: String?) = apply { this.url = url }
        fun thumbnailUrl(thumbnailUrl: String?) = apply { this.thumbnailUrl = thumbnailUrl }
        fun type(type: String) = apply { this.type = type }
        fun filesCount(filesCount: Int?) = apply { this.filesCount = filesCount }
        fun index(index: Int?) = apply { this.index = index }
        fun width(width: Int?) = apply { this.width = width }
        fun height(height: Int?) = apply { this.height = height }
        fun meta(meta: _AttachmentMeta_?) = apply { this.meta = meta }

        fun build() = _PutMultimediaRequest_(
            conversationId,
            name,
            url,
            thumbnailUrl,
            type,
            filesCount,
            index,
            width,
            height,
            meta
        )
    }

    fun toBuilder(): Builder {
        return Builder().conversationId(conversationId)
            .name(name)
            .url(url)
            .thumbnailUrl(thumbnailUrl)
            .type(type)
            .filesCount(filesCount)
            .index(index)
            .width(width)
            .height(height)
            .meta(meta)
    }
}