package com.likeminds.internalsdk.conversation.model

import com.google.gson.annotations.SerializedName

class _Attachment_ private constructor(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName(value = "url", alternate = ["image_url", "video_url", "file_url"])
    val url: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("index")
    val index: Int?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("aws_folder_path")
    val awsFolderPath: String?,
    @SerializedName("local_file_path")
    val localFilePath: String?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("thumbnail_aws_folder_path")
    val thumbnailAWSFolderPath: String?,
    @SerializedName("thumbnail_local_file_path")
    val thumbnailLocalFilePath: String?,
    @SerializedName("meta")
    val meta: _AttachmentMeta_?,
    @SerializedName("created_at")
    val createdAt: Long?,
    @SerializedName("updatedAt")
    val updatedAt: Long?,
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
        private var meta: _AttachmentMeta_? = null
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

        fun meta(meta: _AttachmentMeta_?) = apply { this.meta = meta }
        fun createdAt(createdAt: Long?) = apply { this.createdAt = createdAt }
        fun updatedAt(updatedAt: Long?) = apply { this.updatedAt = updatedAt }
        fun build() = _Attachment_(
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