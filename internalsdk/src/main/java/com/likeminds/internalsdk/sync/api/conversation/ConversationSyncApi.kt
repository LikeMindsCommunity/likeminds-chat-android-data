package com.likeminds.internalsdk.sync.api.conversation

import com.likeminds.internalsdk.sync.model._SyncConversationResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface ConversationSyncApi {

    //api to get conversations through sync apis
    suspend fun syncConversations(
        queries: HashMap<String, Any>
    ): NetworkResponse<APIResponse<_SyncConversationResponse_>>
}