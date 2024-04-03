package com.likeminds.internalsdk.dm

import com.likeminds.internalsdk.dm.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class DMApiImpl @Inject constructor(
    private val dmReceiver: DMReceiver
) : DMApi {

    override suspend fun checkDMTab(): NetworkResponse<APIResponse<_CheckDMTabResponse_>> {
        return dmReceiver.checkDMTab()
    }

    override suspend fun checkDMStatus(checkDMStatusRequest: _CheckDMStatusRequest_): NetworkResponse<APIResponse<_CheckDMStatusResponse_>> {
        return dmReceiver.checkDMStatus(checkDMStatusRequest)
    }

    override suspend fun sendDMRequest(sendDMRequest: _SendDMRequest_): NetworkResponse<APIResponse<_SendDMResponse_>> {
        return dmReceiver.sendDMRequest(sendDMRequest)
    }

    override suspend fun blockMember(blockMemberRequest: _BlockMemberRequest_): NetworkResponse<APIResponse<_BlockMemberResponse_>> {
        return dmReceiver.blockMember(blockMemberRequest)
    }

    override suspend fun checkDMLimit(checkDMLimitRequest: _CheckDMLimitRequest_): NetworkResponse<APIResponse<_CheckDMLimitResponse_>> {
        return dmReceiver.checkDMLimit(checkDMLimitRequest)
    }

    override suspend fun createDMChatroom(createDMChatroomRequest: _CreateDMChatroomRequest_): NetworkResponse<APIResponse<_CreateDMChatroomResponse_>> {
        return dmReceiver.createDMChatroom(createDMChatroomRequest)
    }
}