package com.likeminds.internalsdk.chatroom

import android.os.Build
import com.likeminds.internalsdk.chatroom.api.ChatroomNetworkApi
import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import io.realm.Realm
import javax.inject.Inject

class ChatroomReceiver @Inject constructor(
    private val chatroomNetworkApi: ChatroomNetworkApi
) {

    companion object {
        private const val IS_SECRET_KEY = "is_secret"
        private const val CHATROOM_ID_KEY = "chatroom_id"
        private const val PARTICIPANT_NAME_KEY = "participant_name"
        private const val PAGE_KEY = "page"
        private const val PAGE_SIZE_KEY = "page_size"
    }

    /**
     * API Functions
     */

    suspend fun getChatroomActions(
        request: _GetChatroomActionsRequest_
    ): NetworkResponse<APIResponse<_GetChatroomActionsResponse_>> {
        return chatroomNetworkApi.getChatroomActions(request.chatroomId)
    }

    suspend fun followChatroom(
        request: _FollowChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.followChatroom(request)
    }

    suspend fun leaveSecretChatroom(
        request: _LeaveSecretChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.leaveSecretChatroom(request)
    }

    suspend fun muteChatroom(
        request: _MuteChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.muteChatroom(request)
    }

    suspend fun markReadChatroom(
        request: _MarkReadChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.markReadChatroom(request)
    }

    suspend fun setChatroomTopic(
        request: _SetChatroomTopicRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.setChatroomTopic(request)
    }

    suspend fun getParticipants(
        request: _GetParticipantsRequest_
    ): NetworkResponse<APIResponse<_GetParticipantsResponse_>> {
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[IS_SECRET_KEY] = request.isChatroomSecret
        queries[CHATROOM_ID_KEY] = request.chatroomId
        if (request.participantName != null) {
            queries[PARTICIPANT_NAME_KEY] = request.participantName
        }
        queries[PAGE_KEY] = request.page
        queries[PAGE_SIZE_KEY] = request.pageSize

        return chatroomNetworkApi.getParticipants(queries)
    }

    suspend fun editChatroomTitle(
        request: _EditChatroomTitleRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.editChatroomTitle(request)
    }

    /**
     * DB Functions
     */

    fun getChatroom(realm: Realm, chatroomId: String): ChatroomRO? {
        return ChatDBUtil.getChatroom(realm, chatroomId)
    }

    fun updateChatroomFollowStatus(chatroomId: String, value: Boolean) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatroomRO ->
                chatroomRO.followStatus = value
                if (value) {
                    val currentMillis = System.currentTimeMillis()
                    chatroomRO.updatedAt = currentMillis
                }
            }
        })
    }

    fun updateChatroomMuteStatus(chatroomId: String, value: Boolean) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatroomRO ->
                chatroomRO.muteStatus = value
            }
        })
    }

    fun updateSecretChatroomLeaveStatus(chatroomId: String) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatroomRO ->
                chatroomRO.followStatus = false
                chatroomRO.secretChatRoomLeft = true
            }
        })
    }

    fun updateChatroomTitle(chatroomId: String, updatedTitle: String) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatRoom ->
                chatRoom.title = updatedTitle
                chatRoom.isEdited = true
            }
        })
    }

    fun updateChatroomTopic(chatroomId: String, topicId: String) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatroomRO ->
                chatroomRO.topicId = topicId
                val topic = ChatDBUtil.getConversation(it, topicId)
                if (topic != null) {
                    chatroomRO.topic = topic
                }
            }
        })
    }

    fun updateChatroomReaction(
        reaction: String,
        chatroomId: String
    ) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getChatroom(realm, chatroomId)?.let { chatroomRO ->

                //get logged in member
                val userRO = realm.where(UserRO::class.java).findFirst()

                val index = chatroomRO.reactions.indexOfFirst {
                    it.member?.sdkClientInfoRO?.uuid == userRO?.sdkClientInfoRO?.uuid
                }
                val memberObj =
                    ChatDBUtil.getMember(
                        realm,
                        chatroomRO.communityId,
                        userRO?.sdkClientInfoRO?.uuid
                    ) ?: return@let
                val messageReaction = ReactionRO.build() {
                    this.reaction = reaction
                    member = memberObj
                }
                if (index >= 0) {
                    chatroomRO.reactions[index] = messageReaction
                } else {
                    chatroomRO.reactions.add(0, messageReaction)
                }
            }
        })
    }

    fun removeChatroomReaction(chatroomId: String) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getChatroom(realm, chatroomId)?.let { chatroom ->
                //get logged in member
                val userRO = realm.where(UserRO::class.java).findFirst()

                //Remove member previous reactions if any
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    chatroom.reactions.removeIf { reaction ->
                        reaction.member?.id == userRO?.id
                    }
                } else {
                    val reactionRO = chatroom.reactions.find { reaction ->
                        reaction.member?.id == userRO?.id
                    }
                    chatroom.reactions.remove(reactionRO)
                }
            }
        })
    }

    fun updateLastSeenAndDraft(chatroomId: String, draft: String?) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getChatroom(realm, chatroomId)?.let { chatroomRO ->
                if (chatroomRO.unseenCount != 0) {
                    chatroomRO.unseenCount = 0
                }
                if ((chatroomRO.draftConversation ?: "") != draft) {
                    chatroomRO.draftConversation = draft
                }

                val conversations = chatroomRO.conversations
                if (conversations.isNotEmpty()) {
                    chatroomRO.lastSeenConversation = conversations.last()
                }
            }
        })
    }
}