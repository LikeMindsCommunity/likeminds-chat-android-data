package com.likeminds.chatinternalsdk.chatroom.api

import com.likeminds.chatinternalsdk.chatroom.ChatroomReceiver
import com.likeminds.chatinternalsdk.chatroom.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ChatroomApiImpl @Inject constructor(
    private val chatroomReceiver: ChatroomReceiver
) : ChatroomApi {

    override suspend fun getChatroomActions(
        request: _GetChatroomActionsRequest_
    ): NetworkResponse<APIResponse<_GetChatroomActionsResponse_>> {
        return chatroomReceiver.getChatroomActions(request)
    }

    override suspend fun followChatroom(
        request: _FollowChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomReceiver.followChatroom(request)
    }

    override suspend fun leaveSecretChatroom(
        request: _LeaveSecretChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomReceiver.leaveSecretChatroom(request)
    }

    override suspend fun muteChatroom(
        request: _MuteChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomReceiver.muteChatroom(request)
    }

    override suspend fun markReadChatroom(
        request: _MarkReadChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomReceiver.markReadChatroom(request)
    }

    override suspend fun setChatroomTopic(
        request: _SetChatroomTopicRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomReceiver.setChatroomTopic(request)
    }

    override suspend fun getParticipants(
        request: _GetParticipantsRequest_
    ): NetworkResponse<APIResponse<_GetParticipantsResponse_>> {
        return chatroomReceiver.getParticipants(request)
    }

    override suspend fun editChatroomTitle(
        request: _EditChatroomTitleRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomReceiver.editChatroomTitle(request)
    }
}