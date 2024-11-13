package com.likeminds.chatinternalsdk.db

import android.util.Log
import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.chatroom.model.TYPE_DIRECT_MESSAGE
import com.likeminds.chatinternalsdk.conversation.model.*
import com.likeminds.chatinternalsdk.db.models.*
import com.likeminds.chatinternalsdk.db.util.DbKey
import com.likeminds.chatinternalsdk.db.util.toRealmList
import io.realm.*
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger

object ChatDBUtil {

    private val ONGOING_WRITE_TRANSACTION = AtomicInteger(0)

    /**
     * Always use this function to do any write transactions in DB.
     * @param block The write transaction block which provides a realm instance for further queries
     * @param cb Callback providing a boolean value indicating the success or failure of the transaction
     * @return [Job] Coroutine Job for further operations
     * [Realm.executeTransactionAsync] is not used as coroutine is faster in background processing
     */
    fun writeAsync(block: (realm: Realm) -> Unit, cb: ((iSSuccess: Boolean) -> Unit)? = null): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            val value = write(block)
            cb?.invoke(value)
        }
    }

    /**
     * Use this function where background is already started
     * @param realm: Instance of realm
     * @param block: All the query that need to insert or update in local db
     *
     * @return [Boolean] whether write is successful or not
     **/
    fun write(realm: Realm, block: (realm: Realm) -> Unit): Boolean {
        ONGOING_WRITE_TRANSACTION.incrementAndGet()
        return try {
            realm.executeTransaction {
                block(it)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(LMChatSDK.LOG_TAG, "write error", e)
            false
        } finally {
            ONGOING_WRITE_TRANSACTION.decrementAndGet()
        }
    }

    /**
     * this function is used inside [writeAsync]
     * @param block: All the query that need to insert or update in local db
     *
     * @return [Boolean] whether write is successful or not
     **/
    fun write(block: (realm: Realm) -> Unit): Boolean {
        ONGOING_WRITE_TRANSACTION.incrementAndGet()
        Realm.getDefaultInstance().use { realm ->
            return try {
                realm.executeTransaction {
                    block(it)
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(LMChatSDK.LOG_TAG, "write async error", e)
                false
            } finally {
                ONGOING_WRITE_TRANSACTION.decrementAndGet()
            }
        }
    }

    /**
     * Check whether local db is empty or not
     */
    fun isEmpty(): Boolean {
        Realm.getDefaultInstance().use { realm ->
            if (realm.isEmpty) {
                return true
            }
            val query = realm.where(AppConfigRO::class.java).findFirst() ?: return true
            return !query.isConversationsSynced && !query.isChatroomsSynced && !query.isCommunitiesSynced
        }
    }

    //clear the whole realm db
    fun clearDB() {
        write {
            it.deleteAll()
        }
    }

    /**
     * To fetch the [AppConfigRO] object
     * @return [AppConfigRO]
     */
    fun getAppConfig(realm: Realm): AppConfigRO? {
        return realm.where(AppConfigRO::class.java).findFirst()
    }

    /**
     * To get the [CommunityRO] object as per [communityId]
     *
     * @param realm: Instance of realm
     * @param communityId: Id of the community to be fetched
     *
     * @return [CommunityRO]
     */
    fun getCommunity(realm: Realm, communityId: String?): CommunityRO? {
        if (communityId.isNullOrEmpty()) {
            return null
        }
        return realm.where(CommunityRO::class.java)
            .equalTo(DbKey.ID, communityId)
            .findFirst()
    }

    /**
     * To get the list of all [ChatroomRO] object as per [communityId]
     *
     * @param realm: Instance of realm
     * @param communityId: Id of the community to be fetched
     *
     * @return list of [ChatroomRO]
     */
    fun getChatrooms(
        realm: Realm,
        communityId: String
    ): RealmResults<ChatroomRO> {
        return realm.where(ChatroomRO::class.java)
            .equalTo(DbKey.COMMUNITY_ID, communityId)
            .findAll()
    }

    /**
     * To get a specific [ChatroomRO] as per [chatroomId]
     *
     * @param realm: Instance of realm
     * @param chatroomId: Id of the chatroom to be fetched
     *
     * @return [ChatroomRO]
     */
    fun getChatroom(realm: Realm, chatroomId: String?): ChatroomRO? {
        if (chatroomId.isNullOrEmpty()) {
            return null
        }
        return realm.where(ChatroomRO::class.java)
            .equalTo(DbKey.ID, chatroomId)
            .findFirst()
    }

    /**
     * To get a specific [ConversationRO] as per [conversationId]
     *
     * @param realm: Instance of realm
     * @param conversationId: Id of the conversation to be fetched
     *
     * @return [ConversationRO]
     */
    fun getConversation(realm: Realm, conversationId: String?): ConversationRO? {
        if (conversationId.isNullOrEmpty()) {
            return null
        }
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.ID, conversationId)
            .findFirst()
    }

    /**
     * To get a list of the [ConversationRO] of a community
     *
     * @param realm: Instance of realm
     * @param communityId: Id of the community whose conversations are fetched
     *
     * @return [ConversationRO]
     */
    fun getCommunityConversations(
        realm: Realm,
        communityId: String
    ): RealmResults<ConversationRO> {
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.COMMUNITY_ID, communityId)
            .findAll()
    }

    /**
     * To get a list of the [ConversationRO] of a chatroom
     *
     * @param realm: Instance of realm
     * @param chatroomId: Id of the chatroom whose conversations are fetched
     *
     * @return [ConversationRO]
     */
    fun getChatroomConversations(
        realm: Realm,
        chatroomId: String
    ): RealmResults<ConversationRO> {
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.CHATROOM_ID, chatroomId)
            .findAll()
    }

    /**
     * Make sure to pass this inside a write transaction and all the parameters have to be managed object
     *  @param chatroomRO: chatroom object
     *  @param conversations: list of conversations
     *  @param loggedInUUID: uuid of loggedInMember
     */
    fun updateRelationshipsOfChatroom(
        chatroomRO: ChatroomRO,
        conversations: RealmResults<ConversationRO>,
        loggedInUUID: String
    ) {
        //Add inverse relationships for conversations
        chatroomRO.conversations = conversations.toRealmList()

        //last seen conversation
        if (chatroomRO.lastSeenConversationId != null) {
            val lastSeenConversation = conversations.where()
                .equalTo(
                    DbKey.ID,
                    chatroomRO.lastSeenConversationId.toString()
                )
                .findFirst()
            if (lastSeenConversation != null) {
                chatroomRO.lastSeenConversation = lastSeenConversation
                conversations.where()
                    .equalTo(DbKey.LAST_SEEN, false)
                    .lessThanOrEqualTo(
                        DbKey.CREATED_EPOCH,
                        lastSeenConversation.createdEpoch
                    )
                    .findAll()
                    .setBoolean(DbKey.LAST_SEEN, true)
            }
        }

        //chatroom topic
        val chatRoomTopic = if (chatroomRO.topicId != null) {
            conversations.where()
                .equalTo(DbKey.ID, chatroomRO.topicId)
                .findFirst()
        } else {
            null
        }

        chatroomRO.topic = chatRoomTopic

        //chatroom updated at for sorting
        val lastConversationCreatedEpoch =
            //if last conversation is present in chatroom
            if (chatroomRO.lastConversationRO != null) {
                chatroomRO.lastConversationRO?.createdEpoch
            } else {  //else find last conversation from db
                val conversation = conversations.where()
                    .beginGroup()
                    .equalTo(DbKey.STATE, STATE_NORMAL).or()
                    .equalTo(DbKey.STATE, STATE_POLL).or()
                    .beginGroup()
                    .equalTo(DbKey.STATE, STATE_FOLLOWED)
                    .and()
                    .equalTo(DbKey.MEMBER_OBJECT_UUID, loggedInUUID)
                    .endGroup()
                    .endGroup()
                    .sort(DbKey.CREATED_EPOCH, Sort.DESCENDING)
                    .findFirst()
                conversation?.createdEpoch
            }

        val chatroomUpdatedAt = when {
            lastConversationCreatedEpoch != null -> {
                lastConversationCreatedEpoch
            }

            chatroomRO.dateEpoch != null -> {
                //Multiplying by 1000 as dateEpoch is in seconds
                chatroomRO.dateEpoch!! * 1000
            }

            else -> null
        }
        if (chatroomUpdatedAt != null) {
            chatroomRO.updatedAt = chatroomUpdatedAt
        }

        //total response count
        val totalResponseCount = if (chatroomRO.type == TYPE_DIRECT_MESSAGE) {
            conversations.where()
                .equalTo(DbKey.STATE, STATE_NORMAL)
                .count()
                .toInt()
        } else {
            conversations.where()
                .equalTo(DbKey.STATE, STATE_NORMAL)
                .or()
                .equalTo(DbKey.STATE, STATE_POLL)
                .count()
                .toInt()
        }

        // add the total response count if lastConversationRO is non null
        chatroomRO.totalResponseCount = if (chatroomRO.lastConversationRO != null) {
            totalResponseCount + 1
        } else {
            totalResponseCount
        }

        //if last conversation is present in chatroom add 1 in count
        val count = if (chatroomRO.lastConversationRO != null) {
            conversations.count() + 1
        } else {
            conversations.count()
        }

        chatroomRO.totalAllResponseCount = count

        chatroomRO.relationshipNeeded = false
    }

    /**
     * to get conversation creator
     *
     * @param realm: Instance of realm
     * @param conversation: object of conversation
     *
     * @return [MemberRO]: creator of conversation
     */
    fun getConversationMember(
        realm: Realm,
        conversation: _Conversation_
    ): MemberRO? {
        return getMember(
            realm,
            conversation.communityId,
            conversation.member?.sdkClientInfo?.uuid ?: conversation.memberId
        )
    }

    /**
     * To get a specific [MemberRO] of a community
     *
     * @param realm: Instance of realm
     * @param communityId: Id of the community
     * @param uuid: uuid of the member
     *
     * @return [MemberRO]
     */
    fun getMember(
        realm: Realm,
        communityId: String?,
        uuid: String?
    ): MemberRO? {
        val uid = "$uuid#${communityId}"
        val member = getMemberByUid(realm, uid)
        if (member == null) {
            Log.e(LMChatSDK.LOG_TAG, "Member not found: $uid")
        }
        return member
    }

    private fun getMemberByUid(realm: Realm, uid: String): MemberRO? {
        return realm.where(MemberRO::class.java)
            .equalTo(DbKey.UID, uid)
            .findFirst()
    }

    /**
     * to update chatroom's [isConversationStored]
     *
     * @param chatroomId: id of chatroom to be updated
     * @param isConversationStored: value of [isConversationStored] -> true or false
     */
    fun updateIsConversationStoreForChatroom(
        chatroomId: String,
        isConversationStored: Boolean
    ) {
        write { realm ->
            val chatroomRO = getChatroom(realm, chatroomId)

            chatroomRO?.isConversationStored = isConversationStored
            chatroomRO?.conversationSyncMinTimestamp = System.currentTimeMillis()
        }
    }
}