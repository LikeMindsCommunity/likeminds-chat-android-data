package com.likeminds.internalsdk.dm

import com.likeminds.internalsdk.dm.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class DMReceiver @Inject constructor(
    private val dmNetworkApi: DMNetworkApi
) {

    companion object {
        const val REQ_FROM = "req_from"
        const val CHATROOM_ID = "chatroom_id"
        const val UUID = "uuid"
    }

    suspend fun checkDMTab(): NetworkResponse<APIResponse<_CheckDMTabResponse_>> {
        return dmNetworkApi.checkDMTab()
    }

    suspend fun checkDMStatus(
        checkDMStatusRequest: _CheckDMStatusRequest_
    ): NetworkResponse<APIResponse<_CheckDMStatusResponse_>> {
        val queries = HashMap<String, String>()
        queries[REQ_FROM] = checkDMStatusRequest.requestFrom
        if (!checkDMStatusRequest.chatroomId.isNullOrEmpty()) {
            queries[CHATROOM_ID] = checkDMStatusRequest.chatroomId
        }
        if (!checkDMStatusRequest.uuid.isNullOrEmpty()) {
            queries[UUID] = checkDMStatusRequest.uuid
        }
        return dmNetworkApi.checkDMStatus(queries)
    }

    suspend fun sendDMRequest(sendDMRequest: _SendDMRequest_): NetworkResponse<APIResponse<_SendDMResponse_>> {
        return dmNetworkApi.sendDMRequest(sendDMRequest)
    }

    suspend fun blockMember(blockMemberRequest: _BlockMemberRequest_): NetworkResponse<APIResponse<_BlockMemberResponse_>> {
        return dmNetworkApi.blockMember(blockMemberRequest)
    }

    suspend fun checkDMLimit(checkDMLimitRequest: _CheckDMLimitRequest_): NetworkResponse<APIResponse<_CheckDMLimitResponse_>> {
        val queries = HashMap<String, String>()
        queries[UUID] = checkDMLimitRequest.uuid
        return dmNetworkApi.checkDMLimit(queries)
    }

    suspend fun createDMChatroom(createDMChatroomRequest: _CreateDMChatroomRequest_): NetworkResponse<APIResponse<_CreateDMChatroomResponse_>> {
        return dmNetworkApi.createDMChatroom(createDMChatroomRequest)
    }
}