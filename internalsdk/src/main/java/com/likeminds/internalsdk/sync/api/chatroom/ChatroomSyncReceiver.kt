package com.likeminds.internalsdk.sync.api.chatroom

import com.likeminds.internalsdk.sync.model._SyncChatroomResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ChatroomSyncReceiver @Inject constructor(private val chatroomSyncNetworkApi: ChatroomSyncNetworkApi) {

    suspend fun syncChatrooms(
        queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_SyncChatroomResponse_>> {
        return chatroomSyncNetworkApi.syncChatrooms(queries)
    }
}