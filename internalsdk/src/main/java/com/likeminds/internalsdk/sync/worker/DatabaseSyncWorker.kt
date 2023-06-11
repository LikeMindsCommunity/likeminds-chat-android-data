package com.likeminds.internalsdk.sync.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.db.util.DbKey.RELATIONSHIP_NEEDED
import com.likeminds.internalsdk.db.util.DbKey.REPLY_CONVERSATION
import com.likeminds.internalsdk.db.util.DbKey.REPLY_CONVERSATION_ID
import com.likeminds.internalsdk.db.util.toRealmList
import com.likeminds.internalsdk.sync.SyncSDK
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_CHATROOM
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_DIRECT_MESSAGE_AND_EVENT
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_FOLLOWED
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_REOPEN_CHATROOM
import com.likeminds.internalsdk.utils.measureExecution
import io.realm.kotlin.Realm
import kotlinx.coroutines.runBlocking

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

    private val syncType = workerParams.inputData.getString(INPUT_DATA_SYNC_TYPE)
    private val communityId = workerParams.inputData.getString(INPUT_DATA_COMMUNITY_ID) ?: ""
    private val chatroomId = workerParams.inputData.getString(INPUT_DATA_CHATROOM_ID) ?: ""
    private val isFirstTime = workerParams.inputData.getBoolean(INPUT_DATA_IS_FIRST_TIME, false)

    override fun doWork(): Result {
        measureExecution("$NAME, First time - $isFirstTime, Sync type - $syncType, Community Id - $communityId, Chatroom Id - $chatroomId") {
            runBlocking {
                val realm = Realm.open(GroupChatSDK.getRealmConfiguration())

                //Add relationship for the conversation reply objects
                val conversationsWhereReplyIsNotPresent = realm.query(
                    ConversationRO::class,
                    "$REPLY_CONVERSATION_ID != $0 AND $REPLY_CONVERSATION == $0",
                    null
                ).find()


                conversationsWhereReplyIsNotPresent.forEach { conversationRO ->
                    realm.write {
                        findLatest(conversationRO)?.replyConversation = ChatDBUtil.getConversation(
                            realm,
                            conversationRO.replyConversationId
                        )
                    }
                }

                when {
                    syncType == SYNC_FOLLOWED || syncType == SYNC_DIRECT_MESSAGE_AND_EVENT || syncType == SYNC_REOPEN_CHATROOM -> {
                        //Add inverse relationships to communities
                        realm.write {
                            val communitiesWhereRelationshipIsNeeded =
                                this.query(CommunityRO::class, "$RELATIONSHIP_NEEDED == true")
                                    .find()

                            communitiesWhereRelationshipIsNeeded.forEach { communityRO ->
                                findLatest(communityRO)?.chatrooms =
                                    ChatDBUtil.getChatrooms(realm, communityRO.id).toRealmList()
//                                findLatest(communityRO)?.apply {
//                                    chatrooms =
//                                        ChatDBUtil.getChatrooms(realm, communityRO.id).toRealmList()
//
//                                    conversations = ChatDBUtil.getCommunityConversations(
//                                        realm,
//                                        communityRO.id
//                                    ).toRealmList()
//                                }
                            }
                        }

                        val chatroomWhereRelationshipIsNeeded =
                            realm.query(ChatroomRO::class, "$RELATIONSHIP_NEEDED == true").find()

                        chatroomWhereRelationshipIsNeeded.forEach { chatroomRO ->
                            val conversations =
                                ChatDBUtil.getChatroomConversations(realm, chatroomRO.id)

                            ChatDBUtil.updateRelationshipsOfChatroom(
                                realm,
                                chatroomRO,
                                conversations
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
                                realm,
                                chatroomRO,
                                conversations
                            )
                        }

                    }
                }
                realm.close()
            }
            if (syncType != null) {
                SyncSDK.clearSyncType(syncType)
            }
        }
        return Result.success()
    }

}