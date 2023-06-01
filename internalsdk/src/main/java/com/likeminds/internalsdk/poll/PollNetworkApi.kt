package com.likeminds.internalsdk.poll

import com.likeminds.internalsdk.poll.model._AddPollOptionRequest_
import com.likeminds.internalsdk.poll.model._AddPollOptionResponse_
import com.likeminds.internalsdk.poll.model._GetPollUsersResponse_
import com.likeminds.internalsdk.poll.model._SubmitPollRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PollNetworkApi {

    @POST("conversation/poll")
    suspend fun addPollOption(
        @Body request: _AddPollOptionRequest_
    ): NetworkResponse<APIResponse<_AddPollOptionResponse_>>

    @POST("conversation/poll/submit")
    suspend fun submitPoll(
        @Body request: _SubmitPollRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @GET("conversation/poll/users")
    suspend fun getPollUsers(
        @Query("poll_id") pollId: String,
        @Query("conversation_id") conversationId: String
    ): NetworkResponse<APIResponse<_GetPollUsersResponse_>>
}