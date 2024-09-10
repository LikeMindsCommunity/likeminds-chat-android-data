package com.likeminds.chatinternalsdk.notification

import com.likeminds.chatinternalsdk.chatroom.model._Chatroom_
import com.likeminds.chatinternalsdk.conversation.model._Conversation_
import com.likeminds.chatinternalsdk.db.models.ChatroomRO
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

class NotificationDBImpl @Inject constructor(
    private val notificationReceiver: NotificationReceiver
) : NotificationDB {

    override suspend fun getUnreadChatrooms(
        realm: Realm,
        chatroom: _Chatroom_,
        chatroomLastConversation: _Conversation_
    ): RealmResults<ChatroomRO> {
        return notificationReceiver.getUnreadChatrooms(
            realm,
            chatroom,
            chatroomLastConversation
        )
    }
}