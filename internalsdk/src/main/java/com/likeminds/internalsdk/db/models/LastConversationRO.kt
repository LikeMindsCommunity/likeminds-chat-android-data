package com.likeminds.internalsdk.db.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LastConversationRO(
    @PrimaryKey
    var id: String = "",
    var member: MemberRO? = null,
    var createdAt: String? = null,
    var answer: String = "",
    var state: Int = 0,
    var attachments: RealmList<AttachmentRO> = RealmList(),
    var date: String? = null,
    var deletedBy: String? = null,
    var attachmentCount: Int? = null,
    var attachmentsUploaded: Boolean? = null,
    var uploadWorkerUUID: String? = null,
    var createdEpoch: Long = 0L,
    var chatroomId: String = "",
    var communityId: String = "",
    var link: LinkRO? = null,
    var deletedByMember: MemberRO? = null
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.id,
        builder.member,
        builder.createdAt,
        builder.answer,
        builder.state,
        builder.attachments,
        builder.date,
        builder.deletedBy,
        builder.attachmentCount,
        builder.attachmentsUploaded,
        builder.uploadWorkerUUID,
        builder.createdEpoch,
        builder.chatroomId,
        builder.communityId,
        builder.link,
        builder.deletedByMember
    )

    companion object {

        inline fun build(
            id: String,
            answer: String,
            state: Int,
            createdEpoch: Long,
            block: Builder.() -> Unit
        ) = Builder(id, answer, state, createdEpoch).apply(block).build()
    }

    class Builder(
        var id: String,
        var answer: String,
        var state: Int,
        var createdEpoch: Long
    ) {

        var chatroomId: String = ""
        var communityId: String = ""
        var deletedBy: String? = null
        var member: MemberRO? = null
        var createdAt: String? = null
        var link: LinkRO? = null
        var date: String? = null
        var attachments: RealmList<AttachmentRO> = RealmList()
        var attachmentCount: Int? = null
        var attachmentsUploaded: Boolean? = null
        var uploadWorkerUUID: String? = null
        var deletedByMember: MemberRO? = null

        fun build() = LastConversationRO(this)
    }
}