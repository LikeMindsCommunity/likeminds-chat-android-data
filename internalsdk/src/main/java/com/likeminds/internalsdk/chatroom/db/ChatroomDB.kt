package com.likeminds.internalsdk.chatroom.db

import com.likeminds.internalsdk.db.models.ChatroomRO

interface ChatroomDB {

    suspend fun getChatroom(chatroomId: String): ChatroomRO?
}