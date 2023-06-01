package com.likeminds.internalsdk.poll

import com.likeminds.internalsdk.poll.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class PollReceiver @Inject constructor(
    private val pollNetworkApi: PollNetworkApi
) {

    suspend fun addPollOption(
        request: _AddPollOptionRequest_
    ): NetworkResponse<APIResponse<_AddPollOptionResponse_>> {
        return pollNetworkApi.addPollOption(request)
    }

    suspend fun submitPoll(
        request: _SubmitPollRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return pollNetworkApi.submitPoll(request)
    }

    suspend fun getPollUsers(
        request: _GetPollUsersRequest_
    ): NetworkResponse<APIResponse<_GetPollUsersResponse_>> {
        val pollId = request.pollId
        val conversationId = request.conversationId
        return pollNetworkApi.getPollUsers(pollId, conversationId)
    }
}