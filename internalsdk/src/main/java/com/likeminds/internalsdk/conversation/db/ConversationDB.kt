package com.likeminds.internalsdk.conversation.db

import com.likeminds.internalsdk.conversation.model._Conversation_
import com.likeminds.internalsdk.conversation.model._LinkOGTags_
import com.likeminds.internalsdk.db.models.ConversationRO
import com.likeminds.internalsdk.poll.model._Poll_
import io.realm.Realm
import io.realm.RealmResults
import io.realm.rx.CollectionChange
import kotlinx.coroutines.flow.Flow

interface ConversationDB {

    fun getConversation(conversationId: String): ConversationRO?

    fun getConversationsBelow(
        chatroomId: String,
        limit: Int,
        keyId: String?,
        keyTimestamp: Long?
    ): RealmResults<ConversationRO>

    fun getConversationsAbove(
        chatroomId: String,
        limit: Int,
        keyId: String?,
        keyTimestamp: Long?
    ): RealmResults<ConversationRO>

    fun getTopConversations(
        chatroomId: String,
        limit: Int
    ): RealmResults<ConversationRO>

    fun getBottomConversations(
        chatroomId: String,
        limit: Int
    ): RealmResults<ConversationRO>

    fun observeConversations(
        realm: Realm,
        chatroomId: String
    ): Flow<CollectionChange<RealmResults<ConversationRO>>>

    fun saveTemporaryConversation(conversation: _Conversation_)

    fun savePostedConversation(
        conversation: _Conversation_,
        isFromNotification: Boolean
    )

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