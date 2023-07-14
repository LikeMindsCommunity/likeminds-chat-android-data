package com.likeminds.likemindschat.chatroom

import com.google.firebase.database.PropertyName

class ChatroomFirebaseEntity {
    @PropertyName("answer_id")
    lateinit var answerId: String
}