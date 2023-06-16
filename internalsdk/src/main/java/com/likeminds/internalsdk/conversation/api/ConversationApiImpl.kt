package com.likeminds.internalsdk.conversation.api

import com.likeminds.internalsdk.conversation.ConversationReceiver
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ConversationApiImpl @Inject constructor(
    private val conversationReceiver: ConversationReceiver
) : ConversationApi {

    override suspend fun createConversation(
        request: _CreateConversationRequest_
    ): NetworkResponse<APIResponse<_CreateConversationResponse_>> {
        return conversationReceiver.createConversation(request)
    }

    override suspend fun editConversation(
        request: _EditConversationRequest_
    ): NetworkResponse<APIResponse<_EditConversationResponse_>> {
        return conversationReceiver.editConversation(request)
    }

    override suspend fun deleteConversation(
        request: _DeleteConversationRequest_
    ): NetworkResponse<APIResponse<_DeleteConversationResponse_>> {
        return conversationReceiver.deleteConversation(request)
    }

    override suspend fun putReaction(
        request: _PutReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return conversationReceiver.putReaction(request)
    }

    override suspend fun deleteReaction(
        request: _DeleteReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return conversationReceiver.deleteReaction(request)
    }
}