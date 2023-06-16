package com.likeminds.internalsdk.conversation.api

import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface ConversationApi {

    suspend fun createConversation(
        request: _CreateConversationRequest_
    ): NetworkResponse<APIResponse<_CreateConversationResponse_>>

    suspend fun editConversation(
        request: _EditConversationRequest_
    ): NetworkResponse<APIResponse<_EditConversationResponse_>>

    suspend fun deleteConversation(
        request: _DeleteConversationRequest_
    ): NetworkResponse<APIResponse<_DeleteConversationResponse_>>

    // api to react on a conversation
    suspend fun putReaction(
        request: _PutReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to delete reaction on a conversation
    suspend fun deleteReaction(
        request: _DeleteReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    //api to upload conversation
    suspend fun putMultimedia(
        request: _PutMultimediaRequest_
    ): NetworkResponse<APIResponse<_PutMultimediaResponse_>>
}