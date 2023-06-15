package com.likeminds.likemindschat.conversation

import com.likeminds.internalsdk.conversation.model._DeleteReactionRequest_
import com.likeminds.internalsdk.conversation.model._PutReactionRequest_
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.conversation.model.DeleteReactionRequest
import com.likeminds.likemindschat.conversation.model.PutReactionRequest
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class ConversationClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().conversationComponent()?.inject(this)
    }

    private val conversationApi by lazy {
        groupChatSDK.getConversationApi()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param putReactionRequest - client request model to put a reaction on a conversation
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun putReaction(putReactionRequest: PutReactionRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validatePutReactionRequest(putReactionRequest)

        // builds internal request model
        val request =
            _PutReactionRequest_.Builder()
                .chatroomId(putReactionRequest.chatroomId)
                .conversationId(putReactionRequest.conversationId)
                .reaction(putReactionRequest.reaction)
                .build()

        // calls api and processes the response accordingly
        return when (val response = conversationApi.putReaction(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }
            is NetworkResponse.Success -> {
                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [putReactionRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validatePutReactionRequest(putReactionRequest: PutReactionRequest) {
        if (putReactionRequest.chatroomId.isNullOrEmpty() && putReactionRequest.conversationId.isNullOrEmpty()) {
            RequestUtils.throwException("conversationId")
        }
        if (putReactionRequest.reaction.isEmpty()) {
            RequestUtils.throwException("reaction")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param deleteReactionRequest - client request model to delete a reaction on a conversation
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun deleteReaction(deleteReactionRequest: DeleteReactionRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateDeleteReactionRequest(deleteReactionRequest)

        // builds internal request model
        val request =
            _DeleteReactionRequest_.Builder()
                .chatroomId(deleteReactionRequest.chatroomId)
                .conversationId(deleteReactionRequest.conversationId)
                .build()

        // calls api and processes the response accordingly
        return when (val response = conversationApi.deleteReaction(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }
            is NetworkResponse.Success -> {
                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [deleteReactionRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateDeleteReactionRequest(deleteReactionRequest: DeleteReactionRequest) {
        if (deleteReactionRequest.chatroomId.isNullOrEmpty() && deleteReactionRequest.conversationId.isNullOrEmpty()) {
            RequestUtils.throwException("conversationId")
        }
    }
}