package com.likeminds.internalsdk.chatroom.db

import com.likeminds.internalsdk.chatroom.ChatroomReceiver
import com.likeminds.internalsdk.db.models.ChatroomRO
import javax.inject.Inject

class ChatroomDBImpl @Inject constructor(
    private val chatroomReceiver: ChatroomReceiver
) : ChatroomDB {

    override suspend fun getChatroom(chatroomId: String): ChatroomRO? {
        return chatroomReceiver.getChatroom(chatroomId)
    }
}