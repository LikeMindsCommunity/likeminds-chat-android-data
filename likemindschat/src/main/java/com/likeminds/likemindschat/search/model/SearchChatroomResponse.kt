package com.likeminds.likemindschat.search.model

import com.likeminds.likemindschat.chatroom.model.Chatroom
import com.likeminds.likemindschat.community.model.Community
import com.likeminds.likemindschat.community.model.Member
import com.likeminds.likemindschat.conversation.model.Attachment

data class SearchChatroomResponse(
    val chatrooms: List<SearchChatroom>
)

data class SearchChatroom(
    val attachments: List<Attachment>,
    val attendingStatus: Boolean,
    val chatroom: Chatroom,
    val community: Community,
    val followStatus: Boolean,
    val id: Int,
    val isGuest: Boolean,
    val isTagged: Boolean,
    val member: Member,
    val muteStatus: Boolean,
    val secretChatroomLeft: Boolean,
    val state: Int,
    val updatedAt: Long,
    val isDisabled: Boolean?
)
