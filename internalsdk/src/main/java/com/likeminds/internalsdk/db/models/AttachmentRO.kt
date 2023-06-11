package com.likeminds.internalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class AttachmentRO(
    var id: String = "",
    var url: String = "",
    var chatroomId: String = "",
    var communityId: String = "",
    var name: String? = null,
    var type: String = "",
    var index: Int? = null,
    var width: Int? = null,
    var height: Int? = null,
    var awsFolderPath: String? = null,
    var localFilePath: String? = null,
    var thumbnail: String? = null,
    var thumbnailAWSFolderPath: String? = null,
    var thumbnailLocalFilePath: String? = null,
    var metaRO: AttachmentMetaRO? = null,
    var createdAt: Long? = null,
    var updatedAt: Long? = null
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.id,
        builder.url,
        builder.chatroomId,
        builder.communityId,
        builder.name,
        builder.type,
        builder.index,
        builder.width,
        builder.height,
        builder.awsFolderPath,
        builder.localFilePath,
        builder.thumbnail,
        builder.thumbnailAWSFolderPath,
        builder.thumbnailLocalFilePath,
        builder.metaRO,
        builder.createdAt,
        builder.updatedAt
    )

    companion object {

        inline fun build(
            url: String,
            chatroomId: String,
            communityId: String,
            block: Builder.() -> Unit
        ) = Builder(url, chatroomId, communityId).apply(block).build()
    }

    class Builder(var url: String, var chatroomId: String, var communityId: String) {

        var id: String = ""
        var name: String? = null
        var type: String = ""
        var index: Int? = null
        var width: Int? = null
        var height: Int? = null
        var awsFolderPath: String? = null
        var localFilePath: String? = null
        var thumbnail: String? = null
        var thumbnailAWSFolderPath: String? = null
        var thumbnailLocalFilePath: String? = null
        var metaRO: AttachmentMetaRO? = null
        var createdAt: Long? = null
        var updatedAt: Long? = null

        fun build() = AttachmentRO(this)
    }

    fun toBuilder(): Builder {
        return Builder(url, chatroomId, communityId).apply {
            id = this@AttachmentRO.id
            name = this@AttachmentRO.name
            type = this@AttachmentRO.type
            index = this@AttachmentRO.index
            width = this@AttachmentRO.width
            height = this@AttachmentRO.height
            awsFolderPath = this@AttachmentRO.awsFolderPath
            localFilePath = this@AttachmentRO.localFilePath
            thumbnail = this@AttachmentRO.thumbnail
            thumbnailAWSFolderPath = this@AttachmentRO.thumbnailAWSFolderPath
            thumbnailLocalFilePath = this@AttachmentRO.thumbnailLocalFilePath
            metaRO = this@AttachmentRO.metaRO
            createdAt = this@AttachmentRO.createdAt
            updatedAt = this@AttachmentRO.updatedAt
        }
    }
}