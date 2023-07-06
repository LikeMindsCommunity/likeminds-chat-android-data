package com.likeminds.internalsdk.chatroom.db

import com.likeminds.internalsdk.chatroom.ChatroomReceiver
import com.likeminds.internalsdk.db.models.ChatroomRO
import javax.inject.Inject

class ChatroomDBImpl @Inject constructor(
    private val chatroomReceiver: ChatroomReceiver
) : ChatroomDB {

    override fun getChatroom(chatroomId: String): ChatroomRO? {
        return chatroomReceiver.getChatroom(chatroomId)
    }

    override fun updateChatroomFollowStatus(chatroomId: String, value: Boolean) {
        chatroomReceiver.updateChatroomFollowStatus(chatroomId, value)
    }

    override fun updateChatroomMuteStatus(chatroomId: String, value: Boolean) {
        chatroomReceiver.updateChatroomMuteStatus(chatroomId, value)
    }

    override fun updateSecretChatroomLeaveStatus(chatroomId: String) {
        chatroomReceiver.updateSecretChatroomLeaveStatus(chatroomId)
    }

    override fun updateChatroomTitle(chatroomId: String, updatedTitle: String) {
        chatroomReceiver.updateChatroomTitle(chatroomId, updatedTitle)
    }

    override fun updateChatroomTopic(chatroomId: String, topicId: String) {
        chatroomReceiver.updateChatroomTopic(chatroomId, topicId)
    }

    override fun updateChatroomReaction(reaction: String, chatroomId: String, memberId: String) {
        chatroomReceiver.updateChatroomReaction(reaction, chatroomId, memberId)
    }

    override fun removeChatroomReaction(chatroomId: String) {
        chatroomReceiver.removeChatroomReaction(chatroomId)
    }
}