package com.likeminds.chatinternalsdk.sync.util

import android.app.Application
import com.likeminds.chatinternalsdk.utils.sharedpreferences.BasePreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncPreferences @Inject constructor(
    application: Application
) : BasePreferences(SYNC_PREFS, application) {

    companion object {

        const val SYNC_PREFS = "sync_prefs"
        const val TIMESTAMP_FOR_SYNC_CHATROOM = "timestamp_for_sync_chatroom"
        const val TIMESTAMP_FOR_SYNC_CONVERSATION = "timestamp_for_sync_conversation"
    }

    fun setTimestampForSyncChatroom(time: Long) {
        putPreference(TIMESTAMP_FOR_SYNC_CHATROOM, time)
    }

    fun getTimestampForSyncChatroom(): Long {
        return getPreference(TIMESTAMP_FOR_SYNC_CHATROOM, 0L)
    }

    fun setTimestampForSyncConversation(time: Long) {
        putPreference(TIMESTAMP_FOR_SYNC_CONVERSATION, time)
    }

    fun getTimestampForSyncConversation(): Long {
        return getPreference(TIMESTAMP_FOR_SYNC_CONVERSATION, 0L)
    }
}