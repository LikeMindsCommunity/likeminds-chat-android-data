package com.likeminds.internalsdk.db.models

import io.realm.*
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

open class ConversationRO(
    @PrimaryKey
    var id: String = "",
    var chatroomId: String = "",
    var communityId: String = "",
    var member: MemberRO? = null,
    var answer: String = "",
    var state: Int = 0,
    var createdEpoch: Long = 0L,
    var createdAt: String? = null,
    var attachments: RealmList<AttachmentRO> = RealmList(),
    var link: LinkRO? = null,
    var date: String? = null,
    var isEdited: Boolean? = null,
    var lastSeen: Boolean = false,
    var replyConversationId: String? = null,
    var replyConversation: ConversationRO? = null,
    var deletedBy: String? = null,
    var attachmentCount: Int? = null,
    var attachmentsUploaded: Boolean? = null,
    var uploadWorkerUUID: String? = null,
    var localSavedEpoch: Long = 0L,
    var temporaryId: String? = null,
    var reactions: RealmList<ReactionRO> = RealmList(),
    var isAnonymous: Boolean? = null,
    var allowAddOption: Boolean? = null,
    var pollType: Int? = null,
    var pollTypeText: String? = null,
    var submitTypeText: String? = null,
    var expiryTime: Long? = null,
    var multipleSelectNum: Int? = null,
    var multipleSelectState: Int? = null,
    var polls: RealmList<PollRO> = RealmList(),
    var pollAnswerText: String? = null,
    var toShowResults: Boolean? = null,
    var replyChatRoomId: String? = null,
    var lastUpdatedAt: Long = 0L,

    @LinkingObjects("conversations")
    val community: RealmResults<CommunityRO>? = null,

    @LinkingObjects("conversations")
    val chatroom: RealmResults<ChatroomRO>? = null
) : RealmObject() {

    fun getChatroom() = chatroom?.firstOrNull()

    fun getCommunity() = community?.firstOrNull()

    fun isTemporaryConversation() = id.startsWith("-")

    fun attachmentsToUpload() = attachments.filter {
        !it.awsFolderPath.isNullOrEmpty()
    }

    fun thumbnailsToUpload() = attachments.filter {
        !it.thumbnailAWSFolderPath.isNullOrEmpty()
    }

    private constructor(builder: Builder) : this(
        builder.id,
        builder.chatroomId,
        builder.communityId,
        builder.member,
        builder.answer,
        builder.state,
        builder.createdEpoch,
        builder.createdAt,
        builder.attachments,
        builder.link,
        builder.date,
        builder.isEdited,
        builder.lastSeen,
        builder.replyConversationId,
        builder.replyConversation,
        builder.deletedBy,
        builder.attachmentCount,
        builder.attachmentsUploaded,
        builder.uploadWorkerUUID,
        builder.localSavedEpoch,
        builder.temporaryId,
        builder.reactions,
        builder.isAnonymous,
        builder.allowAddOption,
        builder.pollType,
        builder.pollTypeText,
        builder.submitTypeText,
        builder.expiryTime,
        builder.multipleSelectNum,
        builder.multipleSelectState,
        builder.polls,
        builder.pollAnswerText,
        builder.toShowResults,
        builder.replyChatRoomId,
        builder.lastUpdatedAt
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
        var member: MemberRO? = null
        var createdAt: String? = null
        var attachments: RealmList<AttachmentRO> = RealmList()
        var link: LinkRO? = null
        var date: String? = null
        var isEdited: Boolean? = null
        var lastSeen: Boolean = false
        var replyConversationId: String? = null
        var replyConversation: ConversationRO? = null
        var deletedBy: String? = null
        var attachmentCount: Int? = null
        var attachmentsUploaded: Boolean? = null
        var uploadWorkerUUID: String? = null
        var localSavedEpoch: Long = 0L
        var temporaryId: String? = null
        var reactions: RealmList<ReactionRO> = RealmList()
        var isAnonymous: Boolean? = null
        var allowAddOption: Boolean? = null
        var pollType: Int? = null
        var pollTypeText: String? = null
        var submitTypeText: String? = null
        var expiryTime: Long? = null
        var multipleSelectNum: Int? = null
        var multipleSelectState: Int? = null
        var polls: RealmList<PollRO> = RealmList()
        var pollAnswerText: String? = null
        var toShowResults: Boolean? = null
        var replyChatRoomId: String? = null
        var lastUpdatedAt: Long = 0L

        fun build() = ConversationRO(this)
    }

    fun toBuilder(): Builder {
        return Builder(id, answer, state, createdEpoch).apply {
            chatroomId = this@ConversationRO.chatroomId
            communityId = this@ConversationRO.communityId
            member = this@ConversationRO.member
            createdAt = this@ConversationRO.createdAt
            attachments = this@ConversationRO.attachments
            link = this@ConversationRO.link
            date = this@ConversationRO.date
            isEdited = this@ConversationRO.isEdited
            lastSeen = this@ConversationRO.lastSeen
            replyConversationId = this@ConversationRO.replyConversationId
            replyConversation = this@ConversationRO.replyConversation
            deletedBy = this@ConversationRO.deletedBy
            attachmentCount = this@ConversationRO.attachmentCount
            attachmentsUploaded = this@ConversationRO.attachmentsUploaded
            uploadWorkerUUID = this@ConversationRO.uploadWorkerUUID
            localSavedEpoch = this@ConversationRO.localSavedEpoch
            temporaryId = this@ConversationRO.temporaryId
            reactions = this@ConversationRO.reactions
            isAnonymous = this@ConversationRO.isAnonymous
            allowAddOption = this@ConversationRO.allowAddOption
            pollType = this@ConversationRO.pollType
            pollTypeText = this@ConversationRO.pollTypeText
            submitTypeText = this@ConversationRO.submitTypeText
            expiryTime = this@ConversationRO.expiryTime
            multipleSelectNum = this@ConversationRO.multipleSelectNum
            multipleSelectState = this@ConversationRO.multipleSelectState
            polls = this@ConversationRO.polls
            pollAnswerText = this@ConversationRO.pollAnswerText
            toShowResults = this@ConversationRO.toShowResults
            replyChatRoomId = this@ConversationRO.replyChatRoomId
            lastUpdatedAt = this@ConversationRO.lastUpdatedAt
        }
    }
}