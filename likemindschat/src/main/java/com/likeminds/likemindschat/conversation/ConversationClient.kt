package com.likeminds.likemindschat.conversation

import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.conversation.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class ConversationClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().conversationComponent()?.inject(this)
    }

    private val conversationApi by lazy {
        groupChatSDK.getConversationApi()
    }

    private val conversationDB by lazy {
        groupChatSDK.getConversationDB()
    }

    suspend fun postConversation(postConversationRequest: PostConversationRequest): LMResponse<PostConversationResponse> {
        // validates the client request
        RequestUtils.validate()
        validatePostConversationRequest(postConversationRequest)

        val request = _CreateConversationRequest_.Builder()
            .chatroomId(postConversationRequest.chatroomId)
            .text(postConversationRequest.text)
            .shareLink(postConversationRequest.shareLink)
            .ogTags(ModelConverter.createLinkOGTags(postConversationRequest.ogTags))
            .repliedConversationId(postConversationRequest.repliedConversationId)
            .attachmentCount(postConversationRequest.attachmentCount)
            .temporaryId(postConversationRequest.temporaryId)
            .repliedChatroomId(postConversationRequest.repliedChatroomId)
            .build()

        return when (val response = conversationApi.createConversation(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertPostConversationAPIResponse(body)
            }
        }
    }

    private fun validatePostConversationRequest(postConversationRequest: PostConversationRequest) {
        if (postConversationRequest.chatroomId.isEmpty()) {
            RequestUtils.throwException("chatroomId")
        }
        if (postConversationRequest.text.isEmpty()
            && (postConversationRequest.attachmentCount ?: 0) <= 0
        ) {
            RequestUtils.throwException("text")
        }
    }

    fun getConversation(getConversationRequest: GetConversationRequest): LMResponse<GetConversationResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetConversationRequest(getConversationRequest)

        val conversationRO = conversationDB.getConversation(getConversationRequest.conversationId)
        return if (conversationRO == null) {
            LMResponse(
                success = false,
                errorMessage = "Conversation w.r.t conversation id not found"
            )
        } else {
            LMResponse(
                success = true,
                errorMessage = null,
                ModelConverter.convertGetConversationResponse(conversationRO)
            )
        }
    }

    private fun validateGetConversationRequest(getConversationRequest: GetConversationRequest) {
        if (getConversationRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }
    }

    suspend fun editConversation(editConversationRequest: EditConversationRequest): LMResponse<EditConversationResponse> {
        // validates the client request
        RequestUtils.validate()
        validateEditConversationRequest(editConversationRequest)

        val request = _EditConversationRequest_.Builder()
            .conversationId(editConversationRequest.conversationId)
            .text(editConversationRequest.text)
            .shareLink(editConversationRequest.shareLink)
            .build()

        return when (val response = conversationApi.editConversation(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                val conversation = body.data?.conversation

                //if success -> make db query
                conversation?.let {
                    conversationDB.updateEditedConversation(it.id ?: "", it.answer, it.ogTags)
                }

                ModelConverter.convertEditConversationAPIResponse(body)
            }
        }
    }

    private fun validateEditConversationRequest(editConversationRequest: EditConversationRequest) {
        if (editConversationRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }
        if (editConversationRequest.text.isEmpty()) {
            RequestUtils.throwException("text")
        }
    }

    suspend fun deleteConversations(deleteConversationRequest: DeleteConversationRequest): LMResponse<DeleteConversationResponse> {
        // validates the client request
        RequestUtils.validate()
        validateDeleteConversationRequest(deleteConversationRequest)

        val request = _DeleteConversationRequest_.Builder()
            .conversationIds(deleteConversationRequest.conversationIds)
            .build()

        return when (val response = conversationApi.deleteConversation(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    response.body.success,
                    response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body

                //if success -> make db query
                conversationDB.updateDeletedConversations(deleteConversationRequest.conversationIds)

                ModelConverter.convertDeleteConversationAPIResponse(body)
            }
        }
    }

    private fun validateDeleteConversationRequest(deleteConversationRequest: DeleteConversationRequest) {
        if (deleteConversationRequest.conversationIds.isEmpty()) {
            RequestUtils.throwException("conversationIds")
        }
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

                putReactionRequest.conversationId?.let { conversationId ->
                    conversationDB.updateConversationReaction(
                        putReactionRequest.reaction,
                        conversationId
                    )
                }

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

                deleteReactionRequest.conversationId?.let {
                    conversationDB.removeConversationReaction(it)
                }

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

    suspend fun putMultimedia(
        putMultimediaRequest: PutMultimediaRequest
    ): LMResponse<PutMultimediaResponse> {
        // validates the client request
        RequestUtils.validate()
        validatePutMultimediaRequest(putMultimediaRequest)

        val request = _PutMultimediaRequest_.Builder()
            .conversationId(putMultimediaRequest.conversationId)
            .name(putMultimediaRequest.name)
            .url(putMultimediaRequest.url)
            .thumbnailUrl(putMultimediaRequest.thumbnailUrl)
            .type(putMultimediaRequest.type)
            .filesCount(putMultimediaRequest.filesCount)
            .index(putMultimediaRequest.index)
            .width(putMultimediaRequest.width)
            .height(putMultimediaRequest.height)
            .meta(ModelConverter.createAttachmentMeta(putMultimediaRequest.meta))
            .build()

        return when (val response = conversationApi.putMultimedia(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertPutMultimediaAPIResponse(body)
            }
        }
    }

    private fun validatePutMultimediaRequest(putMultimediaRequest: PutMultimediaRequest) {
        if (putMultimediaRequest.conversationId.isEmpty()) {
            RequestUtils.throwException("conversationId")
        }
        if (putMultimediaRequest.url.isEmpty()) {
            RequestUtils.throwException("url")
        }
    }
}