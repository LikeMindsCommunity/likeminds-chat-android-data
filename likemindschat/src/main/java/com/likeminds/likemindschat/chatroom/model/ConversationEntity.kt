package com.likeminds.likemindschat.chatroom.model

import com.google.firebase.database.PropertyName

class ConversationEntity {
    @PropertyName("answer_id")
    lateinit var answerId: String
}