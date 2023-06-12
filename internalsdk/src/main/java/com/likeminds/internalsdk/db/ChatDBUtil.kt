package com.likeminds.internalsdk.db

import android.util.Log
import com.likeminds.internalsdk.chatroom.model.TYPE_DIRECT_MESSAGE
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.db.util.DbKey
import com.likeminds.internalsdk.db.util.toRealmList
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

    fun write(realm: Realm, block: (realm: Realm) -> Unit): Boolean {
        ONGOING_WRITE_TRANSACTION.incrementAndGet()
        return try {
            realm.executeTransaction {
                block(it)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Realm Write", "", e)
            false
        } finally {
            ONGOING_WRITE_TRANSACTION.decrementAndGet()
        }
    }

    private fun write(block: (realm: Realm) -> Unit): Boolean {
        ONGOING_WRITE_TRANSACTION.incrementAndGet()
        Realm.getDefaultInstance().use { realm ->
            return try {
                realm.executeTransaction {
                    block(it)
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Realm Write", "", e)
                false
            } finally {
                ONGOING_WRITE_TRANSACTION.decrementAndGet()
            }
        }
    }

    fun isEmpty(): Boolean {
        Realm.getDefaultInstance().use { realm ->
            if (realm.isEmpty) {
                return true
            }
            val query = realm.where(AppConfigRO::class.java).findFirst() ?: return true
            return !query.isConversationsSynced && !query.isChatroomsSynced && !query.isCommunitiesSynced
        }
    }

    fun getAppConfig(realm: Realm): AppConfigRO? {
        return realm.where(AppConfigRO::class.java).findFirst()
    }

    fun getCommunity(realm: Realm, communityId: String?): CommunityRO? {
        if (communityId.isNullOrEmpty()) {
            return null
        }
        return realm.where(CommunityRO::class.java)
            .equalTo(DbKey.ID, communityId)
            .findFirst()
    }

    fun getChatrooms(
        realm: Realm,
        communityId: String
    ): RealmResults<ChatroomRO> {
        return realm.where(ChatroomRO::class.java)
            .equalTo(DbKey.COMMUNITY_ID, communityId)
            .findAll()
    }

    fun getChatroom(realm: Realm, chatroomId: String?): ChatroomRO? {
        if (chatroomId.isNullOrEmpty()) {
            return null
        }
        return realm.where(ChatroomRO::class.java)
            .equalTo(DbKey.ID, chatroomId)
            .findFirst()
    }

    fun getConversation(realm: Realm, id: String?): ConversationRO? {
        if (id.isNullOrEmpty()) {
            return null
        }
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.ID, id)
            .findFirst()
    }

    fun getCommunityConversations(
        realm: Realm,
        communityId: String
    ): RealmResults<ConversationRO> {
        return realm.where(ConversationRO::class.java)
            .equalTo(DbKey.COMMUNITY_ID, communityId)
            .findAll()
    }

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
     */
    fun updateRelationshipsOfChatroom(
        chatroomRO: ChatroomRO,
        conversations: RealmResults<ConversationRO>,
        loggedInMemberId: String
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

//        //chatroom updated at for sorting
        val lastConversationCreatedEpoch = if (chatroomRO.type == TYPE_DIRECT_MESSAGE) {
            chatroomRO.lastConversation?.createdEpoch
        } else {
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
                    .equalTo(DbKey.MEMBER_OBJECT_ID, loggedInMemberId)
                    .endGroup()
                    .endGroup()
                    .sort(DbKey.CREATED_EPOCH, Sort.DESCENDING)
                    .findFirst()
                conversation?.createdEpoch
            }
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
        chatroomRO.totalResponseCount = if (chatroomRO.type == TYPE_DIRECT_MESSAGE) {
            conversations.count()
        } else {
            conversations.where()
                .equalTo(DbKey.STATE, STATE_NORMAL).or()
                .equalTo(DbKey.STATE, STATE_POLL)
                .count()
                .toInt()
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

    fun getMember(
        realm: Realm,
        communityId: String?,
        memberId: String?
    ): MemberRO? {
        val uid1 = "$memberId#${communityId}"
        val member = getMemberByUid(realm, uid1)
        if (member == null) {
            Log.e("Member not found", uid1)
        }
        return member
    }

    private fun getMemberByUid(realm: Realm, uid: String): MemberRO? {
        return realm.where(MemberRO::class.java)
            .equalTo(DbKey.UID, uid)
            .findFirst()
    }
}