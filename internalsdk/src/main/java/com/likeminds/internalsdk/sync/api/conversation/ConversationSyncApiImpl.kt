package com.likeminds.internalsdk.sync.api.conversation

import com.likeminds.internalsdk.sync.model._SyncConversationResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ConversationSyncApiImpl @Inject constructor(
    private val conversationSyncReceiver: ConversationSyncReceiver
) : ConversationSyncApi {

    override suspend fun syncConversations(queries: HashMap<String, Any>): NetworkResponse<APIResponse<_SyncConversationResponse_>> {
        return conversationSyncReceiver.syncConversations(queries)
    }
}