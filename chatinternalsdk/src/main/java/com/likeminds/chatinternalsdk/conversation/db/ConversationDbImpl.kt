package com.likeminds.chatinternalsdk.conversation.db

import com.likeminds.chatinternalsdk.conversation.ConversationReceiver
import com.likeminds.chatinternalsdk.conversation.model.*
import com.likeminds.chatinternalsdk.db.models.ConversationRO
import com.likeminds.chatinternalsdk.poll.model._Poll_
import io.realm.Realm
import io.realm.RealmResults
import io.realm.rx.CollectionChange
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConversationDbImpl @Inject constructor(
    private val conversationReceiver: ConversationReceiver
) : ConversationDB {

    override fun getConversation(realm: Realm, conversationId: String): ConversationRO? {
        return conversationReceiver.getConversation(realm, conversationId)
    }

    override fun getConversationsBelow(
        realm: Realm,
        chatroomId: String,
        limit: Int,
        keyId: String?,
        keyTimestamp: Long?
    ): RealmResults<ConversationRO> {
        return conversationReceiver.getConversationsBelow(
            realm,
            chatroomId,
            limit,
            keyId,
            keyTimestamp
        )
    }

    override fun getConversationsAbove(
        realm: Realm,
        chatroomId: String,
        limit: Int,
        keyId: String?,
        keyTimestamp: Long?
    ): RealmResults<ConversationRO> {
        return conversationReceiver.getConversationsAbove(
            realm,
            chatroomId,
            limit,
            keyId,
            keyTimestamp
        )
    }

    override fun getConversationsAboveCount(
        realm: Realm,
        chatroomId: String,
        keyId: String,
        keyTimestamp: Long
    ): Int {
        return conversationReceiver.getConversationsAboveCount(
            realm,
            chatroomId,
            keyId,
            keyTimestamp
        )
    }

    override fun getConversationsBelowCount(
        realm: Realm,
        chatroomId: String,
        keyId: String,
        keyTimestamp: Long
    ): Int {
        return conversationReceiver.getConversationsBelowCount(
            realm,
            chatroomId,
            keyId,
            keyTimestamp
        )
    }

    override fun getTopConversations(
        realm: Realm,
        chatroomId: String,
        limit: Int
    ): RealmResults<ConversationRO> {
        return conversationReceiver.getTopConversations(
            realm,
            chatroomId,
            limit
        )
    }

    override fun getBottomConversations(
        realm: Realm,
        chatroomId: String,
        limit: Int
    ): RealmResults<ConversationRO> {
        return conversationReceiver.getBottomConversations(
            realm,
            chatroomId,
            limit
        )
    }

    override fun observeConversations(
        realm: Realm,
        chatroomId: String
    ): Flow<CollectionChange<RealmResults<ConversationRO>>> {
        return conversationReceiver.observeConversations(realm, chatroomId)
    }

    override fun deleteConversationPermanently(conversationId: String, chatroomId: String) {
        return conversationReceiver.deleteConversationPermanently(conversationId, chatroomId)
    }

    override fun saveTemporaryConversation(conversation: _Conversation_) {
        conversationReceiver.saveTemporaryConversation(conversation)
    }

    override fun updateConversation(conversation: _Conversation_) {
        conversationReceiver.updateConversation(conversation)
    }

    override fun savePostedConversation(savePostedConversationRequest: _SavePostedConversationRequest_) {
        conversationReceiver.savePostedConversation(savePostedConversationRequest)
    }

    override fun isConversationWithinLimit(conversationWithinLimitRequest: _ConversationWithinLimitRequest_): Boolean {
        return conversationReceiver.isConversationWithinLimit(conversationWithinLimitRequest)
    }

    override fun saveNewConversation(
        realm: Realm,
        conversation: _Conversation_
    ) {
        conversationReceiver.saveNewConversation(realm, conversation)
    }

    override fun updateTemporaryConversation(conversationId: String, localSavedEpoch: Long) {
        conversationReceiver.updateTemporaryConversation(conversationId, localSavedEpoch)
    }

    override fun updateEditedConversation(
        conversationId: String,
        conversationText: String,
        linkOgTags: _LinkOGTags_?
    ) {
        conversationReceiver.updateEditedConversation(conversationId, conversationText, linkOgTags)
    }

    override fun updateConversationWorkerUUID(conversationId: String, uuid: String) {
        conversationReceiver.updateConversationWorkerUUID(conversationId, uuid)
    }

    override fun updateConversationSubmitPoll(conversationId: String, allPollItems: List<_Poll_>) {
        conversationReceiver.updateConversationSubmitPoll(conversationId, allPollItems)
    }

    override fun updatePollConversationAddItem(conversationId: String, newPollItem: _Poll_) {
        conversationReceiver.updatePollConversationAddItem(conversationId, newPollItem)
    }

    override fun updateDeletedConversations(
        communityId: String?,
        conversationsId: List<String>
    ) {
        conversationReceiver.updateDeletedConversations(communityId, conversationsId)
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