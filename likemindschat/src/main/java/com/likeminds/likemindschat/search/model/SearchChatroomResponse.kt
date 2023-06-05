package com.likeminds.likemindschat.search.model

import com.likeminds.likemindschat.community.model.Community
import com.likeminds.likemindschat.conversation.model.Attachment

data class SearchChatroomResponse(
    val chatrooms: List<SearchChatroom>
)

// todo: add chatroom
data class SearchChatroom(
    val attachments: List<Attachment>,
    val attendingStatus: Boolean,
//    val chatroom: Collabcard,
    val community: Community,
    val followStatus: Boolean,
    val id: Int,
    val isGuest: Boolean,
    val isTagged: Boolean,
    val member: SearchMember,
    val muteStatus: Boolean,
    val secretChatroomLeft: Boolean,
    val state: Int,
    val updatedAt: Long,
    val isDisabled: Boolean?
)
