package com.likeminds.likemindschat.search.model

import com.likeminds.likemindschat.chatroom.model.Chatroom
import com.likeminds.likemindschat.community.model.Community
import com.likeminds.likemindschat.community.model.Member
import com.likeminds.likemindschat.conversation.model.Attachment

class SearchConversationResponse(
    val conversations: List<SearchConversation>
)

data class SearchConversation(
    val answer: String,
    val attachmentCount: Int,
    val attachments: List<Attachment>,
    val attachmentsUploaded: Boolean,
    val chatroom: Chatroom,
    val community: Community,
    val createdAt: Long,
    val id: Int,
    val isDeleted: Boolean,
    val isEdited: Boolean,
    val lastUpdated: Long,
    val member: Member,
    val state: Int
)
