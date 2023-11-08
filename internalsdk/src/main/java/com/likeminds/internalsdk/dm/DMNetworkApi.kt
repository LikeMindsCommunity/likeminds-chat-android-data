package com.likeminds.internalsdk.dm

import com.likeminds.internalsdk.dm.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface DMNetworkApi {

    @GET("home/dm/meta")
    suspend fun checkDMTab(): NetworkResponse<APIResponse<_CheckDMTabResponse_>>

    @POST("chatroom/dm/request")
    suspend fun sendDMRequest(
        @Body sendDMRequest: _SendDMRequest_
    ): NetworkResponse<APIResponse<_SendDMResponse_>>

    @GET("community/dm/status")
    suspend fun checkDMStatus(
        @QueryMap queries: HashMap<String, String>
    ): NetworkResponse<APIResponse<_CheckDMStatusResponse_>>

    @POST("chatroom/dm/block")
    suspend fun blockMember(
        @Body blockMemberRequest: _BlockMemberRequest_
    ): NetworkResponse<APIResponse<_BlockMemberResponse_>>

    @GET("chatroom/dm/limit")
    suspend fun checkDMLimit(
        @QueryMap queries: HashMap<String, String>
    ): NetworkResponse<APIResponse<_CheckDMLimitResponse_>>

    @POST("chatroom/dm/create")
    suspend fun createDMChatroom(
        @Body createDMChatroomRequest: _CreateDMChatroomRequest_
    ): NetworkResponse<APIResponse<_CreateDMChatroomResponse_>>
}