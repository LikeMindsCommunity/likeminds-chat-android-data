package com.likeminds.internalsdk.chatroom

import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ChatroomReceiver @Inject constructor(private val chatroomNetworkApi: ChatroomNetworkApi) {

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

    suspend fun getChatroomParticipants(
        request: _GetChatroomParticipantsRequest_
    ): NetworkResponse<APIResponse<_GetChatroomParticipantsResponse_>> {
        return chatroomNetworkApi.getChatroomParticipants(
            request.isChatroomSecret,
            request.chatroomId,
            request.participantName,
            request.page,
            request.pageSize
        )
    }
}