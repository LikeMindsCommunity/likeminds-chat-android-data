package com.likeminds.chatinternalsdk.sync.api.conversation

import com.likeminds.chatinternalsdk.sync.model._SyncConversationResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ConversationSyncNetworkApi {

    @GET("conversation/sync")
    suspend fun syncConversations(
        @QueryMap queries: HashMap<String, Any>
    ): NetworkResponse<APIResponse<_SyncConversationResponse_>>
}