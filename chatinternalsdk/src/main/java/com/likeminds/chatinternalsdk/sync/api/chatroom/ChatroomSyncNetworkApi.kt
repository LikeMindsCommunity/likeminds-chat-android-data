package com.likeminds.chatinternalsdk.sync.api.chatroom

import com.likeminds.chatinternalsdk.sync.model._SyncChatroomResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ChatroomSyncNetworkApi {

    @GET("chatroom/sync")
    suspend fun syncChatrooms(
        @QueryMap queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_SyncChatroomResponse_>>
}