package com.likeminds.chatinternalsdk.sync.worker

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.sdk.util.SDKPreferences
import com.likeminds.chatinternalsdk.sync.model._SyncChatroomResponse_
import com.likeminds.chatinternalsdk.sync.util.SyncPreferences
import com.likeminds.chatinternalsdk.sync.util.SyncUtil
import com.likeminds.chatinternalsdk.user.util.UserPreferences
import com.likeminds.chatinternalsdk.utils.MAX_RETRY_COUNT
import com.likeminds.chatinternalsdk.utils.measureExecution
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import io.realm.Realm
import kotlinx.coroutines.runBlocking

class ReopenDMChatroomSyncWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private val chatSDK = LMChatSDK.getInstance()
    private val api = chatSDK.getChatroomSyncApi()
    private val sdkPreferences = SDKPreferences(context as Application)
    private val userPreferences = UserPreferences(context as Application)
    private val syncPreferences = SyncPreferences(context as Application)
    private val maxTimestamp = System.currentTimeMillis() / 1000
    private val minTimestamp = syncPreferences.getTimestampForSyncDM()

    private var page = 1

    companion object {
        const val NAME = "Reopen DM Chatroom Sync Worker"
    }

    override fun doWork(): Result {
        return measureExecution(NAME) {
            val realm = Realm.getDefaultInstance()
            val result = runBlocking {
                getDMChatrooms(realm)
            }
            realm.close()
            return@measureExecution result
        }
    }

    private suspend fun getDMChatrooms(realm: Realm): Result {
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[SyncUtil.PAGE_KEY] = page
        queries[SyncUtil.PAGE_SIZE_KEY] = SyncUtil.CHATROOM_PAGE_SIZE
        queries[SyncUtil.MIN_TIMESTAMP_KEY] = minTimestamp
        queries[SyncUtil.MAX_TIMESTAMP_KEY] = maxTimestamp
        queries[SyncUtil.CHATROOM_TYPES_KEY] = SyncUtil.DM_CHATROOMS_TYPE_LIST

        var data: _SyncChatroomResponse_? = null
        when (val response = api.syncChatrooms(queries)) {
            is NetworkResponse.Error -> {
                // The api call failed with some error, retry again or return failure according to the condition
                Log.e(SyncUtil.TAG, "reopen dm chatroom failed: ${response.body.errorMessage}")
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

        return when {
            isStopped -> {
                // The worker is stopped or killed by the OS.
                Result.success()
            }

            data == null -> {
                // The api call failed with some error, retry again or return failure according to the condition
                if (runAttemptCount <= MAX_RETRY_COUNT) {
                    Result.retry()
                } else {
                    Result.failure()
                }
            }

            data.chatrooms.isEmpty() -> {
                // The response contains no more data. Max timestamp is stored for further api calls
                syncPreferences.setTimestampForSyncDM(maxTimestamp)
                Result.success()
            }

            else -> {
                // Dumps the chatroom data to db and calls for further chatroom data
                SyncUtil.saveChatroomResponse(
                    sdkPreferences.getCommunityId() ?: "",
                    userPreferences.getClientUUID(),
                    data
                )
                page++
                getDMChatrooms(realm)
            }
        }
    }
}