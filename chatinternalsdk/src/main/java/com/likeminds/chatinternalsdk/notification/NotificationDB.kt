package com.likeminds.chatinternalsdk.notification

import com.likeminds.chatinternalsdk.chatroom.model._Chatroom_
import com.likeminds.chatinternalsdk.conversation.model._Conversation_
import com.likeminds.chatinternalsdk.db.models.ChatroomRO
import io.realm.Realm
import io.realm.RealmResults

interface NotificationDB {

    // fetches unread conversation for notification
    suspend fun getUnreadConversationNotification(
        realm: Realm,
        chatroom: _Chatroom_,
        chatroomLastConversation: _Conversation_
    ): RealmResults<ChatroomRO>
}