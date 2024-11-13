package com.likeminds.chatinternalsdk.sync.worker

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.sdk.util.SDKPreferences
import com.likeminds.chatinternalsdk.sync.model._SyncConversationResponse_
import com.likeminds.chatinternalsdk.sync.util.SyncUtil
import com.likeminds.chatinternalsdk.user.util.UserPreferences
import com.likeminds.chatinternalsdk.utils.MAX_RETRY_COUNT
import com.likeminds.chatinternalsdk.utils.measureExecution
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import io.realm.Realm
import kotlinx.coroutines.runBlocking

/**
 * Worker to sync conversations when a live conversation is called from Firebase listener
 * @param context Context object
 * @param workerParameters Contains meta data and input data of this worker
 */
class LiveConversationSyncWorker(
    context: Context,
    workerParameters: WorkerParameters,
) : Worker(context, workerParameters) {

    private val chatSDK = LMChatSDK.getInstance()
    private val sdkPreferences = SDKPreferences(context as Application)
    private val userPreferences = UserPreferences(context as Application)
    private val api = chatSDK.getConversationSyncApi()

    private var minTimeStamp = 0L
    private var page = 1
    private var dataList = ArrayList<_SyncConversationResponse_>()

    private val chatroomId = workerParameters.inputData.getString(INPUT_DATA_CHATROOM_ID) ?: ""
    private val conversationId =
        workerParameters.inputData.getString(INPUT_DATA_CONVERSATION_ID) ?: ""

    companion object {
        const val NAME = "Live Conversation Sync Worker"
        const val INPUT_DATA_CHATROOM_ID = "chatroom_id"
        const val INPUT_DATA_CONVERSATION_ID = "conversation_id"
    }

    override fun doWork(): Result {
        return measureExecution("$NAME, params -> chatroom_id: $chatroomId, conversation_id: $conversationId") {
            val realm = Realm.getDefaultInstance()
            val result = runBlocking {
                getConversations(realm)
            }
            realm.close()
            return@measureExecution result
        }
    }

    private suspend fun getConversations(realm: Realm): Result {
        if (chatroomId.isEmpty()) return Result.failure()
        // Set query parameters for request
        val queries = HashMap<String, Any>()
        queries[SyncUtil.CHATROOM_ID_KEY] = chatroomId
        queries[SyncUtil.PAGE_KEY] = page
        queries[SyncUtil.PAGE_SIZE_KEY] = SyncUtil.CONVERSATION_PAGE_SIZE

        val chatroomRO = ChatDBUtil.getChatroom(realm, chatroomId) ?: return Result.failure()
        minTimeStamp = chatroomRO.conversationSyncMinTimestamp ?: 0L

        queries[SyncUtil.MAX_TIMESTAMP_KEY] = System.currentTimeMillis()
        queries[SyncUtil.MIN_TIMESTAMP_KEY] = minTimeStamp

        var data: _SyncConversationResponse_? = null

        Log.d("PUI", "live sync worker with $queries")

        when (val response = api.syncConversations(queries)) {
            is NetworkResponse.Error -> {
                // The api call failed with some error, retry again or return failure according to the condition.
                Log.e(SyncUtil.TAG, "live conversation sync failed: ${response.body.errorMessage}")
                if (runAttemptCount <= MAX_RETRY_COUNT) {
                    Result.retry()
                } else {
                    Result.failure()
                }
            }

            is NetworkResponse.Success -> {
                data = response.body.data
            }
        }

        val communityId = sdkPreferences.getCommunityId() ?: ""
        val loggedInUUID = userPreferences.getClientUUID()
        return when {
            isStopped -> {
                // The worker is stopped or killed by the OS.
                Result.success()
            }

            data == null -> {
                // The api call failed, retry again or return failure according to the condition.
                if (runAttemptCount <= MAX_RETRY_COUNT) {
                    Result.retry()
                } else {
                    Result.failure()
                }
            }

            /**
             *to handle edge-case when there is no new conversation
             * but we get same conversation from api response
             */
            data.conversations.size == 1 -> {
                val conversation = data.conversations.first()
                if (minTimeStamp == conversation.lastUpdated) {
                    Result.success()
                } else {
                    val creatorId = conversation.memberId
                    val member = data.userMeta[creatorId.toString()]
                    val conversationCreatorUUID = member?.sdkClientInfo?.uuid
                    if (!conversationCreatorUUID.equals(userPreferences.getClientUUID())) {
                        dataList.add(data)
                        SyncUtil.saveConversationResponses(
                            chatroomId,
                            communityId,
                            loggedInUUID,
                            dataList
                        )
                    }
                    ChatDBUtil.updateChatroomMinTimestamp(chatroomId, System.currentTimeMillis())
                    Result.success()
                }
            }

            else -> {
                val conversations = data.conversations.toMutableList()

                Log.d("PUI", "live conversation sync size: ${conversations.count()}")

                val selfConversationIndex = conversations.indexOfFirst {
                    it.id == conversationId && it.member?.sdkClientInfo?.uuid == loggedInUUID
                }

                if (selfConversationIndex != -1) {
                    conversations.removeAt(selfConversationIndex)
                }

                val updatedData = data.copy(conversations = conversations)

                dataList.add(updatedData)
                SyncUtil.saveConversationResponses(
                    chatroomId,
                    communityId,
                    loggedInUUID,
                    dataList
                )
                ChatDBUtil.updateChatroomMinTimestamp(chatroomId, System.currentTimeMillis())
                Result.success()
            }
        }
    }
}