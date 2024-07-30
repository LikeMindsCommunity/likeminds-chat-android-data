package com.likeminds.chatinternalsdk.db.models

import io.realm.*
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

open class ChatroomRO(
    @PrimaryKey
    var id: String = "",
    var communityId: String = "",
    var title: String = "",
    var state: Int? = null,
    var member: MemberRO? = null,
    var createdAt: Long? = null,
    var type: Int? = null,
    var chatroomImageUrl: String? = null,
    var header: String? = null,
    var cardCreationTime: String? = null,
    var totalResponseCount: Int = 0,
    var totalAllResponseCount: Int = 0,
    var muteStatus: Boolean? = null,
    var followStatus: Boolean? = null,
    var hasBeenNamed: Boolean? = null,
    var date: String? = null,
    var isTagged: Boolean? = null,
    var isPending: Boolean? = null,
    var deletedBy: String? = null,
    var updatedAt: Long? = null, //in millis, to sort chatrooms in home feed
    var lastConversation: ConversationRO? = null, //last conversation with state 0
    var lastConversationRO: LastConversationRO? = null,
    var lastSeenConversationId: String? = null,
    var lastSeenConversation: ConversationRO? = null, //last seen conversation
    var dateEpoch: Long? = null,
    var unseenCount: Int = 0,
    var relationshipNeeded: Boolean = true,
    var draftConversation: String? = null,
    var isSecret: Boolean? = null,
    var secretChatRoomParticipants: RealmList<Int> = RealmList(),
    var secretChatRoomLeft: Boolean? = null,
    var conversations: RealmList<ConversationRO> = RealmList(),
    var topicId: String? = null,
    var topic: ConversationRO? = null,
    var autoFollowDone: Boolean? = null,
    var memberCanMessage: Boolean? = null,
    var isEdited: Boolean? = null,
    var reactions: RealmList<ReactionRO> = RealmList(),
    var unreadConversationsCount: Int? = null,
    var accessWithoutSubscription: Boolean = false,
    var externalSeen: Boolean? = null,
    var isConversationStored: Boolean = false, //to check whether chatroom's conversation is saved or not
    var isDraft: Boolean? = null,
    var lastConversationId: String? = null,
    var chatRequestState: Int? = null,
    var isPrivateMember: Boolean? = null,
    var chatRequestedById: String? = null,
    var chatRequestedBy: MemberRO? = null,
    var chatRequestCreatedAt: Long? = null,
    var chatroomWithUser: MemberRO? = null,
    var chatroomWithUserId: String? = null,
    @LinkingObjects("chatrooms")
    val communities: RealmResults<CommunityRO>? = null
) : RealmObject() {

    fun getCommunity() = communities?.firstOrNull()

    private constructor (builder: Builder) : this(
        builder.id,
        builder.communityId,
        builder.title,
        builder.state,
        builder.member,
        builder.createdAt,
        builder.type,
        builder.chatroomImageUrl,
        builder.header,
        builder.cardCreationTime,
        builder.totalResponseCount,
        builder.totalAllResponseCount,
        builder.muteStatus,
        builder.followStatus,
        builder.hasBeenNamed,
        builder.date,
        builder.isTagged,
        builder.isPending,
        builder.deletedBy,
        builder.updatedAt,
        builder.lastConversation,
        builder.lastConversationRO,
        builder.lastSeenConversationId,
        builder.lastSeenConversation,
        builder.dateEpoch,
        builder.unseenCount,
        builder.relationshipNeeded,
        builder.draftConversation,
        builder.isSecret,
        builder.secretChatRoomParticipants,
        builder.secretChatRoomLeft,
        builder.conversations,
        builder.topicId,
        builder.topic,
        builder.autoFollowDone,
        builder.memberCanMessage,
        builder.isEdited,
        builder.reactions,
        builder.unreadConversationsCount,
        builder.accessWithoutSubscription,
        builder.externalSeen,
        builder.isConversationStored,
        builder.isDraft,
        builder.lastConversationId,
        builder.chatRequestState,
        builder.isPrivateMember,
        builder.chatRequestedById,
        builder.chatRequestedBy,
        builder.chatRequestCreatedAt,
        builder.chatroomWithUser,
        builder.chatroomWithUserId
    )

    companion object {

        inline fun build(
            id: String,
            communityId: String,
            title: String,
            block: Builder.() -> Unit
        ) = Builder(id, title, communityId).apply(block).build()
    }

    class Builder(
        var id: String,
        var title: String,
        var communityId: String
    ) {

        var state: Int? = null
        var member: MemberRO? = null
        var createdAt: Long? = null
        var type: Int? = null
        var chatroomImageUrl: String? = null
        var header: String? = null
        var cardCreationTime: String? = null
        var totalResponseCount: Int = 0
        var totalAllResponseCount: Int = 0
        var muteStatus: Boolean? = null
        var followStatus: Boolean? = null
        var hasBeenNamed: Boolean? = null
        var date: String? = null
        var isTagged: Boolean? = null
        var isPending: Boolean? = null
        var deletedBy: String? = null
        var updatedAt: Long? = null //in millis, to sort chatrooms in home feed
        var lastConversation: ConversationRO? = null //last conversation with state 0
        var lastConversationRO: LastConversationRO? = null
        var lastSeenConversationId: String? = null
        var lastSeenConversation: ConversationRO? = null //last seen conversation
        var dateEpoch: Long? = null
        var unseenCount: Int = 0
        var relationshipNeeded: Boolean = true
        var draftConversation: String? = null
        var isSecret: Boolean? = null
        var secretChatRoomParticipants: RealmList<Int> = RealmList()
        var secretChatRoomLeft: Boolean? = null
        var conversations: RealmList<ConversationRO> = RealmList()
        var topicId: String? = null
        var topic: ConversationRO? = null
        var autoFollowDone: Boolean? = null
        var memberCanMessage: Boolean? = null
        var isEdited: Boolean? = null
        var reactions: RealmList<ReactionRO> = RealmList()
        var unreadConversationsCount: Int? = null
        var accessWithoutSubscription: Boolean = false
        var externalSeen: Boolean? = null
        var isConversationStored: Boolean = false
        var isDraft: Boolean? = null
        var lastConversationId: String? = null
        var chatRequestState: Int? = null
        var isPrivateMember: Boolean? = null
        var chatRequestedById: String? = null
        var chatRequestedBy: MemberRO? = null
        var chatRequestCreatedAt: Long? = null
        var chatroomWithUser: MemberRO? = null
        var chatroomWithUserId: String? = null

        fun build() = ChatroomRO(this)
    }

    fun toBuilder(): Builder {
        return Builder(id, title, communityId).apply {
            state = this@ChatroomRO.state
            member = this@ChatroomRO.member
            createdAt = this@ChatroomRO.createdAt
            type = this@ChatroomRO.type
            header = this@ChatroomRO.header
            cardCreationTime = this@ChatroomRO.cardCreationTime
            totalResponseCount = this@ChatroomRO.totalResponseCount
            totalAllResponseCount = this@ChatroomRO.totalAllResponseCount
            muteStatus = this@ChatroomRO.muteStatus
            followStatus = this@ChatroomRO.followStatus
            hasBeenNamed = this@ChatroomRO.hasBeenNamed
            date = this@ChatroomRO.date
            isTagged = this@ChatroomRO.isTagged
            isPending = this@ChatroomRO.isPending
            deletedBy = this@ChatroomRO.deletedBy
            updatedAt = this@ChatroomRO.updatedAt
            lastConversation = this@ChatroomRO.lastConversation
            lastConversationRO = this@ChatroomRO.lastConversationRO
            lastSeenConversationId = this@ChatroomRO.lastSeenConversationId
            lastSeenConversation = this@ChatroomRO.lastSeenConversation
            dateEpoch = this@ChatroomRO.dateEpoch
            unseenCount = this@ChatroomRO.unseenCount
            relationshipNeeded = this@ChatroomRO.relationshipNeeded
            draftConversation = this@ChatroomRO.draftConversation
            isSecret = this@ChatroomRO.isSecret
            secretChatRoomParticipants = this@ChatroomRO.secretChatRoomParticipants
            secretChatRoomLeft = this@ChatroomRO.secretChatRoomLeft
            conversations = this@ChatroomRO.conversations
            topicId = this@ChatroomRO.topicId
            topic = this@ChatroomRO.topic
            autoFollowDone = this@ChatroomRO.autoFollowDone
            memberCanMessage = this@ChatroomRO.memberCanMessage
            isEdited = this@ChatroomRO.isEdited
            reactions = this@ChatroomRO.reactions
            accessWithoutSubscription = this@ChatroomRO.accessWithoutSubscription
            externalSeen = this@ChatroomRO.externalSeen
            chatroomImageUrl = this@ChatroomRO.chatroomImageUrl
            isConversationStored = this@ChatroomRO.isConversationStored
            isDraft = this@ChatroomRO.isDraft
            lastConversationId = this@ChatroomRO.lastConversationId
            chatRequestState = this@ChatroomRO.chatRequestState
            isPrivateMember = this@ChatroomRO.isPrivateMember
            chatRequestedById = this@ChatroomRO.chatRequestedById
            chatRequestedBy = this@ChatroomRO.chatRequestedBy
            chatRequestCreatedAt = this@ChatroomRO.chatRequestCreatedAt
            chatroomWithUser = this@ChatroomRO.chatroomWithUser
            chatroomWithUserId = this@ChatroomRO.chatroomWithUserId
        }
    }
}