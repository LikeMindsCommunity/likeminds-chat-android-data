package com.likeminds.internalsdk.chatroom

import com.likeminds.internalsdk.chatroom.model._FollowChatroomRequest_
import com.likeminds.internalsdk.chatroom.model._GetChatroomRequest_
import com.likeminds.internalsdk.chatroom.model._GetChatroomResponse_
import com.likeminds.internalsdk.chatroom.model._LeaveSecretChatroomRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ChatroomApiImpl @Inject constructor(
    private val chatroomReceiver: ChatroomReceiver
) : ChatroomApi {

    override suspend fun getChatroom(
        request: _GetChatroomRequest_
    ): NetworkResponse<APIResponse<_GetChatroomResponse_>> {
        return chatroomReceiver.getChatroom(request)
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
}