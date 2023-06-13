package com.likeminds.internalsdk.sync.worker

import android.app.Application
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.db.util.DbKey
import com.likeminds.internalsdk.db.util.toRealmList
import com.likeminds.internalsdk.sync.SyncSDK
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_CHATROOM
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_DIRECT_MESSAGE_AND_EVENT
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_FOLLOWED
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_REOPEN_CHATROOM
import com.likeminds.internalsdk.user.util.UserPreferences
import com.likeminds.internalsdk.utils.measureExecution
import io.realm.Realm

/**
 * Worker to make cheap relationships between data models. This is a cpu intensive worker.
 * @param context Context object
 * @param workerParams Contains meta data and input data of this worker
 */
class DatabaseSyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    companion object {

        const val NAME = "Database Sync Worker"

        const val INPUT_DATA_SYNC_TYPE = "sync_type"
        const val INPUT_DATA_CHATROOM_ID = "chatroom_id"
        const val INPUT_DATA_COMMUNITY_ID = "community_id"
        const val INPUT_DATA_IS_FIRST_TIME = "is_first_time"
    }

    private val userPreferences = UserPreferences(context as Application)

    private val syncType = workerParams.inputData.getString(INPUT_DATA_SYNC_TYPE)
    private val communityId = workerParams.inputData.getString(INPUT_DATA_COMMUNITY_ID) ?: ""
    private val chatroomId = workerParams.inputData.getString(INPUT_DATA_CHATROOM_ID) ?: ""
    private val isFirstTime = workerParams.inputData.getBoolean(INPUT_DATA_IS_FIRST_TIME, false)

    override fun doWork(): Result {
        measureExecution("$NAME, First time - $isFirstTime, Sync type - $syncType, Community Id - $communityId, Chatroom Id - $chatroomId") {
            val realm = Realm.getDefaultInstance()
            ChatDBUtil.write(realm) { realm ->
                //Add relationship for the conversation reply objects
                realm.where(ConversationRO::class.java)
                    .isNotNull(DbKey.REPLY_CONVERSATION_ID)
                    .isNull(DbKey.REPLY_CONVERSATION)
                    .findAll()
                    .forEach { conversation ->
                        conversation.replyConversation = ChatDBUtil.getConversation(
                            realm,
                            conversation.replyConversationId
                        )
                    }

                when {
                    syncType == SYNC_FOLLOWED || syncType == SYNC_DIRECT_MESSAGE_AND_EVENT || syncType == SYNC_REOPEN_CHATROOM -> {
                        //Add inverse relationships to communities
                        val communities = realm.where(CommunityRO::class.java)
                            .equalTo(DbKey.RELATIONSHIP_NEEDED, true)
                            .findAll()
                        communities.forEach { communityRO ->
                            //Add inverse relationships for chatrooms
                            communityRO.chatrooms = ChatDBUtil.getChatrooms(
                                realm,
                                communityRO.id
                            ).toRealmList()

                            //Add inverse relationships for conversations
                            communityRO.conversations = ChatDBUtil.getCommunityConversations(
                                realm,
                                communityRO.id
                            ).toRealmList()
                        }

                        val chatrooms = realm.where(ChatroomRO::class.java)
                            .equalTo(DbKey.RELATIONSHIP_NEEDED, true)
                            .beginGroup()
                            .notEqualTo(DbKey.IS_DRAFT, true)
                            .or()
                            .isNull(DbKey.IS_DRAFT)
                            .endGroup()
                            .findAll()
                        chatrooms.forEach { chatroomRO ->
                            val conversations = ChatDBUtil.getChatroomConversations(
                                realm,
                                chatroomRO.id
                            )
                            ChatDBUtil.updateRelationshipsOfChatroom(
                                chatroomRO,
                                conversations,
                                userPreferences.getLMMemberId()
                            )
                        }

                        if (isFirstTime) {
                            val appConfigRO = ChatDBUtil.getAppConfig(realm)
                            appConfigRO?.isChatroomsSynced = true
                            appConfigRO?.isCommunitiesSynced = true
                            appConfigRO?.isConversationsSynced = true
                        }

                    }

                    syncType == SYNC_CHATROOM && chatroomId.isEmpty() -> {

                        val chatroomRO = ChatDBUtil.getChatroom(realm, chatroomId)
                        if (chatroomRO != null) {
                            val communityRO = ChatDBUtil.getCommunity(
                                realm,
                                chatroomRO.communityId
                            )
                            if (communityRO != null) {
                                //Add inverse relationships for the chatroom if it is not already added
                                if (chatroomRO.getCommunity() == null) {
                                    communityRO.chatrooms = ChatDBUtil.getChatrooms(
                                        realm,
                                        chatroomRO.communityId
                                    ).toRealmList()
                                }
                                //Add inverse relationships for conversations of this community
                                communityRO.conversations = ChatDBUtil.getCommunityConversations(
                                    realm,
                                    chatroomRO.communityId
                                ).toRealmList()
                            }

                            val conversations = ChatDBUtil.getChatroomConversations(
                                realm,
                                chatroomId
                            )
                            ChatDBUtil.updateRelationshipsOfChatroom(
                                chatroomRO,
                                conversations,
                                userPreferences.getLMMemberId()
                            )
                        }

                    }
                }
            }
            if (syncType != null) {
                SyncSDK.clearSyncType(syncType)
            }
            realm.close()
        }
        return Result.success()
    }

}