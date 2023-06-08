package com.likeminds.internalsdk.db

import android.util.Log
import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.chatroom.model.TYPE_DIRECT_MESSAGE
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.db.util.DbKey
import com.likeminds.internalsdk.db.util.toRealmList
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.query.Sort
import io.realm.kotlin.types.RealmObject

object ChatDBUtil {

    suspend fun insertOrUpdate(realm: Realm, roObject: RealmObject) {
        realm.write {
            copyToRealm(roObject, updatePolicy = UpdatePolicy.ALL)
        }
    }

    fun isEmpty(): Boolean {
        val realm = Realm.open(GroupChatSDK.getRealmConfiguration())
        val query = getAppConfig(realm) ?: return true
        return !query.isConversationsSynced && !query.isChatroomsSynced && !query.isCommunitiesSynced
    }

    fun getAppConfig(realm: Realm): AppConfigRO? {
        return realm.query(AppConfigRO::class, "${DbKey.ID} == $0", 0).first().find()
    }

    fun getCommunity(realm: Realm, communityId: String?): CommunityRO? {
        if (communityId == null) {
            return null
        }
        return realm.query(CommunityRO::class, "${DbKey.ID} == $0", communityId).first().find()
    }

    fun getChatrooms(
        realm: Realm,
        communityId: String
    ): RealmResults<ChatroomRO> {
        return realm.query(ChatroomRO::class, "${DbKey.COMMUNITY_ID} == $0", communityId).find()
    }

    fun getChatroom(realm: Realm, chatroomId: String?): ChatroomRO? {
        if (chatroomId == null) {
            return null
        }
        return realm.query(ChatroomRO::class, "${DbKey.ID} == $0", chatroomId).first().find()
    }

    fun getConversation(realm: Realm, id: String?): ConversationRO? {
        if (id.isNullOrEmpty()) {
            return null
        }
        return realm.query(ConversationRO::class, "${DbKey.ID} == $0", id).first().find()
    }

    fun getCommunityConversations(
        realm: Realm,
        communityId: String
    ): RealmResults<ConversationRO> {
        return realm.query(ConversationRO::class, "${DbKey.COMMUNITY_ID} == $0", communityId).find()
    }

    fun getChatroomConversations(
        realm: Realm,
        chatroomId: String
    ): RealmResults<ConversationRO> {
        return realm.query(ConversationRO::class, "${DbKey.CHATROOM_ID} == $0", chatroomId).find()
    }

    /**
     * Make sure to pass this inside a write transaction and all the parameters have to be managed object
     */
    suspend fun updateRelationshipsOfChatroom(
        realm: Realm,
        chatroomRO: ChatroomRO,
        conversations: RealmResults<ConversationRO>
    ) {
        realm.write {
            //Add inverse relationships for conversations
            chatroomRO.conversations = conversations.toRealmList()

            //last seen conversation
            if (chatroomRO.lastSeenConversationId != null) {
                val lastSeenConversation = conversations.query(
                    "${DbKey.ID} == $0",
                    chatroomRO.lastSeenConversationId.toString()
                ).first().find()
                if (lastSeenConversation != null) {
                    chatroomRO.lastSeenConversation = lastSeenConversation
                    val conversationsBeforeLastSeen = conversations.query(
                        "${DbKey.LAST_SEEN} == false AND ${DbKey.CREATED_EPOCH} <= $0",
                        lastSeenConversation.createdEpoch
                    ).find()

                    conversationsBeforeLastSeen.forEach { conversationRO ->
                        findLatest(conversationRO)?.lastSeen = true
                    }
                }
            }

            //chatroom topic
            val chatroomTopic = if (chatroomRO.topicId != null) {
                getConversation(realm, chatroomRO.topicId)
            } else {
                null
            }

            chatroomRO.topic = chatroomTopic

            //chatroom updated at for sorting
            val lastConversationCreatedEpoch = if (chatroomRO.type == TYPE_DIRECT_MESSAGE) {
                chatroomRO.lastConversation?.createdEpoch
            } else {
                //if last conversation is present in chatroom
                if (chatroomRO.lastConversationRO != null) {
                    chatroomRO.lastConversationRO?.createdEpoch
                } else {  //else find last conversation from db
                    val conversation =
                        conversations.query("(${DbKey.STATE} == $STATE_NORMAL OR ${DbKey.STATE} == $STATE_POLL OR (${DbKey.STATE} == $STATE_FOLLOWED AND ${DbKey.MEMBER_OBJECT_ID} == $0))")
                            .sort(DbKey.CREATED_EPOCH, Sort.DESCENDING)
                            .first()
                            .find()
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
                conversations.query("${DbKey.STATE} == $STATE_NORMAL OR ${DbKey.STATE} == $STATE_POLL")
                    .find().count()
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

    fun getMemberByUid(realm: Realm, uid: String): MemberRO? {
        return realm.query(MemberRO::class, "${DbKey.UID} == $0", uid).first().find()
    }
}