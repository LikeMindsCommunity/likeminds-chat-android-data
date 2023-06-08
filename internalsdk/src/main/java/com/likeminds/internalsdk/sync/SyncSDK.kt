package com.likeminds.internalsdk.sync

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_FOLLOWED
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

        val appConfigWork = WorkManager.getInstance(context)
            .beginWith(syncAppConfig(firstTime))

        val blockerWork = appConfigWork
            .then(firstTimeSyncChatroom(false))
            .then(syncDatabase(SYNC_FOLLOWED, firstTime))

        var backgroundWork = blockerWork.then(firstTimeSyncChatroom(true))
            .then(syncDatabase(SYNC_FOLLOWED, firstTime))

        if (!firstTime) {
            backgroundWork = backgroundWork.then(cleanDatabase())
        }
        backgroundWork.enqueue()
        return if (firstTime) {
            Pair(blockerWork.workInfosLiveData, null)
        } else {
            Pair(null, appConfigWork.workInfosLiveData)
        }
    }

    /**
     * Worker to sync app meta
     */
    private fun syncAppConfig(isFirstTime: Boolean): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<AppConfigWorker>()
            .setInputData(
                workDataOf(
                    AppConfigWorker.INPUT_DATA_IS_FIRST_TIME to isFirstTime
                )
            )
            .setConstraints(networkConstraint)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .addTag(AppConfigWorker.NAME)
            .build()
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