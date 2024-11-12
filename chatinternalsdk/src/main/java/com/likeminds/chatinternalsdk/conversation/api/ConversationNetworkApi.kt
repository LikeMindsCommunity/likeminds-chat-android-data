package com.likeminds.chatinternalsdk.conversation.api

import com.likeminds.chatinternalsdk.conversation.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface ConversationNetworkApi {

    @POST("conversation")
    @Headers("x-api-version: 1")
    suspend fun postConversation(
        @Body request: _PostConversationRequest_
    ): NetworkResponse<APIResponse<_PostConversationResponse_>>

    @PUT("conversation")
    suspend fun editConversation(
        @Body request: _EditConversationRequest_
    ): NetworkResponse<APIResponse<_EditConversationResponse_>>

    @HTTP(method = "DELETE", path = "conversation", hasBody = true)
    suspend fun deleteConversations(
        @Body request: _DeleteConversationsRequest_
    ): NetworkResponse<APIResponse<_DeleteConversationsResponse_>>

    @PUT("conversation/reaction")
    suspend fun putReaction(
        @Body request: _PutReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @HTTP(method = "DELETE", path = "conversation/reaction", hasBody = true)
    suspend fun deleteReaction(
        @Body request: _DeleteReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>>
}