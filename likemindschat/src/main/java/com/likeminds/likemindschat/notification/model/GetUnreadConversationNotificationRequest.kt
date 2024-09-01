package com.likeminds.likemindschat.notification.model

import com.likeminds.likemindschat.chatroom.model.Chatroom
import com.likeminds.likemindschat.conversation.model.Conversation

class GetUnreadConversationNotificationRequest private constructor(
    val chatroom: Chatroom,
    val chatroomLastConversation: Conversation
) {
    class Builder {
        private var chatroom: Chatroom = Chatroom.Builder().build()
        private var chatroomLastConversation: Conversation = Conversation.Builder().build()

        fun chatroom(chatroom: Chatroom) = apply {
            this.chatroom = chatroom
        }

        fun chatroomLastConversation(chatroomLastConversation: Conversation) = apply {
            this.chatroomLastConversation = chatroomLastConversation
        }

        fun build() = GetUnreadConversationNotificationRequest(chatroom, chatroomLastConversation)
    }

    fun toBuilder(): Builder {
        return Builder().chatroom(chatroom)
            .chatroomLastConversation(chatroomLastConversation)
    }
}