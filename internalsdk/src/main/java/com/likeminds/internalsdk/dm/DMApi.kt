package com.likeminds.internalsdk.dm

import com.likeminds.internalsdk.dm.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface DMApi {

    //api to check whether API dm is enabled or not
    suspend fun checkDMTab(): NetworkResponse<APIResponse<CheckDMTabResponse>>

    // api to check the status of the DM
    suspend fun checkDMStatus(checkDMStatusRequest: CheckDMStatusRequest): NetworkResponse<APIResponse<CheckDMStatusResponse>>

    //api to send a request for DM
    suspend fun sendDMRequest(sendDMRequest: SendDMRequest): NetworkResponse<APIResponse<SendDMResponse>>

    //api to block a member
    suspend fun blockMember(blockMemberRequest: BlockMemberRequest): NetworkResponse<APIResponse<BlockMemberResponse>>

    // api to check the limit of DM
    suspend fun checkDMLimit(checkDMLimitRequest: CheckDMLimitRequest): NetworkResponse<APIResponse<CheckDMLimitResponse>>

    // api to create a dm chatroom
    suspend fun createDMChatroom(createDMChatroomRequest: CreateDMChatroomRequest): NetworkResponse<APIResponse<CreateDMChatroomResponse>>
}