package com.likeminds.likemindschat.conversation.model

class Attachment private constructor(
    val id: String?,
    val name: String?,
    val url: String,
    val type: String,
    val index: Int?,
    val width: Int?,
    val height: Int?,
    val awsFolderPath: String?,
    val localFilePath: String?,
    val thumbnailUrl: String?,
    val thumbnailAWSFolderPath: String?,
    val thumbnailLocalFilePath: String?,
    val meta: AttachmentMeta?,
    val createdAt: Long?,
    val updatedAt: Long?
) {
    class Builder {
        private var id: String? = null
        private var name: String? = null
        private var url: String = ""
        private var type: String = ""
        private var index: Int? = null
        private var width: Int? = null
        private var height: Int? = null
        private var awsFolderPath: String? = null
        private var localFilePath: String? = null
        private var thumbnailUrl: String? = null
        private var thumbnailAWSFolderPath: String? = null
        private var thumbnailLocalFilePath: String? = null
        private var meta: AttachmentMeta? = null
        private var createdAt: Long? = null
        private var updatedAt: Long? = null

        fun id(id: String?) = apply { this.id = id }
        fun name(name: String?) = apply { this.name = name }
        fun url(url: String) = apply { this.url = url }
        fun type(type: String) = apply { this.type = type }
        fun index(index: Int?) = apply { this.index = index }
        fun width(width: Int?) = apply { this.width = width }
        fun height(height: Int?) = apply { this.height = height }
        fun awsFolderPath(awsFolderPath: String?) = apply { this.awsFolderPath = awsFolderPath }
        fun localFilePath(localFilePath: String?) = apply { this.localFilePath = localFilePath }
        fun thumbnailUrl(thumbnailUrl: String?) = apply { this.thumbnailUrl = thumbnailUrl }
        fun thumbnailAWSFolderPath(thumbnailAWSFolderPath: String?) =
            apply { this.thumbnailAWSFolderPath = thumbnailAWSFolderPath }

        fun thumbnailLocalFilePath(thumbnailLocalFilePath: String?) =
            apply { this.thumbnailLocalFilePath = thumbnailLocalFilePath }

        fun meta(meta: AttachmentMeta?) = apply { this.meta = meta }
        fun createdAt(createdAt: Long?) = apply { this.createdAt = createdAt }
        fun updatedAt(updatedAt: Long?) = apply { this.updatedAt = updatedAt }

        fun build() = Attachment(
            id,
            name,
            url,
            type,
            index,
            width,
            height,
            awsFolderPath,
            localFilePath,
            thumbnailUrl,
            thumbnailAWSFolderPath,
            thumbnailLocalFilePath,
            meta,
            createdAt,
            updatedAt
        )
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .name(name)
            .url(url)
            .type(type)
            .index(index)
            .width(width)
            .height(height)
            .awsFolderPath(awsFolderPath)
            .localFilePath(localFilePath)
            .thumbnailUrl(thumbnailUrl)
            .thumbnailAWSFolderPath(thumbnailAWSFolderPath)
            .thumbnailLocalFilePath(thumbnailLocalFilePath)
            .meta(meta)
            .createdAt(createdAt)
            .updatedAt(updatedAt)
    }
}