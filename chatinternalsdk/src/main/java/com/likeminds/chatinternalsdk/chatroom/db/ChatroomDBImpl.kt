package com.likeminds.chatinternalsdk.chatroom.db

import com.likeminds.chatinternalsdk.chatroom.ChatroomReceiver
import com.likeminds.chatinternalsdk.chatroom.model._Chatroom_
import com.likeminds.chatinternalsdk.db.models.ChatroomRO
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.rx.CollectionChange
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

    override fun updateChatRequestState(
        chatroomId: String,
        chatRequestState: Int?,
        chatRequestedById: String?
    ) {
        return chatroomReceiver.updateChatRequestState(
            chatroomId,
            chatRequestState,
            chatRequestedById
        )
    }

    override fun observeDMChatrooms(realm: Realm): Observable<CollectionChange<RealmResults<ChatroomRO>>>? {
        return chatroomReceiver.observeDMChatrooms(realm)
    }

    override fun saveChatroom(chatroom: _Chatroom_) {
        return chatroomReceiver.saveChatroom(chatroom)
    }

    override fun getJoinedChatroomsCount(realm: Realm): Pair<Int, Int> {
        return chatroomReceiver.getJoinedChatroomsCount(realm)
    }

    override fun getUnreadConversationsCount(realm: Realm): Pair<Int, Int> {
        return chatroomReceiver.getUnreadConversationsCount(realm)
    }

    override fun getExistingDMChatroom(realm: Realm, userUUID: String): ChatroomRO? {
        return chatroomReceiver.getExistingDMChatroom(realm, userUUID)
    }
}