package com.likeminds.likemindschat.community.model

data class GetAIChatbotsResponse(
    val page: Int,
    val totalPages: Int,
    val totalChatbots: Int,
    val users: List<Member>
)