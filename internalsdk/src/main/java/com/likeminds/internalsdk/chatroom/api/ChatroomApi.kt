package com.likeminds.internalsdk.chatroom.api

import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface ChatroomApi {

    // api to get chatroom actions
    suspend fun getChatroomActions(
        request: _GetChatroomActionsRequest_
    ): NetworkResponse<APIResponse<_GetChatroomActionsResponse_>>

    // api to follow chatroom
    suspend fun followChatroom(
        request: _FollowChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to leave secret chatroom
    suspend fun leaveSecretChatroom(
        request: _LeaveSecretChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to mute chatroom
    suspend fun muteChatroom(
        request: _MuteChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to mark chatroom as read
    suspend fun markReadChatroom(
        request: _MarkReadChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to set conversation as chatroom's topic
    suspend fun setChatroomTopic(
        request: _SetChatroomTopicRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to get list of participants in chatroom
    suspend fun getParticipants(
        request: _GetParticipantsRequest_
    ): NetworkResponse<APIResponse<_GetParticipantsResponse_>>

    // api to edit the chatroom title
    suspend fun editChatroomTitle(
        request: _EditChatroomTitleRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to update status of the secret chatroom invite
    suspend fun updateChannelInvite(
        request: _UpdateChannelInviteRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to get channel invites
    suspend fun getChannelInvites(
        request: _GetChannelInviteRequest_
    ): NetworkResponse<APIResponse<_GetChannelInviteResponse_>>
}