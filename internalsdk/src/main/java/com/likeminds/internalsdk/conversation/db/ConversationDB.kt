package com.likeminds.internalsdk.conversation.db

import com.likeminds.internalsdk.conversation.model._LinkOGTags_
import com.likeminds.internalsdk.db.models.ConversationRO
import com.likeminds.internalsdk.poll.model._Poll_

interface ConversationDB {

    fun getConversation(conversationId: String): ConversationRO?

    fun updateEditedConversation(
        conversationId: String,
        conversationText: String,
        linkOgTags: _LinkOGTags_?
    )

    fun updateConversationSubmitPoll(conversationId: String, allPollItems: List<_Poll_>)

    fun updatePollConversationAddItem(conversationId: String, newPollItem: _Poll_)

    fun updateDeletedConversations(conversationsId: List<String>)

    fun updateConversationReaction(reaction: String, conversationId: String)

    fun removeConversationReaction(conversationId: String)
}