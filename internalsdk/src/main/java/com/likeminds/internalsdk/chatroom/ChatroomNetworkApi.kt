package com.likeminds.internalsdk.chatroom

import com.likeminds.internalsdk.chatroom.model._FollowChatroomRequest_
import com.likeminds.internalsdk.chatroom.model._GetChatroomResponse_
import com.likeminds.internalsdk.chatroom.model._LeaveSecretChatroomRequest_
import com.likeminds.internalsdk.chatroom.model._MuteChatroomRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface ChatroomNetworkApi {

    @GET("chatroom")
    @Headers("x-accept-version: v2")
    suspend fun getChatroom(@Query("chatroom_id") chatroomId: String): NetworkResponse<APIResponse<_GetChatroomResponse_>>

    @PUT("chatroom/follow")
    suspend fun followChatroom(@Body request: _FollowChatroomRequest_): NetworkResponse<APIResponse<Nothing>>

    @HTTP(method = "DELETE", path = "chatroom/participants", hasBody = true)
    suspend fun leaveSecretChatroom(
        @Body request: _LeaveSecretChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @PUT("chatroom/mute")
    suspend fun muteChatroom(@Body request: _MuteChatroomRequest_): NetworkResponse<APIResponse<Nothing>>
}