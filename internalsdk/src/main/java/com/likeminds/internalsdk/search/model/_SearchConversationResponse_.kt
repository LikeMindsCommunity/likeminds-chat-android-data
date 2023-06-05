package com.likeminds.internalsdk.search.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.conversation.model._Attachment_

data class _SearchConversationResponse_(
    @SerializedName("conversations")
    val conversations: List<_SearchConversation_>
)

// todo: add chatroom
data class _SearchConversation_(
    @SerializedName("answer")
    val answer: String,
    @SerializedName("attachment_count")
    val attachmentCount: Int,
    @SerializedName("attachments")
    val attachments: List<_Attachment_>,
    @SerializedName("attachments_uploaded")
    val attachmentsUploaded: Boolean,
//    @SerializedName("chatroom")
//    val chatroom: Collabcard,
    @SerializedName("community")
    val community: _Community_,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_deleted")
    val isDeleted: Boolean,
    @SerializedName("is_edited")
    val isEdited: Boolean,
    @SerializedName("last_updated")
    val lastUpdated: Long,
    @SerializedName("member")
    val member: _SearchMember_,
    @SerializedName("state")
    val state: Int
)
