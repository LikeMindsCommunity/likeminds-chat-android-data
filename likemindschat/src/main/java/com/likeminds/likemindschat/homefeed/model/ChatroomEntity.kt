package com.likeminds.likemindschat.homefeed.model

import com.google.firebase.database.PropertyName

class ChatroomEntity {
    @PropertyName("conversation_id")
    lateinit var conversationId: String

    @PropertyName("chatroom_id")
    lateinit var chatroomId: String
}