package com.likeminds.internalsdk.poll

import com.likeminds.internalsdk.poll.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface PollApi {

    // api to post a poll conversation
    suspend fun postPollConversation(
        request: _PostPollConversationRequest_
    ): NetworkResponse<APIResponse<_PostPollConversationResponse_>>

    // api to add options to a poll
    suspend fun addPollOption(
        request: _AddPollOptionRequest_
    ): NetworkResponse<APIResponse<_AddPollOptionResponse_>>

    // api to submit polls selected
    suspend fun submitPoll(
        request: _SubmitPollRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to get users who have voted on the specified poll
    suspend fun getPollUsers(
        request: _GetPollUsersRequest_
    ): NetworkResponse<APIResponse<_GetPollUsersResponse_>>
}