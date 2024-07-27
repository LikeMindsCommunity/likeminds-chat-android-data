package com.likeminds.likemindschat.poll

import android.content.Context
import com.likeminds.chatinternalsdk.poll.model.*
import com.likeminds.chatinternalsdk.sync.SyncSDK
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.poll.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.Realm
import javax.inject.Inject

class PollClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().pollComponent()?.inject(this)
    }

    private val pollApi by lazy {
        chatSDK.getPollApi()
    }

    private val conversationDB by lazy {
        chatSDK.getConversationDB()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param postPollConversationRequest - client request model to post a poll conversation
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return PostPollConversationResponse - PostPollConversationResponse model for postPollConversationRequest
     */
    suspend fun postPollConversation(postPollConversationRequest: PostPollConversationRequest): LMResponse<PostPollConversationResponse> {
        // validates the client request
        RequestUtils.validate()
        validatePostPollConversationRequest(postPollConversationRequest)

        // builds internal request model
        val request = _PostPollConversationRequest_.Builder()
            .chatroomId(postPollConversationRequest.chatroomId)
            .text(postPollConversationRequest.text)
            .state(10)
            .repliedConversationId(postPollConversationRequest.repliedConversationId)
            .polls(ModelConverter.createPolls(postPollConversationRequest.polls) ?: emptyList())
            .pollType(postPollConversationRequest.pollType)
            .multipleSelectState(postPollConversationRequest.multipleSelectState)
            .multipleSelectNo(postPollConversationRequest.multipleSelectNo)
            .isAnonymous(postPollConversationRequest.isAnonymous)
            .allowAddOption(postPollConversationRequest.allowAddOption)
            .expiryTime(postPollConversationRequest.expiryTime)
            .temporaryId(postPollConversationRequest.temporaryId)
            .build()

        // calls api and processes the response accordingly
        return when (val response = pollApi.postPollConversation(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                val conversation = body.data?.conversation ?: return LMResponse(
                    success = false,
                    response.body.errorMessage
                )

                // save the conversation in DB
                val realm = Realm.getDefaultInstance()
                conversationDB.saveNewConversation(realm, conversation)

                ModelConverter.convertPostPollConversationAPIResponse(body)
            }
        }
    }

    /**
     * validates [postPollConversationRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validatePostPollConversationRequest(postPollConversationRequest: PostPollConversationRequest) {
        if (postPollConversationRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }

        if (postPollConversationRequest.polls.isEmpty()) {
            RequestUtils.throwException("polls")
        }

        postPollConversationRequest.polls.forEach {
            if (it.text.isEmpty()) {
                RequestUtils.throwException("text")
            }
        }

        if (postPollConversationRequest.text.isEmpty()) {
            RequestUtils.throwException("text")
        }

        if (postPollConversationRequest.pollType == -1) {
            RequestUtils.throwException("pollType")
        }

        if (postPollConversationRequest.expiryTime == -1L) {
            RequestUtils.throwException("pollType")
        }
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

        val conversationId = addPollOptionRequest.conversationId

        // builds internal request model
        val request = _AddPollOptionRequest_.Builder()
            .conversationId(conversationId)
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

                val createdPoll = body.data?.poll

                createdPoll?.let {
                    conversationDB.updatePollConversationAddItem(conversationId, it)
                }

                ModelConverter.convertAddPollOptionAPIResponse(body)
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
     * @param context - context required to start reopen sync
     * @param submitPollRequest - client request model to submit polls selected
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun submitPoll(context: Context, submitPollRequest: SubmitPollRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateSubmitPollRequest(submitPollRequest)

        val conversationId = submitPollRequest.conversationId
        val _polls_ = ModelConverter.createPolls(submitPollRequest.polls)

        // builds internal request model
        val request = _SubmitPollRequest_.Builder()
            .conversationId(conversationId)
            .polls(_polls_ ?: emptyList())
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

                //if success -> make db call
                _polls_?.let { polls ->
                    conversationDB.updateConversationSubmitPoll(
                        conversationId,
                        polls
                    )
                }

                val chatroomId = submitPollRequest.chatroomId

                SyncSDK.startReopenSyncForChatroom(
                    context,
                    chatroomId,
                    conversationId
                )

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
        if (submitPollRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }

        if (submitPollRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }

        if (submitPollRequest.polls.isEmpty()) {
            RequestUtils.throwException("polls")
        }

        submitPollRequest.polls.forEach {
            if (it.text.isEmpty()) {
                RequestUtils.throwException("text")
            }
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getPollUsersRequest - client request model to get users who have voted on that particular poll option
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return GetPollUsersResponse - GetPollUsersResponse model for getPollUsersRequest
     */
    suspend fun getPollUsers(getPollUsersRequest: GetPollUsersRequest): LMResponse<GetPollUsersResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetPollUsersRequest(getPollUsersRequest)

        // builds internal request model
        val request = _GetPollUsersRequest_.Builder()
            .pollId(getPollUsersRequest.pollId)
            .conversationId(getPollUsersRequest.conversationId)
            .build()

        // calls api and processes the response accordingly
        return when (val response = pollApi.getPollUsers(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertGetPollUsersAPIResponse(body)
            }
        }
    }

    /**
     * validates [getPollUsersRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetPollUsersRequest(getPollUsersRequest: GetPollUsersRequest) {
        if (getPollUsersRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }

        if (getPollUsersRequest.pollId.isEmpty()) {
            RequestUtils.throwException("pollId")
        }
    }
}