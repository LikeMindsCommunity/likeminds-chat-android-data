package com.likeminds.internalsdk.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class LastConversationRO : RealmObject {

    @PrimaryKey
    var id: String = ""
    var member: MemberRO? = null
    var createdAt: String? = null
    var answer: String = ""
    var state: Int = 0
    var attachments: RealmList<AttachmentRO> = realmListOf()
    var date: String? = null
    var deletedBy: String? = null
    var attachmentCount: Int? = null
    var attachmentsUploaded: Boolean? = null
    var uploadWorkerUUID: String? = null
    var createdEpoch: Long = 0L
    var chatroomId: String = ""
    var communityId: String = ""
    var link: LinkRO? = null
}