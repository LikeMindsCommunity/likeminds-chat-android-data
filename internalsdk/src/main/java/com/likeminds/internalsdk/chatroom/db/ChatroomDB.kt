package com.likeminds.internalsdk.chatroom.db

import com.likeminds.internalsdk.db.models.ChatroomRO

interface ChatroomDB {

    suspend fun getChatroom(chatroomId: String): ChatroomRO?

    fun updateChatroomFollowStatus(chatroomId: String, value: Boolean)

    fun updateChatroomMuteStatus(chatroomId: String, value: Boolean)

    fun updateSecretChatroomLeaveStatus(chatroomId: String)

    fun updateChatroomTitle(chatroomId: String, updatedTitle: String)

    fun updateChatroomTopic(chatroomId: String, topicId: String)

}