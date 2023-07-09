package com.likeminds.likemindschat.conversation.model

import com.likeminds.likemindschat.chatroom.ChatroomEntity

sealed class LiveConversationResponse {
    data class ChildAdded(val response: ChatroomEntity?) : LiveConversationResponse()
    data class ChildChanged(val response: ChatroomEntity?) : LiveConversationResponse()
    data class ChildRemoved(val response: ChatroomEntity?) : LiveConversationResponse()
    data class ChildMoved(val response: ChatroomEntity?) : LiveConversationResponse()
    data class OnCancelled(val errorMessage: String) : LiveConversationResponse()
}