package com.likeminds.internalsdk.dm

import com.likeminds.internalsdk.dm.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface DMApi {

    //api to check whether dm is enabled or not
    suspend fun checkDMTab(): NetworkResponse<APIResponse<_CheckDMTabResponse_>>

    // api to check the status of the DM
    suspend fun checkDMStatus(checkDMStatusRequest: _CheckDMStatusRequest_): NetworkResponse<APIResponse<_CheckDMStatusResponse_>>

    //api to send a request for DM
    suspend fun sendDMRequest(sendDMRequest: _SendDMRequest_): NetworkResponse<APIResponse<_SendDMResponse_>>

    //api to block a member
    suspend fun blockMember(blockMemberRequest: _BlockMemberRequest_): NetworkResponse<APIResponse<_BlockMemberResponse_>>

    // api to check the limit of DM
    suspend fun checkDMLimit(checkDMLimitRequest: _CheckDMLimitRequest_): NetworkResponse<APIResponse<_CheckDMLimitResponse_>>

    // api to create a dm chatroom
    suspend fun createDMChatroom(createDMChatroomRequest: _CreateDMChatroomRequest_): NetworkResponse<APIResponse<_CreateDMChatroomResponse_>>
}