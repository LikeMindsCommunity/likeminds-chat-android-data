package com.likeminds.likemindschat.chatroom.model

data class ShareChatroomUrlResponse(
    val shareChatroomUrl: ShareChatroomUrl
)

data class ShareChatroomUrl(
    val shareUrl: String?,
    val creatorShareUrl: String?,
    val linkCreatedAt: String?
)