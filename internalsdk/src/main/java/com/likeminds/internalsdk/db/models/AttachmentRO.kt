package com.likeminds.internalsdk.db.models

import io.realm.kotlin.types.EmbeddedRealmObject

class AttachmentRO : EmbeddedRealmObject {

    var id: String = ""
    var url: String = ""
    var chatroomId: String = ""
    var communityId: String = ""
    var name: String? = null
    var type: String = ""
    var index: Int? = null
    var width: Int? = null
    var height: Int? = null
    var awsFolderPath: String? = null
    var localFilePath: String? = null
    var thumbnailUrl: String? = null
    var thumbnailAWSFolderPath: String? = null
    var thumbnailLocalFilePath: String? = null
    var metaRO: AttachmentMetaRO = AttachmentMetaRO()
    var createdAt: Long? = null
    var updatedAt: Long? = null
    var isRecording: Boolean? = null
    var about: String? = null
}