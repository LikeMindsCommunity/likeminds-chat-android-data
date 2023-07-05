package com.likeminds.likemindschat.conversation.model

class PutMultimediaRequest private constructor(
    val conversationId: String,
    val name: String,
    val url: String,
    val thumbnailUrl: String?,
    val type: String,
    val filesCount: Int?,
    val index: Int?,
    val width: Int?,
    val height: Int?,
    val meta: AttachmentMeta?
) {

    class Builder {

        private var conversationId: String = ""
        private var name: String = ""
        private var url: String = ""
        private var thumbnailUrl: String? = null
        private var type: String = ""
        private var filesCount: Int? = null
        private var index: Int? = null
        private var width: Int? = null
        private var height: Int? = null
        private var meta: AttachmentMeta? = null

        fun conversationId(conversationId: String) = apply { this.conversationId = conversationId }
        fun name(name: String) = apply { this.name = name }
        fun url(url: String) = apply { this.url = url }
        fun thumbnailUrl(thumbnailUrl: String?) = apply { this.thumbnailUrl = thumbnailUrl }
        fun type(type: String) = apply { this.type = type }
        fun filesCount(filesCount: Int?) = apply { this.filesCount = filesCount }
        fun index(index: Int?) = apply { this.index = index }
        fun width(width: Int?) = apply { this.width = width }
        fun height(height: Int?) = apply { this.height = height }
        fun meta(meta: AttachmentMeta?) = apply { this.meta = meta }

        fun build() = PutMultimediaRequest(
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