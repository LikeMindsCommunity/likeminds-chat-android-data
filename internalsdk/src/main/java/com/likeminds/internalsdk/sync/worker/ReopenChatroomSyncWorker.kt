package com.likeminds.internalsdk.sync.worker

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.likeminds.internalsdk.GroupChatSDK
import com.likeminds.internalsdk.sdk.util.SDKPreferences
import com.likeminds.internalsdk.sync.model._SyncChatroomResponse_
import com.likeminds.internalsdk.sync.util.SyncPreferences
import com.likeminds.internalsdk.sync.util.SyncUtil
import com.likeminds.internalsdk.user.util.UserPreferences
import com.likeminds.internalsdk.utils.MAX_RETRY_COUNT
import com.likeminds.internalsdk.utils.measureExecution
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import io.realm.Realm
import kotlinx.coroutines.runBlocking

/**
 * Worker to sync home feed chatrooms when the app is reopened and DB contains first sync data
 * @param context Context object
 * @param workerParams Contains meta data and input data of this worker
 */
class ReopenChatroomSyncWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val collabmatesSdk = GroupChatSDK.getInstance()
    private val api = collabmatesSdk.getChatroomSyncApi()
    private val sdkPreferences = SDKPreferences(context as Application)
    private val userPreferences = UserPreferences(context as Application)
    private val syncPreferences = SyncPreferences(context as Application)
    private val maxTimestamp = System.currentTimeMillis() / 1000
    private val minTimestamp = syncPreferences.getTimestampForSyncChatroom()

    private var page = 1

    companion object {

        const val NAME = "Reopen Chatroom Sync Worker"
    }

    override fun doWork(): Result {
        return measureExecution(NAME) {
            val realm = Realm.getDefaultInstance()
            val result = runBlocking {
                getChatrooms(realm)
            }
            realm.close()
            return@measureExecution result
        }
    }

    /**
     * Fetches all chatrooms
     */
    private suspend fun getChatrooms(realm: Realm): Result {
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[SyncUtil.PAGE_KEY] = page
        queries[SyncUtil.PAGE_SIZE_KEY] = SyncUtil.CHATROOM_PAGE_SIZE
        queries[SyncUtil.MIN_TIMESTAMP_KEY] = minTimestamp
        queries[SyncUtil.MAX_TIMESTAMP_KEY] = maxTimestamp
        queries[SyncUtil.CHATROOM_TYPES_KEY] = SyncUtil.CHATROOM_TYPE_LIST

        var data: _SyncChatroomResponse_? = null
        when (val response = api.syncChatrooms(queries)) {
            is NetworkResponse.Error -> {
                // The api call failed with some error, retry again or return failure according to the condition
                Log.e(SyncUtil.TAG, "reopen chatroom failed: ${response.body.errorMessage}")
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
                syncPreferences.setTimestampForSyncChatroom(maxTimestamp)
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
                getChatrooms(realm)
            }
        }
    }
}