package com.likeminds.internalsdk.sync.worker

import android.app.Application
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.likeminds.internalsdk.GroupChatSDK

class FirstTimeChatroomSyncWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val groupChatSDK = GroupChatSDK.getInstance()
    private val api = groupChatSDK.getKettleChatroomSyncApi()
    private val sdkPreferences = SDKPreferences(context as Application)
    private val syncPreferences = SyncPreferences(context as Application)
    private var timeStartedAt: Long = 0L

    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}