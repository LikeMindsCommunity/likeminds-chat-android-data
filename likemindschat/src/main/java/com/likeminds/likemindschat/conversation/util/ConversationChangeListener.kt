package com.likeminds.likemindschat.conversation.util

import com.likeminds.likemindschat.conversation.model.Conversation

interface ConversationChangeListener {

    fun getPostedConversations(conversations: List<Conversation>?)
    fun getChangedConversations(conversations: List<Conversation>?)
    fun getNewConversations(conversations: List<Conversation>?)
}