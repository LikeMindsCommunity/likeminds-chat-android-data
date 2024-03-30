package com.likeminds.internalsdk.sync.util

import com.likeminds.internalsdk.conversation.model._ConversationState_
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.ROConverter
import com.likeminds.internalsdk.db.models.AppConfigRO
import com.likeminds.internalsdk.db.util.toRealmList
import com.likeminds.internalsdk.sync.model._SyncChatroomResponse_
import com.likeminds.internalsdk.sync.model._SyncConversationResponse_
import io.realm.Realm

object SyncUtil {

    //Query Value
    const val CHATROOM_PAGE_SIZE = 50
    const val CONVERSATION_PAGE_SIZE = 500
    val CHATROOM_TYPE_LIST = listOf(0, 7)

    //Query Key
    const val PAGE_KEY = "page"
    const val PAGE_SIZE_KEY = "page_size"
    const val MIN_TIMESTAMP_KEY = "min_timestamp"
    const val MAX_TIMESTAMP_KEY = "max_timestamp"
    const val CHATROOM_TYPES_KEY = "chatroom_types"
    const val CHATROOM_ID_KEY = "chatroom_id"
    const val CONVERSATION_ID_KEY = "conversation_id"

    const val TAG = "SyncWorker"

    //Stores App config data to DB
    fun saveAppConfig(communityId: String) {
        if (communityId.isEmpty()) return
        val realm = Realm.getDefaultInstance()
        ChatDBUtil.write(realm) { localRealm ->
            val appConfig = ChatDBUtil.getAppConfig(localRealm)
            if (appConfig != null) {
                appConfig.communities = communityId.toRealmList()
            } else {
                localRealm.insertOrUpdate(AppConfigRO.build {
                    communities = communityId.toRealmList()
                })
            }
        }
        realm.close()
    }

    // Stores chatroom data to DB
    fun saveChatroomResponse(
        communityId: String,
        loggedInUUID: String,
        data: _SyncChatroomResponse_
    ) {
        val chatrooms = data.chatrooms
        val realm = Realm.getDefaultInstance()

        ChatDBUtil.write(realm) { realmWrite ->
            val community = data.communityMeta[communityId] ?: return@write
            val communityRO = ROConverter.convertCommunity(community) ?: return@write
            communityRO.relationshipNeeded = true

            //save community
            realmWrite.insertOrUpdate(communityRO)

            chatrooms.forEach { chatroom ->
                //chatroom creator
                val creatorId = chatroom.userId
                val creator = data.userMeta[creatorId.toString()] ?: return@forEach
                val chatroomCreatorRO =
                    ROConverter.convertMember(creator, communityId) ?: return@forEach
                realmWrite.insertOrUpdate(chatroomCreatorRO)

                //last conversation
                val lastConversationId = chatroom.lastConversationId
                val lastConversation =
                    data.conversationMeta[lastConversationId.toString()] ?: return@forEach

                //isConversation Deleted
                val lastConversationDeletedByMemberRO = if (lastConversation.deletedBy != null) {
                    val lastConversationDeletedById = lastConversation.deletedBy
                    val lastConversationDeletedBy =
                        data.userMeta[lastConversationDeletedById.toString()]
                    ROConverter.convertMember(lastConversationDeletedBy, communityId)
                } else {
                    null
                }

                //poll check
                val lastConversationPolls =
                    if (_ConversationState_.isPoll(lastConversation.state)) {
                        val list = data.pollsMeta[lastConversationId.toString()] ?: emptyList()
                        list.sortedBy { it.id }
                            .map { poll ->
                                val userId = poll.userId
                                val user = data.userMeta[userId]
                                poll.toBuilder().member(user).build()
                            }
                    } else {
                        emptyList()
                    }

                //attachments
                val lastConversationAttachment =
                    if (lastConversation.attachmentUploaded == true &&
                        (lastConversation.attachmentCount ?: 0) > 0
                    ) {
                        data.attachmentMeta[lastConversationId.toString()]
                    } else {
                        emptyList()
                    }

                //last conversation creator
                val lastConversationCreatorId = lastConversation.memberId
                val lastConversationCreator =
                    data.userMeta[lastConversationCreatorId.toString()] ?: return@forEach

                val lastConversationCreatorRO =
                    ROConverter.convertMember(lastConversationCreator, communityId)
                        ?: return@forEach

                //widget data
                val lastConversationWidgetId = lastConversation.widgetId
                val lastConversationWidget = if (!lastConversationWidgetId.isNullOrEmpty()) {
                    data.widgets[lastConversationWidgetId]
                } else {
                    null
                }
                val lastConversationWidgetRO = ROConverter.convertWidgetRO(lastConversationWidget)

                val lastConversationRO = ROConverter.convertLastConversation(
                    realm,
                    lastConversation,
                    lastConversationCreatorRO,
                    lastConversationAttachment,
                    lastConversationDeletedByMemberRO,
                    lastConversationWidgetRO
                ) ?: return@forEach

                realmWrite.insertOrUpdate(lastConversationRO)
                realmWrite.insertOrUpdate(lastConversationCreatorRO)

                //chatroom topic
                val topicId = chatroom.topicId
                if (topicId != null) {
                    val topic = data.conversationMeta[topicId]
                    val topicCreator = data.userMeta[topic?.memberId.toString()]
                    val topicCreatorRO =
                        ROConverter.convertMember(topicCreator, communityId)

                    //isConversation Deleted
                    val topicConversationDeletedByMemberRO = if (topic?.deletedBy != null) {
                        val topicConversationDeletedById = topic.deletedBy
                        val topicConversationDeletedBy =
                            data.userMeta[topicConversationDeletedById.toString()]
                        ROConverter.convertMember(topicConversationDeletedBy, communityId)
                    } else {
                        null
                    }

                    //topic poll check
                    val topicConversationPolls =
                        if (_ConversationState_.isPoll(topic?.state ?: 0)) {
                            val list = data.pollsMeta[topicId.toString()] ?: emptyList()
                            list.sortedBy { it.id }
                                .map { poll ->
                                    val userId = poll.userId
                                    val user = data.userMeta[userId]
                                    poll.toBuilder().member(user).build()
                                }
                        } else {
                            emptyList()
                        }

                    //topic attachments
                    val topicConversationAttachments =
                        if (topic?.attachmentUploaded == true && (topic.attachmentCount ?: 0) > 0) {
                            data.attachmentMeta[topicId.toString()]
                        } else {
                            emptyList()
                        }

                    //widget data
                    val topicConversationWidgetId = topic?.widgetId
                    val topicConversationWidget = if (!topicConversationWidgetId.isNullOrEmpty()) {
                        data.widgets[topicConversationWidgetId]
                    } else {
                        null
                    }
                    val topicConversationWidgetRO =
                        ROConverter.convertWidgetRO(topicConversationWidget)

                    val topicRO =
                        ROConverter.convertConversation(
                            realm,
                            topic,
                            topicCreatorRO,
                            topicConversationPolls,
                            topicConversationAttachments,
                            loggedInUUID = loggedInUUID,
                            deletedByMemberRO = topicConversationDeletedByMemberRO,
                            widget = topicConversationWidgetRO
                        )
                    if (topicCreatorRO != null) {
                        realmWrite.insertOrUpdate(topicCreatorRO)
                    }
                    if (topicRO != null) {
                        realmWrite.insertOrUpdate(topicRO)
                    }
                }

                //last seen conversation
                val lastSeenConversationId = chatroom.lastSeenConversationId
                if (lastSeenConversationId != null) {
                    val lastSeenConversation =
                        data.conversationMeta[lastSeenConversationId.toString()]
                    val lastSeenConversationCreator =
                        data.userMeta[lastSeenConversation?.memberId.toString()]
                    val lastSeenConversationCreatorRO =
                        ROConverter.convertMember(
                            lastSeenConversationCreator,
                            communityId
                        )

                    val lastSeenConversationDeletedByMemberRO =
                        if (lastSeenConversation?.deletedBy != null) {
                            val lastSeenConversationDeletedById = lastSeenConversation.deletedBy
                            val lastSeenConversationDeletedBy =
                                data.userMeta[lastSeenConversationDeletedById.toString()]
                            ROConverter.convertMember(lastSeenConversationDeletedBy, communityId)
                        } else {
                            null
                        }

                    //last seen poll check
                    val lastSeenConversationPolls =
                        if (_ConversationState_.isPoll(lastSeenConversation?.state ?: 0)) {
                            val list =
                                data.pollsMeta[lastSeenConversationId.toString()] ?: emptyList()
                            list.sortedBy { it.id }
                                .map { poll ->
                                    val userId = poll.userId
                                    val user = data.userMeta[userId]
                                    poll.toBuilder().member(user).build()
                                }
                        } else {
                            emptyList()
                        }

                    //last seen attachments
                    val lastSeenConversationAttachments =
                        if (lastSeenConversation?.attachmentUploaded == true
                            && (lastSeenConversation.attachmentCount ?: 0) > 0
                        ) {
                            data.attachmentMeta[lastSeenConversationId.toString()]
                        } else {
                            emptyList()
                        }

                    //widget data
                    val lastSeenConversationWidgetId = lastSeenConversation?.widgetId
                    val lastSeenConversationWidget =
                        if (!lastSeenConversationWidgetId.isNullOrEmpty()) {
                            data.widgets[lastSeenConversationWidgetId]
                        } else {
                            null
                        }
                    val lastSeenConversationWidgetRO =
                        ROConverter.convertWidgetRO(lastSeenConversationWidget)


                    val lastSeenConversationRO = ROConverter.convertConversation(
                        realm,
                        lastSeenConversation,
                        lastSeenConversationCreatorRO,
                        lastSeenConversationPolls,
                        lastSeenConversationAttachments,
                        loggedInUUID = loggedInUUID,
                        deletedByMemberRO = lastSeenConversationDeletedByMemberRO,
                        widget = lastSeenConversationWidgetRO
                    )
                    if (lastSeenConversationRO != null) {
                        realmWrite.insertOrUpdate(lastSeenConversationRO)
                    }
                    if (lastSeenConversationCreatorRO != null) {
                        realmWrite.insertOrUpdate(lastSeenConversationCreatorRO)
                    }
                }

                // gets the member object for chatRequestedBy
                val chatRequestedById = chatroom.chatRequestedById
                val chatRequestedBy = data.userMeta[chatRequestedById.toString()]
                val chatRequestedByRO = ROConverter.convertMember(chatRequestedBy, communityId)

                // gets the member object for chatroomWithUser
                val chatroomWithUserId = chatroom.chatroomWithUserId
                val chatroomWithUser = data.userMeta[chatroomWithUserId.toString()]
                val chatroomWithUserRO = ROConverter.convertMember(chatroomWithUser, communityId)

                //convert chatroom
                val chatroomRO = ROConverter.convertChatroom(
                    realm,
                    chatroom,
                    chatroomCreatorRO,
                    lastConversationRO,
                    chatRequestByRO = chatRequestedByRO,
                    chatroomWithUserRO = chatroomWithUserRO
                ) ?: return@forEach
                chatroomRO.relationshipNeeded = true

                realmWrite.insertOrUpdate(chatroomRO)
            }
        }
        realm.close()
    }

    // Stores conversation data to DB
    fun saveConversationResponses(
        chatroomId: String,
        communityId: String,
        loggedInUUID: String,
        dataList: ArrayList<_SyncConversationResponse_>
    ) {
        val realm = Realm.getDefaultInstance()
        ChatDBUtil.write(realm) { realmWrite ->
            dataList.forEach { data ->
                //fetch community
                val community = data.communityMeta[communityId] ?: return@write
                val communityRO =
                    ROConverter.convertCommunity(community) ?: return@write
                communityRO.relationshipNeeded = true
                realmWrite.insertOrUpdate(communityRO)

                //fetch chatroom
                val chatroom = data.chatroomMeta[chatroomId] ?: return@write

                //chatroom creator
                val chatroomCreatorId = chatroom.userId
                val chatroomCreator =
                    data.userMeta[chatroomCreatorId.toString()] ?: return@write
                val chatroomCreatorRO =
                    ROConverter.convertMember(chatroomCreator, communityId) ?: return@write
                realmWrite.insertOrUpdate(chatroomCreatorRO)

                //reactions
                val chatroomReactions = if (chatroom.hasReactions == true) {
                    val list = data.chatroomReactionsMeta[chatroomId] ?: emptyList()
                    list.map { reaction ->
                        val userId = reaction.userId.toString()
                        val user = data.userMeta[userId]
                        reaction.toBuilder().member(user).build()
                    }
                } else {
                    emptyList()
                }

                val chatroomRO = ROConverter.convertChatroom(
                    realmWrite,
                    chatroom,
                    chatroomCreatorRO,
                    reactions = chatroomReactions
                ) ?: return@write
                chatroomRO.relationshipNeeded = true
                realmWrite.insertOrUpdate(chatroomRO)

                data.conversations.forEach conversation@{ conversation ->
                    val id = conversation.id
                    //conversation creator
                    val creatorId = conversation.memberId
                    val creator = data.userMeta[creatorId.toString()] ?: return@conversation
                    val creatorRO =
                        ROConverter.convertMember(creator, communityId) ?: return@conversation
                    realmWrite.insertOrUpdate(creatorRO)

                    val deletedByMemberRO = if (conversation.deletedBy != null) {
                        val deletedById = conversation.deletedBy
                        val deletedByMember =
                            data.userMeta[deletedById.toString()]
                        ROConverter.convertMember(deletedByMember, communityId)
                    } else {
                        null
                    }

                    //reactions
                    val reactions = if (conversation.hasReactions == true) {
                        val list = data.conversationReactionMeta[id.toString()] ?: emptyList()
                        list.map { reaction ->
                            val userId = reaction.userId.toString()
                            val user = data.userMeta[userId]
                            reaction.toBuilder().member(user).build()
                        }
                    } else {
                        emptyList()
                    }

                    //polls
                    val conversationPolls =
                        if (_ConversationState_.isPoll(conversation.state)) {
                            val list = data.conversationPollMeta[id.toString()] ?: emptyList()
                            list.sortedBy { it.id }
                                .map { poll ->
                                    val userId = poll.userId
                                    val user = data.userMeta[userId]
                                    poll.toBuilder().member(user).build()
                                }
                        } else {
                            emptyList()
                        }

                    //attachment
                    val conversationAttachment =
                        if (conversation.attachmentUploaded == true &&
                            (conversation.attachmentCount ?: 0) > 0
                        ) {
                            data.conversationAttachmentsMeta[id.toString()] ?: emptyList()
                        } else {
                            emptyList()
                        }

                    //widget data
                    val widgetId = conversation.widgetId
                    val widget = data.widgets[widgetId]
                    val widgetRO = ROConverter.convertWidgetRO(widget)

                    val conversationRO =
                        ROConverter.convertConversation(
                            realmWrite,
                            conversation,
                            creatorRO,
                            conversationPolls,
                            conversationAttachment,
                            reactions,
                            loggedInUUID = loggedInUUID,
                            deletedByMemberRO = deletedByMemberRO,
                            widget = widgetRO
                        ) ?: return@conversation

                    realmWrite.insertOrUpdate(
                        conversationRO
                    )
                }
            }
        }
        realm.close()
    }
}