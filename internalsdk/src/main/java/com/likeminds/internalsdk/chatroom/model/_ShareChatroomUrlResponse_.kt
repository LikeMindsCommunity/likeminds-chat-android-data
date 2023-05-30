package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

data class _ShareChatroomUrlResponse_(
    @SerializedName("chatroom_share")
    val shareChatroomUrl: _ShareChatroomUrl_
)

data class _ShareChatroomUrl_(
    @SerializedName("share_url")
    val shareUrl: String?,
    @SerializedName("creator_share_url")
    val creatorShareUrl: String?,
    @SerializedName("link_created_at")
    val linkCreatedAt: String?
)