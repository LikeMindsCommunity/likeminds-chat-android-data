package com.likeminds.chatinternalsdk.sync.api.chatroom

import com.likeminds.chatinternalsdk.sync.model._SyncChatroomResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ChatroomSyncApiImpl @Inject constructor(private val chatroomSyncReceiver: ChatroomSyncReceiver) :
    ChatroomSyncApi {

    override suspend fun syncChatrooms(queries: HashMap<String, Any?>): NetworkResponse<APIResponse<_SyncChatroomResponse_>> {
        return chatroomSyncReceiver.syncChatrooms(queries)
    }
}