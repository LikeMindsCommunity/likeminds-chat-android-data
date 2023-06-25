package com.likeminds.internalsdk.conversation.db

import com.likeminds.internalsdk.conversation.ConversationReceiver
import com.likeminds.internalsdk.conversation.model._Conversation_
import com.likeminds.internalsdk.conversation.model._LinkOGTags_
import com.likeminds.internalsdk.db.models.ConversationRO
import com.likeminds.internalsdk.poll.model._Poll_
import javax.inject.Inject

class ConversationDbImpl @Inject constructor(
    private val conversationReceiver: ConversationReceiver
) : ConversationDB {

    override fun getConversation(conversationId: String): ConversationRO? {
        return conversationReceiver.getConversation(conversationId)
    }

    override fun saveTemporaryConversationAsync(conversation: _Conversation_) {
        conversationReceiver.saveTemporaryConversationAsync(conversation)
    }

    override fun savePostedConversationAsync(
        conversation: _Conversation_,
        isFromNotification: Boolean
    ) {
        conversationReceiver.savePostedConversationAsync(conversation, isFromNotification)
    }

    override fun updateEditedConversation(
        conversationId: String,
        conversationText: String,
        linkOgTags: _LinkOGTags_?
    ) {
        conversationReceiver.updateEditedConversation(conversationId, conversationText, linkOgTags)
    }

    override fun updateConversationSubmitPoll(conversationId: String, allPollItems: List<_Poll_>) {
        conversationReceiver.updateConversationSubmitPoll(conversationId, allPollItems)
    }

    override fun updatePollConversationAddItem(conversationId: String, newPollItem: _Poll_) {
        conversationReceiver.updatePollConversationAddItem(conversationId, newPollItem)
    }

    override fun updateDeletedConversations(conversationsId: List<String>) {
        conversationReceiver.updateDeletedConversations(conversationsId)
    }

    override fun updateConversationReaction(
        reaction: String,
        conversationId: String
    ) {
        conversationReceiver.updateConversationReaction(reaction, conversationId)
    }

    override fun removeConversationReaction(conversationId: String) {
        conversationReceiver.removeConversationReaction(conversationId)
    }
}