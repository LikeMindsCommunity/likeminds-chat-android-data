package com.likeminds.internalsdk.sync

import androidx.annotation.StringDef
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_CHATROOM
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_COMMUNITY
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_DIRECT_MESSAGE_AND_EVENT
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_FIRST_TIME
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_FOLLOWED
import com.likeminds.internalsdk.sync.SyncType.Companion.SYNC_REOPEN_CHATROOM

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
@StringDef(
    SYNC_FIRST_TIME,
    SYNC_FOLLOWED,
    SYNC_CHATROOM,
    SYNC_COMMUNITY,
    SYNC_DIRECT_MESSAGE_AND_EVENT,
    SYNC_REOPEN_CHATROOM
)
annotation class SyncType {

    companion object {

        const val SYNC_FIRST_TIME = "first_time"
        const val SYNC_FOLLOWED = "followed"
        const val SYNC_CHATROOM = "chatroom"
        const val SYNC_COMMUNITY = "community"
        const val SYNC_DIRECT_MESSAGE_AND_EVENT = "direct_message_and_event"
        const val SYNC_REOPEN_CHATROOM = "reopen_chatroom"
    }

}