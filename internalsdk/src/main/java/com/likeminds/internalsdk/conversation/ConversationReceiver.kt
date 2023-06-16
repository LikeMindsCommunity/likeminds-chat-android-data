package com.likeminds.internalsdk.conversation

import com.likeminds.internalsdk.conversation.api.ConversationNetworkApi
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ConversationReceiver @Inject constructor(
    private val conversationNetworkApi: ConversationNetworkApi
) {

    suspend fun createConversation(
        request: _CreateConversationRequest_
    ): NetworkResponse<APIResponse<_CreateConversationResponse_>> {
        return conversationNetworkApi.createConversation(request)
    }

    suspend fun editConversation(
        request: _EditConversationRequest_
    ): NetworkResponse<APIResponse<_EditConversationResponse_>> {
        return conversationNetworkApi.editConversation(request)
    }

    suspend fun deleteConversation(
        request: _DeleteConversationRequest_
    ): NetworkResponse<APIResponse<_DeleteConversationResponse_>> {
        return conversationNetworkApi.deleteConversation(request)
    }

    suspend fun putReaction(
        request: _PutReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return conversationNetworkApi.putReaction(request)
    }

    suspend fun deleteReaction(
        request: _DeleteReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return conversationNetworkApi.deleteReaction(request)
    }
}