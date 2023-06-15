package com.likeminds.internalsdk.sync.util

import android.app.Application
import com.likeminds.internalsdk.utils.sharedpreferences.BasePreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncPreferences @Inject constructor(
    application: Application
) : BasePreferences(SYNC_PREFS, application) {

    companion object {

        const val SYNC_PREFS = "sync_prefs"
        const val TIMESTAMP_FOR_SYNC_CHATROOM = "timestamp_for_sync_chatroom"
    }

    fun setTimestampForSyncChatroom(time: Long) {
        putPreference(TIMESTAMP_FOR_SYNC_CHATROOM, time)
    }

    fun getTimestampForSyncChatroom(): Long {
        return getPreference(TIMESTAMP_FOR_SYNC_CHATROOM, 0L)
    }
}