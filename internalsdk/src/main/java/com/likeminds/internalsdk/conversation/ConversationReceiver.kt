package com.likeminds.internalsdk.conversation

import com.likeminds.internalsdk.conversation.model._DeleteReactionRequest_
import com.likeminds.internalsdk.conversation.model._PutReactionRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ConversationReceiver @Inject constructor(
    private val conversationNetworkApi: ConversationNetworkApi
) {

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