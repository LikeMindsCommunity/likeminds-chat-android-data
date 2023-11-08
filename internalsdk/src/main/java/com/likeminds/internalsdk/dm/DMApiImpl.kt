package com.likeminds.internalsdk.dm

import com.likeminds.internalsdk.dm.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class DMApiImpl @Inject constructor(
    private val dmReceiver: DMReceiver
) : DMApi {

    override suspend fun checkDMTab(): NetworkResponse<APIResponse<CheckDMTabResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkDMStatus(checkDMStatusRequest: CheckDMStatusRequest): NetworkResponse<APIResponse<CheckDMStatusResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendDMRequest(sendDMRequest: SendDMRequest): NetworkResponse<APIResponse<SendDMResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun blockMember(blockMemberRequest: BlockMemberRequest): NetworkResponse<APIResponse<BlockMemberResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun checkDMLimit(checkDMLimitRequest: CheckDMLimitRequest): NetworkResponse<APIResponse<CheckDMLimitResponse>> {
        TODO("Not yet implemented")
    }

    override suspend fun createDMChatroom(createDMChatroomRequest: CreateDMChatroomRequest): NetworkResponse<APIResponse<CreateDMChatroomResponse>> {
        TODO("Not yet implemented")
    }
}