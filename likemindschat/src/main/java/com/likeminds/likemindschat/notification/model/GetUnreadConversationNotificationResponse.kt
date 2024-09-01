package com.likeminds.likemindschat.notification.model

import com.likeminds.likemindschat.conversation.model.Attachment

data class GetConversationNotificationUnreadResponse(
    val unreadConversation: List<ChatroomNotificationData>
)

data class ChatroomNotificationData(
    val communityName: String,
    val chatroomName: String,
    val chatroomTitle: String,
    val chatroomUserName: String,
    val chatroomUserImage: String,
    val chatroomId: String,
    val communityImage: String,
    val communityId: Int,
    val route: String,
    val chatroomUnreadConversationCount: Int,
    val chatroomLastConversation: String?,
    val chatroomLastConversationUserName: String?,
    val chatroomLastConversationUserImage: String?,
    val routeChild: String,
    val chatroomLastConversationUserTimestamp: Long?,
    val attachments: List<Attachment>?,
    val sortKey: String?
)