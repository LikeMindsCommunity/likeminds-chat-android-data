package com.likeminds.internalsdk.sync

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_FOLLOWED
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_REOPEN_CHATROOM
import com.likeminds.internalsdk.sync.worker.*
import java.util.concurrent.TimeUnit

object SyncSDK {

    private var ongoingSyncTypes = ArrayList<@SyncType String>()

    fun clearSyncType(@SyncType syncType: String) {
        ongoingSyncTypes.remove(syncType)
    }

    //All work manager will run only if internet connection is stable
    private val networkConstraint = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED).build()

    /**
     * Sync steps for
     * 1. Fetch app config, compute new communities if present
     * 2. Fetch and save page = 1 for chatroom sync in blocker shimmer view
     * 3. Run a database sync worker to make relationships for responses till now
     * 4. Remove shimmer
     * 5. Fetch and save page = 2 to empty response for chatroom sync
     * 6. Run a database sync worker to make relationships for responses till now
     * 7. if app is not open for the first time then clean database as well
     *
     * Return: Pair -> first: live data of blockerWork, second: live data of app config worker
     */
    fun startFirstHomeFeedSync(context: Context): Pair<LiveData<MutableList<WorkInfo>>?, LiveData<MutableList<WorkInfo>>?>? {
        if (ongoingSyncTypes.contains(SYNC_FOLLOWED)) {
            return null
        }
        ongoingSyncTypes.add(SYNC_FOLLOWED)
        val firstTime = ChatDBUtil.isEmpty()

        val blockerWorker = WorkManager.getInstance(context)
            .beginWith(firstTimeSyncChatroom(false))
            .then(syncDatabase(SYNC_FOLLOWED, firstTime))

        var backgroundWork = blockerWorker.then(firstTimeSyncChatroom(true))
            .then(syncDatabase(SYNC_FOLLOWED, firstTime))

        if (!firstTime) {
            backgroundWork = backgroundWork.then(cleanDatabase())
        }
        backgroundWork.enqueue()
        return if (firstTime) {
            Pair(blockerWorker.workInfosLiveData, null)
        } else {
            Pair(null, blockerWorker.workInfosLiveData)
        }
    }

    /**
     * Sync steps for
     * 1. Fetch app config, compute new communities if present
     * 2. Fetch and save for page = 1 to empty response for reopen chatroom sync workers
     * 3. Run a database worker
     * 4. if app is not open for the first time then clean database as well
     *
     * Return: live data of worker
     */
    fun startReopenSyncForHomeFeed(context: Context): LiveData<MutableList<WorkInfo>>? {
        if (ongoingSyncTypes.contains(SYNC_REOPEN_CHATROOM)) {
            return null
        }

        val firstTime = ChatDBUtil.isEmpty()

        ongoingSyncTypes.add(SYNC_REOPEN_CHATROOM)
        var worker = WorkManager.getInstance(context)
            .beginWith(reopenSyncChatroom())
            .then(syncDatabase(SYNC_REOPEN_CHATROOM, firstTime))

        if (!firstTime) {
            worker = worker.then(cleanDatabase())
        }

        worker.enqueue()

        return worker.workInfosLiveData
    }

    //return first chatroom sync worker
    private fun firstTimeSyncChatroom(isBackgroundWorker: Boolean): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<FirstTimeChatroomSyncWorker>()
            .setInputData(workDataOf(FirstTimeChatroomSyncWorker.IS_BACKGROUND_WORKER to isBackgroundWorker))
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .setConstraints(networkConstraint)
            .addTag(FirstTimeChatroomSyncWorker.NAME)
            .build()
    }

    //return reopen chatroom sync worker
    private fun reopenSyncChatroom(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<ReopenChatroomSyncWorker>()
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .setConstraints(networkConstraint)
            .addTag(ReopenChatroomSyncWorker.NAME)
            .build()
    }

    /**
     * Worker to sync db
     */
    private fun syncDatabase(
        @SyncType syncType: String,
        isFirstTime: Boolean? = false,
        chatroomId: String? = null,
        communityId: String? = null
    ): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<DatabaseSyncWorker>()
            .setInputData(
                workDataOf(
                    DatabaseSyncWorker.INPUT_DATA_SYNC_TYPE to syncType,
                    DatabaseSyncWorker.INPUT_DATA_COMMUNITY_ID to communityId,
                    DatabaseSyncWorker.INPUT_DATA_CHATROOM_ID to chatroomId,
                    DatabaseSyncWorker.INPUT_DATA_IS_FIRST_TIME to isFirstTime
                )
            )
            .setConstraints(networkConstraint)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .addTag(DatabaseSyncWorker.NAME)
            .build()
    }

    /**
     * Worker to clean up non required database files
     */
    private fun cleanDatabase(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<DatabaseCleanupWorker>()
            .setConstraints(networkConstraint)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .addTag(DatabaseCleanupWorker.NAME)
            .build()
    }
}