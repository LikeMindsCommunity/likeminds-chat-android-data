package com.likeminds.likemindschat.search.model

import com.likeminds.likemindschat.community.model.Community
import com.likeminds.likemindschat.conversation.model.Attachment

class SearchConversationResponse(
    val conversations: List<SearchConversation>
)

// todo: add chatroom
data class SearchConversation(
    val answer: String,
    val attachmentCount: Int,
    val attachments: List<Attachment>,
    val attachmentsUploaded: Boolean,
//    val chatroom: Collabcard,
    val community: Community,
    val createdAt: Long,
    val id: Int,
    val isDeleted: Boolean,
    val isEdited: Boolean,
    val lastUpdated: Long,
    val member: SearchMember,
    val state: Int
)
