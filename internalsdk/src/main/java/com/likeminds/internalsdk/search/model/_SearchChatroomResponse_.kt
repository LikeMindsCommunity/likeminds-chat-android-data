package com.likeminds.internalsdk.search.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.chatroom.model._Chatroom_
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.community.model._Member_
import com.likeminds.internalsdk.conversation.model._Attachment_

data class _SearchChatroomResponse_(
    @SerializedName("chatrooms")
    val chatrooms: List<_SearchChatroom_>
)

data class _SearchChatroom_(
    @SerializedName("attachments")
    val attachments: List<_Attachment_>,
    @SerializedName("attending_status")
    val attendingStatus: Boolean,
    @SerializedName("chatroom")
    val chatroom: _Chatroom_,
    @SerializedName("community")
    val community: _Community_,
    @SerializedName("follow_status")
    val followStatus: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_guest")
    val isGuest: Boolean,
    @SerializedName("is_tagged")
    val isTagged: Boolean,
    @SerializedName("member")
    val member: _Member_,
    @SerializedName("mute_status")
    val muteStatus: Boolean,
    @SerializedName("secret_chatroom_left")
    val secretChatroomLeft: Boolean,
    @SerializedName("state")
    val state: Int,
    @SerializedName("updated_at")
    val updatedAt: Long,
    @SerializedName("is_disabled")
    val isDisabled: Boolean?
)
