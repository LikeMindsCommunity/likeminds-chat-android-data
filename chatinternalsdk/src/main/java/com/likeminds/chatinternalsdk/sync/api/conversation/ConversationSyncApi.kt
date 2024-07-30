package com.likeminds.chatinternalsdk.sync.api.conversation

import com.likeminds.chatinternalsdk.sync.model._SyncConversationResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

interface ConversationSyncApi {

    //api to get conversations through sync apis
    suspend fun syncConversations(
        queries: HashMap<String, Any>
    ): NetworkResponse<APIResponse<_SyncConversationResponse_>>
}