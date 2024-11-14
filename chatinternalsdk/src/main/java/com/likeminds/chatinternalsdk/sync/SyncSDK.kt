package com.likeminds.chatinternalsdk.sync

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.*
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.sync.SyncType.Companion.SYNC_CHATROOM
import com.likeminds.chatinternalsdk.sync.SyncType.Companion.SYNC_FIRST_TIME_HOME_FEED
import com.likeminds.chatinternalsdk.sync.SyncType.Companion.SYNC_REOPEN_HOME_FEED
import com.likeminds.chatinternalsdk.sync.worker.*
import java.util.concurrent.TimeUnit

object SyncSDK {

    private var ongoingSyncTypes = ArrayList<@SyncType String>()

    //clear the ongoingSyncType array after all the workers are completed
    fun clearSyncType(@SyncType syncType: String) {
        ongoingSyncTypes.remove(syncType)
    }

    //All work manager will run only if internet connection is stable
    private val networkConstraint = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED).build()

    /**
     * Sync steps for
     * 1. Fetch and save page = 1 for chatroom sync
     * 2. Run a database sync worker to make relationships for responses till now
     * 3. Fetch and save page = 2 to empty response for chatroom sync
     * 4. Run a database sync worker to make relationships for responses till now
     *
     * Return: Pair -> first: live data of blockerWork, second: live data of app config worker
     */
    fun startFirstHomeFeedSync(
        context: Context
    ): Pair<LiveData<MutableList<WorkInfo>>?, LiveData<MutableList<WorkInfo>>?>? {
        if (ongoingSyncTypes.contains(SYNC_FIRST_TIME_HOME_FEED)) {
            return null
        }
        ongoingSyncTypes.add(SYNC_FIRST_TIME_HOME_FEED)
        val firstTime = ChatDBUtil.isEmpty()

        val blockerWorker = WorkManager.getInstance(context)
            .beginWith(firstTimeSyncChatroom(false))
            .then(syncDatabase(SYNC_FIRST_TIME_HOME_FEED, firstTime))

        val backgroundWork = blockerWorker.then(firstTimeSyncChatroom(true))
            .then(syncDatabase(SYNC_FIRST_TIME_HOME_FEED, firstTime))

        backgroundWork.enqueue()
        return if (firstTime) {
            Pair(blockerWorker.workInfosLiveData, null)
        } else {
            Pair(null, backgroundWork.workInfosLiveData)
        }
    }

    /**
     * Sync steps for
     * 1. Fetch and save for page = 1 to empty response for reopen chatroom sync workers
     * 2. Run a database worker
     *
     * Return: live data of worker
     */
    fun startReopenSyncForHomeFeed(context: Context): Pair<LiveData<MutableList<WorkInfo>>?, LiveData<MutableList<WorkInfo>>?>? {
        if (ongoingSyncTypes.contains(SYNC_REOPEN_HOME_FEED)) {
            return null
        }

        val firstTime = ChatDBUtil.isEmpty()

        ongoingSyncTypes.add(SYNC_REOPEN_HOME_FEED)
        val worker = WorkManager.getInstance(context)
            .beginWith(reopenSyncChatroom())
            .then(syncDatabase(SYNC_REOPEN_HOME_FEED, firstTime))

        worker.enqueue()

        return Pair(worker.workInfosLiveData, null)
    }

    //return first chatroom sync worker
    private fun firstTimeSyncChatroom(isBackgroundWorker: Boolean): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<FirstTimeChatroomSyncWorker>()
            .setInputData(workDataOf(FirstTimeChatroomSyncWorker.IS_BACKGROUND_WORKER to isBackgroundWorker))
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
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
                WorkRequest.MIN_BACKOFF_MILLIS,
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
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .addTag(DatabaseSyncWorker.NAME)
            .build()
    }

    /**
     * Sync steps for chatrooms
     * 1. Fetch and save for page = 1 for conversation sync
     * 2. Run a database worker to create relationship for responses till now
     * @return Returns LiveData<State> for step 1 and 2 to observe the work states
     */
    fun startFirstTimeSyncForChatroom(
        context: Context,
        chatroomId: String
    ): MediatorLiveData<WorkInfo.State> {
        val blockerWork = WorkManager.getInstance(context)
            .beginWith(firstTimeSyncConversation(chatroomId, false))
            .then(syncDatabase(SYNC_CHATROOM, chatroomId = chatroomId))

        blockerWork.enqueue()
        //MediatorLiveData is a subclass of live data, it will observe the list of worker's live data
        //and post value according to the logic
        return MediatorLiveData<WorkInfo.State>().apply {
            addSource(blockerWork.workInfosLiveData) { workInfoList ->
                //Post the status of only the database sync worker as that is the last worker and
                //we want to observe the completion of the last worker
                val workInfo = workInfoList.firstOrNull {
                    it.tags.contains(DatabaseSyncWorker.NAME)
                }
                if (workInfo != null) {
                    value = workInfo.state
                }
            }
        }
    }

    /**
     * Sync steps for chatrooms
     * 3. Fetch and save for page = 2 till empty response for conversation sync
     * 4. Run a database worker to create relationship for responses till now
     * @return Returns LiveData<State> for step 1 and 2 to observe the work states
     */
    fun startFirstTimeBackgroundSync(
        context: Context,
        chatroomId: String
    ): MediatorLiveData<WorkInfo.State> {
        val work = WorkManager.getInstance(context)
            .beginWith(firstTimeSyncConversation(chatroomId, true))
            .then(syncDatabase(SYNC_CHATROOM, chatroomId = chatroomId))
        work.enqueue()
        return MediatorLiveData<WorkInfo.State>().apply {
            addSource(work.workInfosLiveData) { workInfoList ->
                //Post the status of only the database sync worker as that is the last worker and
                //we want to observe the completion of the last worker
                val workInfo = workInfoList.firstOrNull {
                    it.tags.contains(DatabaseSyncWorker.NAME)
                }
                if (workInfo != null) {
                    value = workInfo.state
                }
            }
        }
    }

    /**
     * Sync steps for guest chatrooms
     * 1. Fetch and save for page = 1 till empty response for conversation sync
     * 2. Run a database worker to create relationship for responses till now
     * @return Returns LiveData<State> to observe the work states
     */
    fun startReopenSyncForChatroom(
        context: Context,
        chatroomId: String,
        conversationId: String? = null
    ): MediatorLiveData<WorkInfo.State> {
        val work = WorkManager.getInstance(context)
            .beginWith(
                reopenSyncConversation(
                    chatroomId,
                    conversationId
                )
            )
            .then(syncDatabase(SYNC_CHATROOM, chatroomId = chatroomId))
        work.enqueue()
        //MediatorLiveData is a subclass of live data, it will observe the list of worker's live data
        //and post value according to the logic
        return MediatorLiveData<WorkInfo.State>().apply {
            addSource(work.workInfosLiveData) { workInfoList ->
                //Post the status of only the database sync worker as that is the last worker and
                //we want to observe the completion of the last worker
                val workInfo = workInfoList.firstOrNull {
                    it.tags.contains(DatabaseSyncWorker.NAME)
                }
                if (workInfo != null) {
                    value = workInfo.state
                }
            }
        }
    }

    /**
     * Sync steps for live conversation
     * 1. Fetch and save for page = 1 if the conversation is not created by logged in user
     * @return Returns LiveData<State> to observe the work states
     */
    fun startLiveSyncConversation(
        context: Context,
        chatroomId: String,
        conversationId: String
    ): MediatorLiveData<WorkInfo.State> {
        val work = WorkManager.getInstance(context)
            .beginWith(liveSyncConversation(chatroomId, conversationId))
            .then(syncDatabase(SYNC_CHATROOM, chatroomId = chatroomId))
        work.enqueue()
        //MediatorLiveData is a subclass of live data, it will observe the list of worker's live data
        //and post value according to the logic
        return MediatorLiveData<WorkInfo.State>().apply {
            addSource(work.workInfosLiveData) { workInfoList ->
                //Post the status of only the database sync worker as that is the last worker and
                //we want to observe the completion of the last worker
                val workInfo = workInfoList.firstOrNull {
                    it.tags.contains(DatabaseSyncWorker.NAME)
                }
                if (workInfo != null) {
                    value = workInfo.state
                }
            }
        }
    }

    //return first conversation sync worker
    private fun firstTimeSyncConversation(
        chatroomId: String,
        isBackgroundWorker: Boolean
    ): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<FirstTimeConversationSyncWorker>()
            .setInputData(
                workDataOf(
                    FirstTimeConversationSyncWorker.INPUT_DATA_CHATROOM_ID to chatroomId,
                    FirstTimeConversationSyncWorker.INPUT_DATA_BACKGROUND_WORKER to isBackgroundWorker
                )
            )
            .setConstraints(networkConstraint)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .addTag(FirstTimeConversationSyncWorker.NAME)
            .build()
    }

    //return reopen conversation sync worker
    private fun reopenSyncConversation(
        chatroomId: String,
        conversationId: String?
    ): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<ReopenConversationSyncWorker>()
            .setInputData(
                workDataOf(
                    ReopenConversationSyncWorker.INPUT_DATA_CHATROOM_ID to chatroomId,
                    ReopenConversationSyncWorker.INPUT_DATA_CONVERSATION_ID to conversationId
                )
            )
            .setConstraints(networkConstraint)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .addTag(ReopenConversationSyncWorker.NAME)
            .build()
    }

    //return live conversation sync worker
    private fun liveSyncConversation(
        chatroomId: String,
        conversationId: String
    ): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<LiveConversationSyncWorker>()
            .setInputData(
                workDataOf(
                    LiveConversationSyncWorker.INPUT_DATA_CHATROOM_ID to chatroomId,
                    LiveConversationSyncWorker.INPUT_DATA_CONVERSATION_ID to conversationId
                )
            )
            .setConstraints(networkConstraint)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .addTag(LiveConversationSyncWorker.NAME)
            .build()
    }
}