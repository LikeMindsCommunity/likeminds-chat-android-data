package com.likeminds.internalsdk.sync.util

import com.likeminds.internalsdk.conversation.model._ConversationState_
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.ROConverter
import com.likeminds.internalsdk.sync.model._SyncChatroomResponse_
import io.realm.kotlin.Realm

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

    // Stores chatroom data to DB
    suspend fun saveChatroomResponse(
        realm: Realm,
        communityId: String,
        loggedInMemberId: String,
        data: _SyncChatroomResponse_
    ) {
        val chatrooms = data.chatrooms

        val community = data.communityMeta[communityId] ?: return
        val communityRO = ROConverter.convertCommunity(community) ?: return
        communityRO.relationshipNeeded = true

        //save community
        ChatDBUtil.insertOrUpdate(realm, communityRO)

        chatrooms.forEach { chatroom ->
            //chatroom creator
            val creatorId = chatroom.userId
            val creator = data.userMeta[creatorId.toString()] ?: return@forEach
            val chatroomCreatorRO =
                ROConverter.convertMember(creator, communityId) ?: return@forEach
            ChatDBUtil.insertOrUpdate(realm, chatroomCreatorRO)

            //last conversation
            val lastConversationId = chatroom.lastConversationId
            val lastConversation =
                data.conversationMeta[lastConversationId.toString()] ?: return@forEach

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

            val lastConversationRO = ROConverter.convertLastConversation(
                realm,
                lastConversation,
                lastConversationCreatorRO,
                lastConversationAttachment
            ) ?: return@forEach

            ChatDBUtil.insertOrUpdate(realm, lastConversationRO)
            ChatDBUtil.insertOrUpdate(realm, lastConversationCreatorRO)

            //chatroom topic
            val topicId = chatroom.topicId
            if (topicId != null) {
                val topic = data.conversationMeta[topicId]
                val topicCreator = data.userMeta[topic?.memberId.toString()]
                val topicCreatorRO =
                    ROConverter.convertMember(topicCreator, communityId)

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

                val topicRO =
                    ROConverter.convertConversation(
                        realm,
                        topic,
                        topicCreatorRO,
                        topicConversationPolls,
                        topicConversationAttachments,
                        loggedInMemberId = loggedInMemberId
                    )
                if (topicCreatorRO != null) {
                    ChatDBUtil.insertOrUpdate(realm, topicCreatorRO)
                }
                if (topicRO != null) {
                    ChatDBUtil.insertOrUpdate(realm, topicRO)
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

                val lastSeenConversationRO = ROConverter.convertConversation(
                    realm,
                    lastSeenConversation,
                    lastSeenConversationCreatorRO,
                    lastSeenConversationPolls,
                    lastSeenConversationAttachments
                )
                if (lastSeenConversationRO != null) {
                    ChatDBUtil.insertOrUpdate(realm, lastSeenConversationRO)
                }
                if (lastSeenConversationCreatorRO != null) {
                    ChatDBUtil.insertOrUpdate(realm, lastSeenConversationCreatorRO)
                }
            }

            //convert chatroom
            val chatroomRO = ROConverter.convertChatroom(
                realm,
                chatroom,
                chatroomCreatorRO,
                lastConversationRO
            ) ?: return@forEach
            chatroomRO.relationshipNeeded = true
            ChatDBUtil.insertOrUpdate(realm, chatroomRO)
        }
    }
}