package com.likeminds.internalsdk.db.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ChatroomRO : RealmObject {

    @PrimaryKey
    var id: String = ""
    var communityId: String = ""
    var title: String = ""
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
    var secretChatRoomParticipants: RealmList<Int> = realmListOf()
    var secretChatRoomLeft: Boolean? = null
    var conversations: RealmList<ConversationRO>? = null
    var topicId: String? = null
    var topic: ConversationRO? = null
    var autoFollowDone: Boolean? = null
    var memberCanMessage: Boolean? = null
    var isEdited: Boolean? = null
    var reactions: RealmList<ReactionRO> = realmListOf()
    var unreadConversationsCount: Int? = null
    var accessWithoutSubscription: Boolean? = false
    var externalSeen: Boolean? = null

    //to check whether chatroom's conversation is saved or not
    var isConversationStored: Boolean = false

    //todo
//    //---used for local purposes---
//    @LinkingObjects("chatrooms")
//    val communities: RealmResults<CommunityRO>? = null
}