package com.likeminds.chatinternalsdk.sync.api.chatroom

import com.likeminds.chatinternalsdk.sync.model._SyncChatroomResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

interface ChatroomSyncApi {

    //api to get chatrooms for home feed
    suspend fun syncChatrooms(
        queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_SyncChatroomResponse_>>
}