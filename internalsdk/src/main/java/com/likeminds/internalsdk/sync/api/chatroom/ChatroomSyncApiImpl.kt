package com.likeminds.internalsdk.sync.api.chatroom

import com.likeminds.internalsdk.sync.model._SyncChatroomResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class ChatroomSyncApiImpl @Inject constructor(private val chatroomSyncReceiver: ChatroomSyncReceiver) :
    ChatroomSyncApi {

    override suspend fun syncChatrooms(queries: HashMap<String, Any?>): NetworkResponse<APIResponse<_SyncChatroomResponse_>> {
        return chatroomSyncReceiver.syncChatrooms(queries)
    }
}