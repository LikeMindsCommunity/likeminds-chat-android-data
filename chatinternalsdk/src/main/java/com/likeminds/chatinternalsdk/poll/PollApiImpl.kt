package com.likeminds.chatinternalsdk.poll

import com.likeminds.chatinternalsdk.poll.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class PollApiImpl @Inject constructor(
    private val pollReceiver: PollReceiver
) : PollApi {

    override suspend fun postPollConversation(
        request: _PostPollConversationRequest_
    ): NetworkResponse<APIResponse<_PostPollConversationResponse_>> {
        return pollReceiver.postPollConversation(request)
    }

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