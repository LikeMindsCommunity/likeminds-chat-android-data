package com.likeminds.likemindschat.conversation.model

import com.likeminds.likemindschat.chatroom.model.ChatroomFirebaseEntity

sealed class LiveConversationResponse {
    data class ChildAdded(val response: ChatroomFirebaseEntity?) : LiveConversationResponse()
    data class ChildChanged(val response: ChatroomFirebaseEntity?) : LiveConversationResponse()
    data class ChildRemoved(val response: ChatroomFirebaseEntity?) : LiveConversationResponse()
    data class ChildMoved(val response: ChatroomFirebaseEntity?) : LiveConversationResponse()
    data class OnCancelled(val errorMessage: String) : LiveConversationResponse()
}