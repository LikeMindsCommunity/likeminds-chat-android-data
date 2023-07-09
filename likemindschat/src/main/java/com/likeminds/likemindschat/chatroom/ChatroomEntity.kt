package com.likeminds.likemindschat.chatroom

import com.google.firebase.database.PropertyName

class ChatroomEntity {
    @PropertyName("answer_id")
    lateinit var answerId: String
}