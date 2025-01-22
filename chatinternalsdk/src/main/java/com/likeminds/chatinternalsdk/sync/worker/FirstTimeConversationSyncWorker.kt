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
import com.likeminds.chatinternalsdk.sync.util.SyncPreferences
import com.likeminds.chatinternalsdk.sync.util.SyncUtil
import com.likeminds.chatinternalsdk.user.util.UserPreferences
import com.likeminds.chatinternalsdk.utils.MAX_RETRY_COUNT
import com.likeminds.chatinternalsdk.utils.measureExecution
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import kotlinx.coroutines.runBlocking

/**
 * Worker to sync conversations for the first time (followed chatrooms, unfollowed chatrooms)
 * Also, to sync conversations of a community or chatroom
 * @param context Context object
 * @param workerParameters Contains meta data and input data of this worker
 */
class FirstTimeConversationSyncWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val chatSDK = LMChatSDK.getInstance()
    private val sdkPreferences = SDKPreferences(context as Application)
    private val syncPreferences = SyncPreferences(context as Application)
    private val userPreferences = UserPreferences(context as Application)
    private val api = chatSDK.getConversationSyncApi()

    private var dataList = ArrayList<_SyncConversationResponse_>()

    private var maxTimestamp = System.currentTimeMillis()
    val chatroomId = workerParameters.inputData.getString(INPUT_DATA_CHATROOM_ID) ?: ""
    private val isBackgroundWorker =
        workerParameters.inputData.getBoolean(INPUT_DATA_BACKGROUND_WORKER, false)

    /*
    * page = 1 -> Shows shimmer.
    * page = 2 -> Loads further data in background.
    * */
    private var page = if (isBackgroundWorker) {
        2
    } else {
        1
    }

    companion object {

        const val NAME = "First Time Conversation Sync Worker"

        const val INPUT_DATA_CHATROOM_ID = "chatroom_id"
        const val INPUT_DATA_BACKGROUND_WORKER = "background_worker"
    }

    override fun doWork(): Result {
        return measureExecution("$NAME, chatroom id: $chatroomId, isBackgroundWorker: $isBackgroundWorker") {
            val result = runBlocking {
                getConversations()
            }
            return@measureExecution result
        }
    }

    /**
     * Fetch conversations, save fetched conversations and update last timestamp
     * @return Success or Failure
     */
    private suspend fun getConversations(): Result {
        if (chatroomId.isEmpty()) return Result.failure()
        val queries = HashMap<String, Any>()
        // Set query parameters for request
        queries[SyncUtil.CHATROOM_ID_KEY] = chatroomId
        queries[SyncUtil.PAGE_KEY] = page
        queries[SyncUtil.PAGE_SIZE_KEY] = SyncUtil.CONVERSATION_PAGE_SIZE
        queries[SyncUtil.MIN_TIMESTAMP_KEY] = 0

        /*
        * For blocker worker -> Current timestamp is used as Max timestamp and stored in prefs
        * For background worker -> Timestamp is fetched from prefs and is used as Max timestamp
        * */
        if (isBackgroundWorker) {
            queries[SyncUtil.MAX_TIMESTAMP_KEY] = syncPreferences.getTimestampForSyncConversation()
        } else {
            queries[SyncUtil.MAX_TIMESTAMP_KEY] = maxTimestamp
            syncPreferences.setTimestampForSyncConversation(maxTimestamp)
        }

        var data: _SyncConversationResponse_? = null
        when (val response = api.syncConversations(queries)) {
            is NetworkResponse.Error -> {
                // The api call failed with some error, retry again or return failure according to the condition.
                Log.e(SyncUtil.TAG, "first time conversation failed: ${response.body.errorMessage}")
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
                /*
                * The response contains no more data.
                * Stores loaded conversations to DB.
                * */
                SyncUtil.saveConversationResponses(chatroomId, communityId, loggedInUUID, dataList)
                ChatDBUtil.updateIsConversationStoreForChatroom(chatroomId, true)
                ChatDBUtil.updateChatroomMinTimestamp(chatroomId, System.currentTimeMillis())
                Result.success()
            }

            else -> {
                if (!isBackgroundWorker) {
                    // First page conversations are stored in DB directly.
                    dataList.add(data)
                    SyncUtil.saveConversationResponses(
                        chatroomId,
                        communityId,
                        loggedInUUID,
                        dataList
                    )
                    ChatDBUtil.updateIsConversationStoreForChatroom(chatroomId, true)
                    ChatDBUtil.updateChatroomMinTimestamp(chatroomId, System.currentTimeMillis())
                    Result.success()
                } else {
                    /*
                    * Further conversations are stored in chunks of 5 pages.
                    * Each page of conversation is added in dataList.
                    * This dataList is stored in DB once 5 page of conversations are loaded or empty response is found.
                    * */
                    if (page % 5 != 1) {
                        dataList.add(data)
                        page++
                        getConversations()
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
                        getConversations()
                    }
                }
            }
        }
    }
}