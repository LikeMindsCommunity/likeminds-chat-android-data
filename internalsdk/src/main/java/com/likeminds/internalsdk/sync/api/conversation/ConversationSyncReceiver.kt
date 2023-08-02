package com.likeminds.internalsdk.sync.api.conversation

import com.likeminds.internalsdk.sync.model._SyncConversationResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ConversationSyncReceiver @Inject constructor(private val conversationSyncNetworkApi: ConversationSyncNetworkApi) {

    suspend fun syncConversations(
        queries: HashMap<String, Any>
    ): NetworkResponse<APIResponse<_SyncConversationResponse_>> {
        return conversationSyncNetworkApi.syncConversations(queries)
    }
}