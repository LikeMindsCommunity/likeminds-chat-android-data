package com.likeminds.chatinternalsdk.conversation.api

import com.likeminds.chatinternalsdk.conversation.ConversationReceiver
import com.likeminds.chatinternalsdk.conversation.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ConversationApiImpl @Inject constructor(
    private val conversationReceiver: ConversationReceiver
) : ConversationApi {

    override suspend fun postConversation(
        request: _PostConversationRequest_
    ): NetworkResponse<APIResponse<_PostConversationResponse_>> {
        return conversationReceiver.postConversation(request)
    }

    override suspend fun editConversation(
        request: _EditConversationRequest_
    ): NetworkResponse<APIResponse<_EditConversationResponse_>> {
        return conversationReceiver.editConversation(request)
    }

    override suspend fun deleteConversations(
        request: _DeleteConversationsRequest_
    ): NetworkResponse<APIResponse<_DeleteConversationsResponse_>> {
        return conversationReceiver.deleteConversations(request)
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