package com.likeminds.chatinternalsdk.conversation.api

import com.likeminds.chatinternalsdk.conversation.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

interface ConversationApi {

    // api to post conversation
    suspend fun postConversation(
        request: _PostConversationRequest_
    ): NetworkResponse<APIResponse<_PostConversationResponse_>>

    // api to edit conversation
    suspend fun editConversation(
        request: _EditConversationRequest_
    ): NetworkResponse<APIResponse<_EditConversationResponse_>>

    // api to delete conversation
    suspend fun deleteConversations(
        request: _DeleteConversationsRequest_
    ): NetworkResponse<APIResponse<_DeleteConversationsResponse_>>

    // api to react on a conversation
    suspend fun putReaction(
        request: _PutReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to delete reaction on a conversation
    suspend fun deleteReaction(
        request: _DeleteReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    //api to upload conversation media
    suspend fun putMultimedia(
        request: _PutMultimediaRequest_
    ): NetworkResponse<APIResponse<_PutMultimediaResponse_>>
}