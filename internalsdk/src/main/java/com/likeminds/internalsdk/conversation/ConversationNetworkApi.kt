package com.likeminds.internalsdk.conversation

import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.*

interface ConversationNetworkApi {

    @POST("conversation")
    suspend fun createConversation(
        @Body request: _CreateConversationRequest_
    ): NetworkResponse<APIResponse<_CreateConversationResponse_>>

    @PUT("conversation")
    suspend fun editConversation(
        @Body request: _EditConversationRequest_
    ): NetworkResponse<APIResponse<_EditConversationResponse_>>

    @HTTP(method = "DELETE", path = "conversation", hasBody = true)
    suspend fun deleteConversation(
        @Body request: _DeleteConversationRequest_
    ): NetworkResponse<APIResponse<_DeleteConversationResponse_>>

    @PUT("conversation/reaction")
    suspend fun putReaction(
        @Body request: _PutReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    @HTTP(method = "DELETE", path = "conversation/reaction", hasBody = true)
    suspend fun deleteReaction(
        @Body request: _DeleteReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>>
}