package com.likeminds.internalsdk.sync.api.chatroom

import com.likeminds.internalsdk.sync.model._SyncChatroomResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface ChatroomSyncApi {

    //api to get chatrooms for home feed
    suspend fun syncChatrooms(
        queries: HashMap<String, Any?>
    ): NetworkResponse<APIResponse<_SyncChatroomResponse_>>
}