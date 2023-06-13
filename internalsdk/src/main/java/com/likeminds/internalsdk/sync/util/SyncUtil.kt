package com.likeminds.internalsdk.sync.util

import android.util.Log
import com.likeminds.internalsdk.conversation.model._ConversationState_
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.ROConverter
import com.likeminds.internalsdk.db.models.AppConfigRO
import com.likeminds.internalsdk.db.util.toRealmList
import com.likeminds.internalsdk.sync.model._SyncChatroomResponse_
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
        loggedInMemberId: String,
        data: _SyncChatroomResponse_
    ) {
        val chatrooms = data.chatrooms
        val realm = Realm.getDefaultInstance()

        ChatDBUtil.write(realm) { realmWrite ->
            val community = data.communityMeta[communityId] ?: return@write
            val communityRO = ROConverter.convertCommunity(community) ?: return@write
            communityRO.relationshipNeeded = true

            //save community
            Log.d("Test_DB", "community")
            realmWrite.insertOrUpdate(communityRO)

            chatrooms.forEach { chatroom ->
                //chatroom creator
                val creatorId = chatroom.userId
                val creator = data.userMeta[creatorId.toString()] ?: return@forEach
                val chatroomCreatorRO =
                    ROConverter.convertMember(creator, communityId) ?: return@forEach
                Log.d("Test_DB", "chatroom creator")
                realmWrite.insertOrUpdate(chatroomCreatorRO)

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

                Log.d("Test_DB", "last conversation")
                realmWrite.insertOrUpdate(lastConversationRO)
                Log.d("Test_DB", "last conversation creator")
                realmWrite.insertOrUpdate(lastConversationCreatorRO)

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
                        Log.d("Test_DB", "topic creator ro")
                        realmWrite.insertOrUpdate(topicCreatorRO)
                    }
                    if (topicRO != null) {
                        Log.d("Test_DB", "topic")
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
                        Log.d("Test_DB", "last seen conversation")
                        realmWrite.insertOrUpdate(lastSeenConversationRO)
                    }
                    if (lastSeenConversationCreatorRO != null) {
                        Log.d("Test_DB", "last seen conversation creator")
                        realmWrite.insertOrUpdate(lastSeenConversationCreatorRO)
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

                Log.d("Test_DB", "chatroom")
                realmWrite.insertOrUpdate(chatroomRO)
            }
        }
        realm.close()
    }
}