package com.likeminds.likemindschat.conversation.model

import com.likeminds.likemindschat.chatroom.model.ConversationEntity

sealed class LiveConversationResponse {
    data class ChildAdded(val response: ConversationEntity?) : LiveConversationResponse()
    data class ChildChanged(val response: ConversationEntity?) : LiveConversationResponse()
    data class ChildRemoved(val response: ConversationEntity?) : LiveConversationResponse()
    data class ChildMoved(val response: ConversationEntity?) : LiveConversationResponse()
    data class OnCancelled(val errorMessage: String) : LiveConversationResponse()
}