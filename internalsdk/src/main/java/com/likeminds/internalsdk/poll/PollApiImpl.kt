package com.likeminds.internalsdk.poll

import com.likeminds.internalsdk.poll.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class PollApiImpl @Inject constructor(
    private val pollReceiver: PollReceiver
) : PollApi {

    override suspend fun addPollOption(
        request: _AddPollOptionRequest_
    ): NetworkResponse<APIResponse<_AddPollOptionResponse_>> {
        return pollReceiver.addPollOption(request)
    }

    override suspend fun submitPoll(
        request: _SubmitPollRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return pollReceiver.submitPoll(request)
    }

    override suspend fun getPollUsers(
        request: _GetPollUsersRequest_
    ): NetworkResponse<APIResponse<_GetPollUsersResponse_>> {
        return pollReceiver.getPollUsers(request)
    }
}