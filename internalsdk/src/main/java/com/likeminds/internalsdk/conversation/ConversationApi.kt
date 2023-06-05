package com.likeminds.internalsdk.conversation

import com.likeminds.internalsdk.conversation.model._DeleteReactionRequest_
import com.likeminds.internalsdk.conversation.model._PutReactionRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface ConversationApi {

    // api to react on a conversation
    suspend fun putReaction(
        request: _PutReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>>

    // api to delete reaction on a conversation
    suspend fun deleteReaction(
        request: _DeleteReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>>
}