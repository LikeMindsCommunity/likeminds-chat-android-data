package com.likeminds.likemindschat.dm.model

enum class DMRequestFrom(val value: String) {
    CHATROOM("chatroom"),
    DM_FEED("dm_feed_v2"),
    MEMBER_PROFILE("member_profile"),
    GROUP_CHANNEL("group_channel")
}