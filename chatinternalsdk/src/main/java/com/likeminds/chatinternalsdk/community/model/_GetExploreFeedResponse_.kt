package com.likeminds.chatinternalsdk.community.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.chatroom.model._Chatroom_

data class _GetExploreFeedResponse_(
    @SerializedName("chatrooms")
    val chatrooms: List<_Chatroom_>,
    @SerializedName("pinned_chatrooms_count")
    val pinnedChatroomCount: Int
)