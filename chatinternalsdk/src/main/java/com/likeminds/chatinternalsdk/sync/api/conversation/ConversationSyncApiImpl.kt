package com.likeminds.chatinternalsdk.sync.api.conversation

import com.likeminds.chatinternalsdk.sync.model._SyncConversationResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ConversationSyncApiImpl @Inject constructor(
    private val conversationSyncReceiver: ConversationSyncReceiver
) : ConversationSyncApi {

    override suspend fun syncConversations(queries: HashMap<String, Any>): NetworkResponse<APIResponse<_SyncConversationResponse_>> {
        return conversationSyncReceiver.syncConversations(queries)
    }
}