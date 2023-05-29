package com.likeminds.internalsdk.chatroom

import com.likeminds.internalsdk.chatroom.model._GetChatroomRequest_
import com.likeminds.internalsdk.chatroom.model._GetChatroomResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ChatroomReceiver @Inject constructor(private val chatroomNetworkApi: ChatroomNetworkApi) {

    suspend fun getChatroom(
        request: _GetChatroomRequest_
    ): NetworkResponse<APIResponse<_GetChatroomResponse_>> {
        return chatroomNetworkApi.getChatroom(request.chatroomId)
    }
}