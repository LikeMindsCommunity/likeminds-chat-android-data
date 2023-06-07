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
import io.realm.kotlin.Realm
import kotlinx.coroutines.runBlocking

class FirstTimeChatroomSyncWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val groupChatSDK = GroupChatSDK.getInstance()
    private val api = groupChatSDK.getChatroomSyncApi()
    private val sdkPreferences = SDKPreferences(context as Application)
    private val userPreferences = UserPreferences(context as Application)
    private val syncPreferences = SyncPreferences(context as Application)
    private var timeStartedAt: Long = 0L

    private val isBackgroundWorker =
        workerParameters.inputData.getBoolean(IS_BACKGROUND_WORKER, false)

    /*
    * page = 1 -> Shows blocker while loading data.
    * page = 2 -> Loads further data in background.
    * */
    private var page = if (isBackgroundWorker) {
        2
    } else {
        1
    }

    companion object {

        const val NAME = "First time Chatroom Sync Worker"
        const val IS_BACKGROUND_WORKER = "is_background_worker"
    }

    override fun doWork(): Result {
        return measureExecution("$NAME, worker params: isBackgroundSync: $isBackgroundWorker") {
            val realm = Realm.open(GroupChatSDK.getRealmConfiguration())
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
        timeStartedAt = System.currentTimeMillis()
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[SyncUtil.PAGE_KEY] = page
        queries[SyncUtil.PAGE_SIZE_KEY] = SyncUtil.CHATROOM_PAGE_SIZE
        queries[SyncUtil.CHATROOM_TYPES_KEY] = SyncUtil.CHATROOM_TYPE_LIST
        queries[SyncUtil.MIN_TIMESTAMP_KEY] = 0

        /*
        * For blocker worker -> Current timestamp is used as Max timestamp and stored in prefs
        * For background worker -> Timestamp is fetched from prefs and is used as Max timestamp
        * */
        if (isBackgroundWorker) {
            queries[SyncUtil.MAX_TIMESTAMP_KEY] = syncPreferences.getTimestampForSyncChatroom()
        } else {
            val maxTimestamp = System.currentTimeMillis() / 1000
            queries[SyncUtil.MAX_TIMESTAMP_KEY] = maxTimestamp
            syncPreferences.setTimestampForSyncChatroom(maxTimestamp)
        }

        var data: _SyncChatroomResponse_? = null
        when (val response = api.syncChatrooms(queries)) {
            is NetworkResponse.Error -> {
                Log.e(SyncUtil.TAG, "first time chatroom failed: ${response.body.errorMessage}")
                // The api call failed with some error, retry again or return failure according to the condition
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
                // The api call failed, retry again or return failure according to the condition
                if (runAttemptCount <= MAX_RETRY_COUNT) {
                    Result.retry()
                } else {
                    Result.failure()
                }
            }

            data.chatrooms.isEmpty() -> {
                // The response contains no more data.
                Result.success()
            }

            else -> {
                // Dumps the chatroom data to db
                SyncUtil.saveChatroomResponse(
                    realm,
                    sdkPreferences.getCommunityId() ?: "",
                    userPreferences.getLMMemberId(),
                    data
                )
                // Chatroom data for next page is called in background
                if (isBackgroundWorker) {
                    page++
                    getChatrooms(realm)
                }
                Result.success()
            }
        }
    }
}