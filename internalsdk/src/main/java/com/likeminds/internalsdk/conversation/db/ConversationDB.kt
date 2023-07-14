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

    //query to get a single conversation
    fun getConversation(realm: Realm, conversationId: String): ConversationRO?

    //query to get conversations below a particular conversation
    fun getConversationsBelow(
        realm: Realm,
        chatroomId: String,
        limit: Int,
        keyId: String?,
        keyTimestamp: Long?
    ): RealmResults<ConversationRO>

    //query to get conversations above a particular conversation
    fun getConversationsAbove(
        realm: Realm,
        chatroomId: String,
        limit: Int,
        keyId: String?,
        keyTimestamp: Long?
    ): RealmResults<ConversationRO>

    //query to get top most conversations
    fun getTopConversations(
        realm: Realm,
        chatroomId: String,
        limit: Int
    ): RealmResults<ConversationRO>

    //query to get bottom most conversations
    fun getBottomConversations(
        realm: Realm,
        chatroomId: String,
        limit: Int
    ): RealmResults<ConversationRO>

    // query to get count of conversations above
    fun getConversationsAboveCount(
        realm: Realm,
        chatroomId: String,
        keyId: String,
        keyTimestamp: Long
    ): Int

    // query to get count of conversations below
    fun getConversationsBelowCount(
        realm: Realm,
        chatroomId: String,
        keyId: String,
        keyTimestamp: Long
    ): Int

    //query to get observe conversations
    fun observeConversations(
        realm: Realm,
        chatroomId: String
    ): Flow<CollectionChange<RealmResults<ConversationRO>>>

    // query to delete a conversation permanently
    fun deleteConversationPermanently(conversationId: String, chatroomId: String)

    //query to get save temporary conversation
    fun saveTemporaryConversation(conversation: _Conversation_)

    //query to update a conversation in local db
    fun updateConversation(conversation: _Conversation_)

    //query to get save posted conversation
    fun savePostedConversation(
        conversation: _Conversation_,
        isFromNotification: Boolean
    )

    //query to update temporary conversation
    fun updateTemporaryConversation(conversationId: String, localSavedEpoch: Long)

    //query to update edited conversation
    fun updateEditedConversation(
        conversationId: String,
        conversationText: String,
        linkOgTags: _LinkOGTags_?
    )

    //query to update conversation upload worker uuid
    fun updateConversationUploadWorkerUUID(conversationId: String, uuid: String)


    //query to update conversation after submitting poll
    fun updateConversationSubmitPoll(conversationId: String, allPollItems: List<_Poll_>)

    //query to update conversation after adding option
    fun updatePollConversationAddItem(conversationId: String, newPollItem: _Poll_)

    //query to update once user deletes conversation
    fun updateDeletedConversations(conversationsId: List<String>)

    //query to update conversation once user add a reaction
    fun updateConversationReaction(reaction: String, conversationId: String)

    //query to update conversation once user remove a reaction
    fun removeConversationReaction(conversationId: String)
}