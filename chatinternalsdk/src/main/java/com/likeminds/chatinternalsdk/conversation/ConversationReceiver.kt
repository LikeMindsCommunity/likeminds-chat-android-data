package com.likeminds.chatinternalsdk.conversation

import android.os.Build
import android.util.Log
import com.likeminds.chatinternalsdk.conversation.api.ConversationNetworkApi
import com.likeminds.chatinternalsdk.conversation.model.*
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.db.ROConverter
import com.likeminds.chatinternalsdk.db.models.*
import com.likeminds.chatinternalsdk.db.util.DbKey
import com.likeminds.chatinternalsdk.poll.model._Poll_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import io.realm.*
import io.realm.kotlin.toChangesetFlow
import io.realm.rx.CollectionChange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class ConversationReceiver @Inject constructor(
    private val conversationNetworkApi: ConversationNetworkApi
) {

    /**
     * API Functions
     */

    suspend fun postConversation(
        request: _PostConversationRequest_
    ): NetworkResponse<APIResponse<_PostConversationResponse_>> {
        return conversationNetworkApi.postConversation(request)
    }

    suspend fun editConversation(
        request: _EditConversationRequest_
    ): NetworkResponse<APIResponse<_EditConversationResponse_>> {
        return conversationNetworkApi.editConversation(request)
    }

    suspend fun deleteConversations(
        request: _DeleteConversationsRequest_
    ): NetworkResponse<APIResponse<_DeleteConversationsResponse_>> {
        return conversationNetworkApi.deleteConversations(request)
    }

    suspend fun putReaction(
        request: _PutReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return conversationNetworkApi.putReaction(request)
    }

    suspend fun deleteReaction(
        request: _DeleteReactionRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return conversationNetworkApi.deleteReaction(request)
    }

    /**
     * Db Functions
     */

    fun getConversationsBelow(
        realm: Realm,
        chatroomId: String,
        limit: Int,
        keyId: String?,
        keyTimestamp: Long?
    ): RealmResults<ConversationRO> {
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.CHATROOM_ID, chatroomId)
            .greaterThanOrEqualTo(DbKey.CREATED_EPOCH, keyTimestamp ?: 0L)
            .notEqualTo(DbKey.ID, keyId ?: "")
            .sort(DbKey.CREATED_EPOCH, Sort.ASCENDING, DbKey.ID, Sort.ASCENDING)
            .limit(limit.toLong())
            .findAll()
    }

    fun getConversationsAbove(
        realm: Realm,
        chatroomId: String,
        limit: Int,
        keyId: String?,
        keyTimestamp: Long?
    ): RealmResults<ConversationRO> {
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.CHATROOM_ID, chatroomId)
            .lessThanOrEqualTo(DbKey.CREATED_EPOCH, keyTimestamp ?: 0L)
            .notEqualTo(DbKey.ID, keyId ?: "")
            .sort(DbKey.CREATED_EPOCH, Sort.DESCENDING, DbKey.ID, Sort.DESCENDING)
            .limit(limit.toLong())
            .findAll()
            .where()
            .sort(DbKey.CREATED_EPOCH, Sort.ASCENDING, DbKey.ID, Sort.ASCENDING)
            .findAll()
    }

    fun getConversationsAboveCount(
        realm: Realm,
        chatroomId: String,
        keyId: String,
        keyTimestamp: Long
    ): Int {
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.CHATROOM_ID, chatroomId)
            .lessThanOrEqualTo(DbKey.CREATED_EPOCH, keyTimestamp)
            .notEqualTo(DbKey.ID, keyId)
            .count()
            .toInt()
    }

    fun getConversationsBelowCount(
        realm: Realm,
        chatroomId: String,
        keyId: String,
        keyTimestamp: Long
    ): Int {
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.CHATROOM_ID, chatroomId)
            .greaterThanOrEqualTo(DbKey.CREATED_EPOCH, keyTimestamp)
            .notEqualTo(DbKey.ID, keyId)
            .count()
            .toInt()
    }

    fun getTopConversations(
        realm: Realm,
        chatroomId: String,
        limit: Int
    ): RealmResults<ConversationRO> {
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.CHATROOM_ID, chatroomId)
            .sort(DbKey.CREATED_EPOCH, Sort.ASCENDING, DbKey.ID, Sort.ASCENDING)
            .limit(limit.toLong())
            .findAll()
    }

    fun getBottomConversations(
        realm: Realm,
        chatroomId: String,
        limit: Int
    ): RealmResults<ConversationRO> {
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.CHATROOM_ID, chatroomId)
            .sort(DbKey.CREATED_EPOCH, Sort.DESCENDING, DbKey.ID, Sort.DESCENDING)
            .limit(limit.toLong())
            .findAll()
            .where()
            .sort(DbKey.CREATED_EPOCH, Sort.ASCENDING, DbKey.ID, Sort.ASCENDING)
            .findAll()
    }

    fun observeConversations(
        realm: Realm,
        chatroomId: String
    ): Flow<CollectionChange<RealmResults<ConversationRO>>> {
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.CHATROOM_ID, chatroomId)
            .findAllAsync()
            .toChangesetFlow()
            .filter {
                it.collection.isLoaded && it.changeset != null
                        && it.changeset?.state == OrderedCollectionChangeSet.State.UPDATE
            }
    }

    fun deleteConversationPermanently(conversationId: String, chatroomId: String) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatroomRO ->
                val conversation = ChatDBUtil.getConversation(it, conversationId)
                    ?: return@writeAsync
                //Delete the conversation
                conversation.deleteFromRealm()

                //Update the total response count of this chatroom
                chatroomRO.totalResponseCount -= 1
                chatroomRO.totalAllResponseCount -= 1

                val lastConversation = chatroomRO.conversations.where()
                    .equalTo(DbKey.STATE, STATE_NORMAL)
                    .sort(DbKey.CREATED_EPOCH, Sort.DESCENDING)
                    .findFirst() ?: return@writeAsync
                chatroomRO.lastConversation = lastConversation
                chatroomRO.lastSeenConversation = lastConversation
                chatroomRO.lastSeenConversationId = lastConversation.id
            }
        })
    }

    fun saveTemporaryConversation(conversation: _Conversation_) {
        ChatDBUtil.writeAsync({ realm ->
            val startTime = System.currentTimeMillis()
            //get logged in member
            val userRO = realm.where(UserRO::class.java).findFirst()

            val conversationRO =
                ROConverter.convertConversation(realm, conversation, loggedInMember = userRO)
            if (conversationRO != null) {
                ChatDBUtil.getChatroom(realm, conversationRO.chatroomId)?.let { chatroomRO ->
                    //add the conversation to db
                    chatroomRO.conversations.add(conversationRO)
                    //Make the chatroom followed, if it is not already followed
                    if (chatroomRO.followStatus != true) {
                        chatroomRO.followStatus = true
                    }
                    //Save this conversation as the last conversation
                    if (conversationRO.createdEpoch > (chatroomRO.lastConversationRO?.createdEpoch
                            ?: 0)
                    ) {
                        val lastConversation = chatroomRO.conversations.last()
                        val lastConversationRO =
                            ROConverter.convertConversationToLastConversation(lastConversation)
                                ?: return@writeAsync
                        chatroomRO.lastConversationRO = realm.copyToRealm(lastConversationRO)
                    }
                    if (conversationRO.createdEpoch > (chatroomRO.lastSeenConversation?.createdEpoch
                            ?: 0L)
                    ) {
                        chatroomRO.lastSeenConversation = chatroomRO.conversations.last()
                    }
                    //Update the chatroom timestamp for sorting of chatrooms
                    if ((conversationRO.state == STATE_NORMAL || conversationRO.state == STATE_FOLLOWED || conversationRO.state == STATE_POLL) && conversationRO.createdEpoch > (chatroomRO.updatedAt
                            ?: 0)
                    ) {
                        chatroomRO.updatedAt = conversationRO.createdEpoch
                    }

                    //Update the total response count of this chatroom
                    chatroomRO.totalResponseCount += 1
                    chatroomRO.totalAllResponseCount += 1

                    val endTime = System.currentTimeMillis()
                    Log.d("PUI","""
                        Data Layer Save Temporary Conversation Called
                        time taken = ${endTime - startTime}
                    """.trimIndent())
                }
            }
        })
    }

    fun updateConversation(conversation: _Conversation_) {
        val realm = Realm.getDefaultInstance()
        ChatDBUtil.write(realm) { localRealm ->

            //get logged in member
            val userRO = localRealm.where(UserRO::class.java).findFirst()

            val conversationRO = ROConverter.convertConversation(
                localRealm,
                conversation,
                loggedInMember = userRO
            ) ?: return@write
            localRealm.copyToRealmOrUpdate(conversationRO, ImportFlag.CHECK_SAME_VALUES_BEFORE_SET)
        }
        realm.close()
    }

    fun savePostedConversation(savePostedConversationRequest: _SavePostedConversationRequest_) {
        ChatDBUtil.writeAsync({ realm ->

            //get logged in member
            val userRO = realm.where(UserRO::class.java).findFirst()

            val conversation = savePostedConversationRequest.conversation
            val isFromNotification = savePostedConversationRequest.isFromNotification

            val conversationRO =
                ROConverter.convertConversation(realm, conversation, loggedInMember = userRO)
                    ?: return@writeAsync

            ChatDBUtil.getChatroom(realm, conversation.chatroomId)?.let { chatroomRO ->
                Log.d(
                    "PUI", """
                    -------------------
                    Data Layer Save Posted Conversation Called
                    
                    Request Details
                    isFromNotification: $isFromNotification
                    conversation widget id: ${conversation.widgetId}
                    
                    Chatroom RO Details
                    chatroom id: ${chatroomRO.id}
                    chatroom name: ${chatroomRO.header}
                    chatroom existing conversation count: ${chatroomRO.conversations.count()}
                    
                    Conversation RO Details
                    conversation id: ${conversationRO.id}
                    conversation text: ${conversationRO.answer}
                    conversation temp Id: ${conversationRO.temporaryId}
                    conversation widget id: ${conversationRO.widgetId}
                    conversation widget: ${conversationRO.widgetRO}
                    -------------------
                """.trimIndent()
                )
                //add the conversation to db
                if (chatroomRO.conversations.isEmpty()) {
                    chatroomRO.conversations.add(conversationRO)
                } else {
                    //delete the temporary conversation if present
                    if (!isFromNotification) {
                        chatroomRO.conversations.where()
                            .equalTo(DbKey.ID, conversationRO.temporaryId)
                            .findFirst()
                            ?.deleteFromRealm()
                    }
                    chatroomRO.conversations.add(conversationRO)
                }
                //Save this conversation as the last conversation
                if (conversationRO.createdEpoch > (chatroomRO.lastConversationRO?.createdEpoch
                        ?: 0)
                ) {
                    val lastConversation = chatroomRO.conversations.last(null)
                    val lastConversationRO =
                        ROConverter.convertConversationToLastConversation(lastConversation)
                            ?: return@writeAsync
                    chatroomRO.lastConversationRO = realm.copyToRealm(lastConversationRO)
                }
                //Save this conversation as the last seen conversation
                if (conversationRO.createdEpoch > (chatroomRO.lastSeenConversation?.createdEpoch
                        ?: 0L)
                ) {
                    chatroomRO.lastSeenConversation = chatroomRO.conversations.last()
                    chatroomRO.lastSeenConversationId = chatroomRO.conversations.last()?.id
                }

                if (isFromNotification) {
                    //Make the chatroom followed, if it is not already followed
                    if (chatroomRO.followStatus != true) {
                        chatroomRO.followStatus = true
                    }
                    //Update the chatroom timestamp for sorting of chatrooms
                    if ((conversationRO.state == STATE_NORMAL || conversationRO.state == STATE_FOLLOWED) && conversationRO.createdEpoch > (chatroomRO.updatedAt
                            ?: 0)
                    ) {
                        chatroomRO.updatedAt = conversationRO.createdEpoch
                    }

                    //Update the total response count of this chatroom
                    chatroomRO.totalResponseCount += 1
                    chatroomRO.totalAllResponseCount += 1
                }
            }
        })
    }

    fun saveNewConversation(
        realm: Realm,
        conversation: _Conversation_
    ) {
        ChatDBUtil.write(realm) { realmInstance ->
            //get logged in member
            val userRO = realmInstance.where(UserRO::class.java).findFirst()

            val conversationRO =
                ROConverter.convertConversation(
                    realmInstance,
                    conversation,
                    loggedInMember = userRO
                ) ?: return@write

            ChatDBUtil.getChatroom(realmInstance, conversation.chatroomId)?.let { chatroomRO ->
                if (!chatroomRO.conversations.contains(conversationRO)) {
                    chatroomRO.conversations.add(conversationRO)
                }
                //Make the chatroom followed, if it is not already followed
                if (chatroomRO.followStatus != true) {
                    chatroomRO.followStatus = true
                }

                //Save this conversation as the last conversation
                if (conversationRO.createdEpoch > (chatroomRO.lastConversationRO?.createdEpoch
                        ?: 0)
                ) {
                    val lastConversation = chatroomRO.conversations.last(null)
                    val lastConversationRO =
                        ROConverter.convertConversationToLastConversation(lastConversation)
                            ?: return@write
                    chatroomRO.lastConversationRO = realmInstance.copyToRealm(lastConversationRO)

                }
                if (conversationRO.createdEpoch > (chatroomRO.lastSeenConversation?.createdEpoch
                        ?: 0L)
                ) {
                    chatroomRO.lastSeenConversation = chatroomRO.conversations
                        .last(null)
                }
                //Update the chatroom timestamp for sorting of chatrooms
                if ((conversationRO.state == STATE_NORMAL || conversationRO.state == STATE_FOLLOWED || conversationRO.state == STATE_POLL) && conversationRO.createdEpoch > (chatroomRO.updatedAt
                        ?: 0)
                ) {
                    chatroomRO.updatedAt = conversationRO.createdEpoch
                }
                //Update the total response count of this chatroom
                chatroomRO.totalResponseCount += 1
                chatroomRO.totalAllResponseCount += 1
            }
        }
    }

    fun updateTemporaryConversation(conversationId: String, localSavedEpoch: Long) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getConversation(it, conversationId)?.let { conversation ->
                conversation.localSavedEpoch = localSavedEpoch
            }
        })
    }

    fun getConversation(realm: Realm, conversationId: String): ConversationRO? {
        return ChatDBUtil.getConversation(realm, conversationId)
    }

    fun updateEditedConversation(
        conversationId: String,
        conversationText: String,
        linkOgTags: _LinkOGTags_?
    ) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getConversation(it, conversationId)?.let { conversation ->
                conversation.answer = conversationText
                conversation.isEdited = true
                conversation.link = ROConverter.convertLink(
                    conversation.chatroomId,
                    conversation.communityId,
                    linkOgTags
                )

                val lastConversation = it.where(LastConversationRO::class.java)
                    .equalTo(DbKey.ID, conversationId)
                    .findFirst()

                lastConversation?.answer = conversationText
            }
        })
    }

    fun updateConversationUploadWorkerUUID(conversationId: String, uuid: String) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getConversation(it, conversationId)?.let { conversation ->
                conversation.uploadWorkerUUID = uuid
            }
        })
    }

    fun updateConversationSubmitPoll(conversationId: String, allPollItems: List<_Poll_>) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getConversation(realm, conversationId)?.let { conversation ->
                val containsAnyVote = conversation.polls.count { (it.noVotes ?: 0) > 0 } > 0
                allPollItems.forEachIndexed { index, poll ->
                    val pollFromDb = conversation.polls[index]
                    if (pollFromDb?.isSelected != poll.isSelected
                        || pollFromDb?.noVotes != poll.noVotes
                        || pollFromDb?.percentage != poll.percentage
                    ) {
                        pollFromDb?.apply {
                            isSelected = poll.isSelected
                            noVotes = poll.noVotes
                            percentage = poll.percentage
                        }
                    }
                }

                val singleMemberVotes = allPollItems.count { poll -> poll.noVotes == 1 }
                if (singleMemberVotes >= 1 && !containsAnyVote) {
                    //This means the current user has voted as a first user.
                    conversation.pollAnswerText = "1 member voted on this poll"
                }

                conversation.getChatroom()?.let { chatroomRO ->
                    val currentMillis = System.currentTimeMillis()
                    chatroomRO.followStatus = true
                    chatroomRO.updatedAt = currentMillis
                }
            }
        })
    }

    fun updatePollConversationAddItem(conversationId: String, newPollItem: _Poll_) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getConversation(realm, conversationId)?.let { conversationRO ->
                ROConverter.convertPoll(
                    realm,
                    conversationRO.communityId,
                    newPollItem,
                    newPollItem.member?.sdkClientInfo?.uuid
                )?.let { pollRO ->
                    conversationRO.polls.add(pollRO)
                }
            }
        })
    }

    fun updateDeletedConversations(
        communityId: String?,
        conversationsId: List<String>
    ) {
        ChatDBUtil.writeAsync({ realm ->
            val conversations = realm.where(ConversationRO::class.java)
                .`in`(DbKey.ID, conversationsId.toTypedArray())
                .findAll()

            //get logged in member
            val userRO = realm.where(UserRO::class.java).findFirst()

            val memberRO = ChatDBUtil.getMember(
                realm,
                communityId,
                userRO?.sdkClientInfoRO?.uuid
            )

            conversations.setString(DbKey.DELETED_BY, userRO?.id)
            conversations.forEach {
                it?.deletedByMember = memberRO
            }

            // finds and deletes the conversations from [LastConversationRO]
            val lastConversations = realm.where(LastConversationRO::class.java)
                .`in`(DbKey.ID, conversationsId.toTypedArray())
                .findAll()

            lastConversations.forEach {
                it?.deletedByMember = memberRO
                it?.deletedBy = userRO?.id
            }
        })
    }

    fun updateConversationReaction(reaction: String, conversationId: String) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getConversation(realm, conversationId)
                ?.let { conversationRO ->

                    //get logged in member
                    val userRO = realm.where(UserRO::class.java).findFirst()

                    //Remove member previous reactions if any
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        conversationRO.reactions.removeIf { reaction ->
                            reaction.member?.sdkClientInfoRO?.uuid == userRO?.sdkClientInfoRO?.uuid
                        }
                    } else {
                        val reactionRO = conversationRO.reactions.find { reaction ->
                            reaction.member?.sdkClientInfoRO?.uuid == userRO?.sdkClientInfoRO?.uuid
                        }
                        conversationRO.reactions.remove(reactionRO)
                    }

                    //Add new member reaction
                    val memberObj =
                        ChatDBUtil.getMember(
                            realm,
                            conversationRO.communityId,
                            userRO?.sdkClientInfoRO?.uuid
                        )
                            ?: return@let
                    val messageReaction = ReactionRO.build {
                        this.reaction = reaction
                        member = memberObj
                    }
                    val addIndex = conversationRO.reactions.lastIndex + 1
                    conversationRO.reactions.add(addIndex, messageReaction)
                }
        })
    }

    fun removeConversationReaction(conversationId: String) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getConversation(realm, conversationId)?.let { conversationRO ->

                //get logged in member
                val userRO = realm.where(UserRO::class.java).findFirst()

                //Remove member previous reactions if any
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    conversationRO.reactions.removeIf { reaction ->
                        reaction.member?.sdkClientInfoRO?.uuid == userRO?.sdkClientInfoRO?.uuid
                    }
                } else {
                    val reactionRO = conversationRO.reactions.find { reaction ->
                        reaction.member?.sdkClientInfoRO?.uuid == userRO?.sdkClientInfoRO?.uuid
                    }
                    conversationRO.reactions.remove(reactionRO)
                }
            }
        })
    }
}