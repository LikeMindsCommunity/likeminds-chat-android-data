package com.likeminds.chatinternalsdk.sync.worker

import android.app.Application
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.db.models.*
import com.likeminds.chatinternalsdk.db.util.DbKey
import com.likeminds.chatinternalsdk.db.util.toRealmList
import com.likeminds.chatinternalsdk.sync.SyncSDK
import com.likeminds.chatinternalsdk.sync.SyncType.Companion.SYNC_CHATROOM
import com.likeminds.chatinternalsdk.sync.SyncType.Companion.SYNC_FIRST_TIME_HOME_FEED
import com.likeminds.chatinternalsdk.sync.SyncType.Companion.SYNC_REOPEN_HOME_FEED
import com.likeminds.chatinternalsdk.user.util.UserPreferences
import com.likeminds.chatinternalsdk.utils.measureExecution
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
            ChatDBUtil.write(realm) { realmInstance ->
                //Add relationship for the conversation reply objects
                realmInstance.where(ConversationRO::class.java)
                    .isNotNull(DbKey.REPLY_CONVERSATION_ID)
                    .isNull(DbKey.REPLY_CONVERSATION)
                    .findAll()
                    .forEach { conversation ->
                        conversation.replyConversation = ChatDBUtil.getConversation(
                            realmInstance,
                            conversation.replyConversationId
                        )
                    }

                when {
                    syncType == SYNC_FIRST_TIME_HOME_FEED || syncType == SYNC_REOPEN_HOME_FEED -> {
                        //Add inverse relationships to communities
                        val communities = realmInstance.where(CommunityRO::class.java)
                            .equalTo(DbKey.RELATIONSHIP_NEEDED, true)
                            .findAll()
                        communities.forEach { communityRO ->
                            //Add inverse relationships for chatrooms
                            communityRO.chatrooms = ChatDBUtil.getChatrooms(
                                realmInstance,
                                communityRO.id
                            ).toRealmList()

                            //Add inverse relationships for conversations
                            communityRO.conversations = ChatDBUtil.getCommunityConversations(
                                realmInstance,
                                communityRO.id
                            ).toRealmList()
                        }

                        val chatrooms = realmInstance.where(ChatroomRO::class.java)
                            .equalTo(DbKey.RELATIONSHIP_NEEDED, true)
                            .beginGroup()
                            .notEqualTo(DbKey.IS_DRAFT, true)
                            .or()
                            .isNull(DbKey.IS_DRAFT)
                            .endGroup()
                            .findAll()
                        chatrooms.forEach { chatroomRO ->
                            val conversations = ChatDBUtil.getChatroomConversations(
                                realmInstance,
                                chatroomRO.id
                            )
                            ChatDBUtil.updateRelationshipsOfChatroom(
                                chatroomRO,
                                conversations,
                                userPreferences.getClientUUID()
                            )
                        }

                        if (isFirstTime) {
                            val appConfigRO = ChatDBUtil.getAppConfig(realmInstance)
                            appConfigRO?.isChatroomsSynced = true
                            appConfigRO?.isCommunitiesSynced = true
                            appConfigRO?.isConversationsSynced = true
                        }
                    }

                    syncType == SYNC_CHATROOM && chatroomId.isNotEmpty() -> {

                        val chatroomRO = ChatDBUtil.getChatroom(realmInstance, chatroomId)
                        if (chatroomRO != null) {
                            val communityRO = ChatDBUtil.getCommunity(
                                realmInstance,
                                chatroomRO.communityId
                            )
                            if (communityRO != null) {
                                //Add inverse relationships for the chatroom if it is not already added
                                if (chatroomRO.getCommunity() == null) {
                                    communityRO.chatrooms = ChatDBUtil.getChatrooms(
                                        realmInstance,
                                        chatroomRO.communityId
                                    ).toRealmList()
                                }
                                //Add inverse relationships for conversations of this community
                                communityRO.conversations = ChatDBUtil.getCommunityConversations(
                                    realmInstance,
                                    chatroomRO.communityId
                                ).toRealmList()
                            }

                            val conversations = ChatDBUtil.getChatroomConversations(
                                realmInstance,
                                chatroomId
                            )

                            // filters all the conversations with duplicate temporary ids
                            val duplicateTempIdConversations =
                                conversations.groupBy { it.temporaryId }
                                    .filter { it.value.size > 1 }

                            // loops through each temporary ids which are duplicate and delete the one with id also equal to temporary id
                            duplicateTempIdConversations.keys.forEach { tempId ->
                                conversations.where()
                                    .equalTo(DbKey.TEMPORARY_ID, tempId)
                                    .equalTo(DbKey.ID, tempId)
                                    .findAll()
                                    .deleteAllFromRealm()
                            }

                            ChatDBUtil.updateRelationshipsOfChatroom(
                                chatroomRO,
                                conversations,
                                userPreferences.getClientUUID()
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