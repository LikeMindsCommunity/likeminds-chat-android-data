package com.likeminds.internalsdk.conversation

import com.likeminds.internalsdk.conversation.model._DeleteReactionRequest_
import com.likeminds.internalsdk.conversation.model._PutReactionRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ConversationApiImpl @Inject constructor(
    private val conversationReceiver: ConversationReceiver
) : ConversationApi {

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