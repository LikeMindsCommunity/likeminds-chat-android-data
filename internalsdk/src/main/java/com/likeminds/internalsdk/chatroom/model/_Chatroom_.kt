package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName

class _Chatroom_ private constructor(
    @SerializedName("member")
    val member: _Member_,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("answer_text")
    val answerText: String?,
    @SerializedName("state")
    val state: Int,
    @SerializedName("share_url")
    val shareUrl: String?,
    @SerializedName("community_id")
    val communityId: String?,
    @SerializedName("community_name")
    val communityName: String?
) {
}