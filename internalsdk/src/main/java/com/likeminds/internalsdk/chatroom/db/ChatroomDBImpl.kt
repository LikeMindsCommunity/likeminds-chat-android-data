package com.likeminds.internalsdk.chatroom.db

import com.likeminds.internalsdk.chatroom.ChatroomReceiver
import com.likeminds.internalsdk.db.models.ChatroomRO
import io.realm.Realm
import javax.inject.Inject

class ChatroomDBImpl @Inject constructor(
    private val chatroomReceiver: ChatroomReceiver
) : ChatroomDB {

    override fun getChatroom(realm: Realm, chatroomId: String): ChatroomRO? {
        return chatroomReceiver.getChatroom(realm, chatroomId)
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

    override fun updateChatroomReaction(reaction: String, chatroomId: String) {
        chatroomReceiver.updateChatroomReaction(reaction, chatroomId)
    }

    override fun removeChatroomReaction(chatroomId: String) {
        chatroomReceiver.removeChatroomReaction(chatroomId)
    }

    override fun updateLastSeenAndDraft(chatroomId: String, draft: String?) {
        chatroomReceiver.updateLastSeenAndDraft(chatroomId, draft)
    }
}