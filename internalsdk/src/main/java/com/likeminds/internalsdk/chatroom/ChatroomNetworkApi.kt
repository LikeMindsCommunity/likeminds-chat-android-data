package com.likeminds.internalsdk.chatroom

import com.likeminds.internalsdk.chatroom.model._GetChatroomResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ChatroomNetworkApi {

    @GET("chatroom")
    @Headers("x-accept-version: v2")
    suspend fun getChatroom(@Query("chatroom_id") chatroomId: String): NetworkResponse<APIResponse<_GetChatroomResponse_>>
}