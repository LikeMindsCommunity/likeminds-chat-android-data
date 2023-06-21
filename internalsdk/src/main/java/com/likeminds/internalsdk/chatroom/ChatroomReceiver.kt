package com.likeminds.internalsdk.chatroom

import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
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

    suspend fun getChatroom(
        request: _GetChatroomRequest_
    ): NetworkResponse<APIResponse<_GetChatroomResponse_>> {
        return chatroomNetworkApi.getChatroom(request.chatroomId)
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

    suspend fun shareChatroomUrl(
        request: _ShareChatroomUrlRequest_
    ): NetworkResponse<APIResponse<_ShareChatroomUrlResponse_>> {
        val chatroomId = request.chatroomId
        val domain = request.domain
        return chatroomNetworkApi.shareChatroomUrl(chatroomId, domain)
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
}