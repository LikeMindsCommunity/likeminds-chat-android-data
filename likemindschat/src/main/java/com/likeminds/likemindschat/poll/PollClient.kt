package com.likeminds.likemindschat.poll

import com.likeminds.internalsdk.poll.model._AddPollOptionRequest_
import com.likeminds.internalsdk.poll.model._SubmitPollRequest_
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.poll.model.AddPollOptionRequest
import com.likeminds.likemindschat.poll.model.AddPollOptionResponse
import com.likeminds.likemindschat.poll.model.SubmitPollRequest
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class PollClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().pollComponent()?.inject(this)
    }

    private val pollApi by lazy {
        groupChatSDK.getPollApi()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param addPollOptionRequest - client request model to add poll option
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return AddPollOptionResponse - AddPollOptionResponse model for addPollOptionRequest
     */
    suspend fun addPollOption(addPollOptionRequest: AddPollOptionRequest): LMResponse<AddPollOptionResponse> {
        // validates the client request
        RequestUtils.validate()
        validateAddPollOptionRequest(addPollOptionRequest)

        // builds internal request model
        val request = _AddPollOptionRequest_.Builder()
            .conversationId(addPollOptionRequest.conversationId)
            .poll(ModelConverter.createPoll(addPollOptionRequest.poll))
            .build()

        // calls api and processes the response accordingly
        return when (val response = pollApi.addPollOption(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )
            }
            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertAddPollOptionResponse(body)
            }
        }
    }

    /**
     * validates [addPollOptionRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateAddPollOptionRequest(addPollOptionRequest: AddPollOptionRequest) {
        if (addPollOptionRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }

        if (addPollOptionRequest.poll.text.isEmpty()) {
            RequestUtils.throwException("text")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param submitPollRequest - client request model to submit polls selected
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun submitPoll(submitPollRequest: SubmitPollRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateSubmitPollRequest(submitPollRequest)

        // builds internal request model
        val request = _SubmitPollRequest_.Builder()
            .conversationId(submitPollRequest.conversationId)
            .polls(ModelConverter.createPolls(submitPollRequest.polls))
            .build()

        // calls api and processes the response accordingly
        return when (val response = pollApi.submitPoll(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )
            }
            is NetworkResponse.Success -> {
                LMResponse(
                    success = response.body.success,
                )
            }
        }
    }

    /**
     * validates [submitPollRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateSubmitPollRequest(submitPollRequest: SubmitPollRequest) {
        // todo:
        if (submitPollRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }
    }
}