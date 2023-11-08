package com.likeminds.internalsdk.dm

import com.likeminds.internalsdk.dm.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface DMNetworkApi {

    @GET("home/dm/meta")
    suspend fun checkDMTab(): NetworkResponse<APIResponse<CheckDMTabResponse>>

    @POST("chatroom/dm/request")
    suspend fun sendDMRequest(
        @Body sendDMRequest: SendDMRequest
    ): NetworkResponse<APIResponse<SendDMResponse>>

    @GET("community/dm/status")
    suspend fun checkDMStatus(
        @QueryMap queries: HashMap<String, String>
    ): NetworkResponse<APIResponse<CheckDMStatusResponse>>

    @POST("chatroom/dm/block")
    suspend fun blockMember(
        @Body blockMemberRequest: BlockMemberRequest
    ): NetworkResponse<APIResponse<BlockMemberResponse>>

    @GET("chatroom/dm/limit")
    suspend fun checkDMLimit(
        @QueryMap queries: HashMap<String, String>
    ): NetworkResponse<APIResponse<CheckDMLimitResponse>>

    @POST("chatroom/dm/create")
    suspend fun createDMChatroom(
        @Body createDMChatroomRequest: CreateDMChatroomRequest
    ): NetworkResponse<APIResponse<CreateDMChatroomResponse>>
}