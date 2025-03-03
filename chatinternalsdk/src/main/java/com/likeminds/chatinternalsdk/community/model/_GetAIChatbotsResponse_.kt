package com.likeminds.chatinternalsdk.community.model

import com.google.gson.annotations.SerializedName

data class _GetAIChatbotsResponse_(
    @SerializedName("page")
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_chatbots")
    val totalChatbots: Int,
    @SerializedName("users")
    val users: List<_Member_>
)