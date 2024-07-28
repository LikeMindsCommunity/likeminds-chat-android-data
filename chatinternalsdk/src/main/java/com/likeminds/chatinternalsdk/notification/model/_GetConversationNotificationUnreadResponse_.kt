package com.likeminds.chatinternalsdk.notification.model

import com.google.gson.annotations.SerializedName
import com.likeminds.chatinternalsdk.conversation.model._Attachment_

data class _GetConversationNotificationUnreadResponse_(
    @SerializedName("unread_conversation")
    val unreadConversation: List<_ChatroomNotificationData_>
)

data class _ChatroomNotificationData_(
    @SerializedName("community_name")
    val communityName: String,
    @SerializedName("chatroom_name")
    val chatroomName: String,
    @SerializedName("chatroom_title")
    val chatroomTitle: String,
    @SerializedName("chatroom_user_name")
    val chatroomUserName: String,
    @SerializedName("chatroom_user_image")
    val chatroomUserImage: String,
    @SerializedName("chatroom_id")
    val chatroomId: String,
    @SerializedName("community_image")
    val communityImage: String,
    @SerializedName("community_id")
    val communityId: Int,
    @SerializedName("route")
    val route: String,
    @SerializedName("chatroom_unread_conversation_count")
    val chatroomUnreadConversationCount: Int,
    @SerializedName("chatroom_last_conversation")
    val chatroomLastConversation: String?,
    @SerializedName("chatroom_last_conversation_user_name")
    val chatroomLastConversationUserName: String?,
    @SerializedName("chatroom_last_conversation_user_image")
    val chatroomLastConversationUserImage: String?,
    @SerializedName("route_child")
    val routeChild: String,
    @SerializedName("chatroom_last_conversation_timestamp")
    val chatroomLastConversationUserTimestamp: Long?,
    @SerializedName("attachments")
    val attachments: List<_Attachment_>?,
    @SerializedName("sort_key")
    val sortKey: String?
)