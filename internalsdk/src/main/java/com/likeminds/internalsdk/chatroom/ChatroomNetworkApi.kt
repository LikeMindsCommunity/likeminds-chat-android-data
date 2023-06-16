package com.likeminds.internalsdk.chatroom

import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface ChatroomNetworkApi {

    @GET("chatroom")
    @Headers("x-accept-version: v2")
    suspend fun getChatroom(
        @Query("chatroom_id") chatroomId: String
    ): NetworkResponse<APIResponse<_GetChatroomResponse_>>

    @PUT("chatroom/follow")
    suspend fun followChatroom(
        @Body request: _FollowChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @HTTP(method = "DELETE", path = "chatroom/participants", hasBody = true)
    suspend fun leaveSecretChatroom(
        @Body request: _LeaveSecretChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @PUT("chatroom/mute")
    suspend fun muteChatroom(
        @Body request: _MuteChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @POST("chatroom/mark_read")
    suspend fun markReadChatroom(
        @Body request: _MarkReadChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @PUT("conversation/topic")
    suspend fun setChatroomTopic(
        @Body request: _SetChatroomTopicRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @GET("chatroom/participants")
    suspend fun getChatroomParticipants(
        @QueryMap queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_GetChatroomParticipantsResponse_>>
}