package com.likeminds.chatinternalsdk.sync

import androidx.annotation.StringDef
import com.likeminds.chatinternalsdk.sync.SyncType.Companion.SYNC_CHATROOM
import com.likeminds.chatinternalsdk.sync.SyncType.Companion.SYNC_FIRST_TIME_HOME_FEED
import com.likeminds.chatinternalsdk.sync.SyncType.Companion.SYNC_REOPEN_HOME_FEED

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
@StringDef(
    SYNC_FIRST_TIME_HOME_FEED,
    SYNC_CHATROOM,
    SYNC_REOPEN_HOME_FEED
)
annotation class SyncType {

    companion object {

        const val SYNC_FIRST_TIME_HOME_FEED = "first_time_home_feed"
        const val SYNC_CHATROOM = "chatroom"
        const val SYNC_REOPEN_HOME_FEED = "reopen_home_feed"
    }

}