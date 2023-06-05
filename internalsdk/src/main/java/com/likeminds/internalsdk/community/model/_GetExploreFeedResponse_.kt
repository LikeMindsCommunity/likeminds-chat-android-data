package com.likeminds.internalsdk.community.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.chatroom.model._Chatroom_

data class _GetExploreFeedResponse_(
    @SerializedName("chatrooms")
    val chatrooms: List<_Chatroom_>,
    @SerializedName("pinned_chatrooms_count")
    val pinnedChatroomCount: Int
)