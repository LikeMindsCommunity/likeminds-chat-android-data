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
 * Worker to sync conversations when the app is reopened and DB contains first sync data
 * @param context Context object
 * @param workerParameters Contains meta data and input data of this worker
 */
class ReopenConversationSyncWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val chatSDK = LMChatSDK.getInstance()
    private val sdkPreferences = SDKPreferences(context as Application)
    private val userPreferences = UserPreferences(context as Application)
    private val api = chatSDK.getConversationSyncApi()

    val chatroomId = workerParameters.inputData.getString(INPUT_DATA_CHATROOM_ID) ?: ""
    val isFromLive = workerParameters.inputData.getBoolean(INPUT_DATA_IS_FROM_LIVE, false)
    val conversationId = workerParameters.inputData.getString(INPUT_DATA_CONVERSATION_ID)

    private var maxTimestamp = System.currentTimeMillis()
    private var minTimeStamp = 0L
    private var page = 1
    private var dataList = ArrayList<_SyncConversationResponse_>()

    companion object {

        const val NAME = "Reopen Conversation Sync Worker"
        const val INPUT_DATA_CHATROOM_ID = "chatroom_id"
        const val INPUT_DATA_IS_FROM_LIVE = "is_from_live"
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

        if (conversationId != null) {
            queries[SyncUtil.CONVERSATION_ID_KEY] = conversationId
        }

        // Set min timestamp equal to the last conversation's timestamp (when page = 1)
        if (page == 1) {
            minTimeStamp = if (conversationId != null) {
                0
            } else {
                val chatroomRO =
                    ChatDBUtil.getChatroom(realm, chatroomId) ?: return Result.failure()

                val lastSyncedAt =
                    if (chatroomRO.conversationSyncMinTimestamp == null && chatroomRO.conversations.isNotEmpty()) {
                        chatroomRO.lastSeenConversation?.lastUpdatedAt ?: 0
                    } else {
                        chatroomRO.conversationSyncMinTimestamp ?: 0
                    }
                lastSyncedAt
            }
        }

        queries[SyncUtil.MAX_TIMESTAMP_KEY] = maxTimestamp
        queries[SyncUtil.MIN_TIMESTAMP_KEY] = minTimeStamp
        var data: _SyncConversationResponse_? = null

        ChatDBUtil.write(realm) { realmWrite ->
            // get the chatroom from DB
            val chatroomRO = ChatDBUtil.getChatroom(realmWrite, chatroomId)

            chatroomRO?.conversationSyncMinTimestamp = System.currentTimeMillis()
        }

        when (val response = api.syncConversations(queries)) {
            is NetworkResponse.Error -> {
                // The api call failed with some error, retry again or return failure according to the condition.
                Log.e(SyncUtil.TAG, "reopen conversation failed: ${response.body.errorMessage}")
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

            data.conversations.isEmpty() -> {
                //TODO: check this out whether to do this or not

                // Iterate through dataList and create new instances with filtered conversations
//                val updatedDataList = if (isFromLive) {
//                    dataList.map { syncConversationResponse ->
//                        // Filter conversations based on the condition
//                        val filteredConversations =
//                            syncConversationResponse.conversations.filter { conversation ->
//                                conversation.member?.sdkClientInfo?.uuid != userPreferences.getClientUUID()
//                            }
//
//                        // Return a new instance with the filtered list of conversations
//                        syncConversationResponse.copy(conversations = filteredConversations)
//                    } as ArrayList
//                } else {
//                    dataList
//                }

                /*
                * The response contains no more data.
                * Stores loaded conversations to DB.
                * */
                SyncUtil.saveConversationResponses(
                    chatroomId,
                    communityId,
                    loggedInUUID,
                    dataList
                )
                ChatDBUtil.updateIsConversationStoreForChatroom(chatroomId, true)
                Result.success()
            }

            /**
             *to handle edge-case when there is no new conversation
             * but we get same conversation from api response
             */
            data.conversations.size == 1 -> {
                //TODO: check this case
                val conversation = data.conversations.first()
                Log.d(
                    "PUI",
                    "getConversations: $minTimeStamp:::${conversation.lastUpdated}:::$conversation"
                )

                val conversationCreatorUUID = conversation.member?.sdkClientInfo?.uuid ?: ""

                if (isFromLive && conversationCreatorUUID == userPreferences.getClientUUID()) {
                    Result.success()
                } else if (minTimeStamp == conversation.lastUpdated) {
                    Result.success()
                } else {
                    dataList.add(data)
                    SyncUtil.saveConversationResponses(
                        chatroomId,
                        communityId,
                        loggedInUUID,
                        dataList
                    )
                    Result.success()
                }
            }

            else -> {
                // Further conversations are loaded.
                if (page % 5 != 1) {
                    dataList.add(data)
                    page++
                    getConversations(realm)
                } else {
                    dataList.add(data)
                    SyncUtil.saveConversationResponses(
                        chatroomId,
                        communityId,
                        loggedInUUID,
                        dataList
                    )
                    dataList.clear()
                    page++
                    getConversations(realm)
                }
            }
        }
    }
}