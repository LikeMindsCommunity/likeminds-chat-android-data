package com.likeminds.internalsdk.sync.api.chatroom

import com.likeminds.internalsdk.sync.model._SyncChatroomResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ChatroomSyncNetworkApi {

    @GET("chatroom/sync")
    suspend fun syncChatrooms(
        @QueryMap queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_SyncChatroomResponse_>>
}