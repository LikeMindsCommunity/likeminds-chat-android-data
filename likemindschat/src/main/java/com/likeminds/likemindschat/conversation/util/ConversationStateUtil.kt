package com.likeminds.likemindschat.conversation.util

import com.likeminds.likemindschat.conversation.model.ConversationState

object ConversationStateUtil {

    // returns true if state is NORMAL or POLL
    fun isNormalOrPollConversation(state: ConversationState): Boolean {
        return state == ConversationState.NORMAL || state == ConversationState.POLL
    }

    // returns true if state is POLL
    fun isPoll(state: ConversationState): Boolean {
        return state == ConversationState.POLL
    }

    // returns int value for the ConversationState
    fun ConversationState.getStateValue(): Int {
        return value
    }

    // returns [ConversationState] for the int value
    fun Int.getConversationState(): ConversationState {
        return when (this) {
            ConversationState.NORMAL.value -> ConversationState.NORMAL
            ConversationState.FIRST_CONVERSATION.value -> ConversationState.FIRST_CONVERSATION
            ConversationState.MEMBER_JOINED_OPEN_CHATROOM.value -> ConversationState.MEMBER_JOINED_OPEN_CHATROOM
            ConversationState.MEMBER_LEFT_OPEN_CHATROOM.value -> ConversationState.MEMBER_LEFT_OPEN_CHATROOM
            ConversationState.COMMUNITY_PURPOSE_EDITED.value -> ConversationState.COMMUNITY_PURPOSE_EDITED
            ConversationState.GUEST_USER_FOLLOWED.value -> ConversationState.GUEST_USER_FOLLOWED
            ConversationState.MEMBER_ADDED_TO_CHATROOM.value -> ConversationState.MEMBER_ADDED_TO_CHATROOM
            ConversationState.MEMBER_LEFT_SECRET_CHATROOM.value -> ConversationState.MEMBER_LEFT_SECRET_CHATROOM
            ConversationState.MEMBER_REMOVED_FROM_CHATROOM.value -> ConversationState.MEMBER_REMOVED_FROM_CHATROOM
            ConversationState.POLL.value -> ConversationState.POLL
            ConversationState.ALL_MEMBERS_ADDED.value -> ConversationState.ALL_MEMBERS_ADDED
            ConversationState.TOPIC_CHANGED.value -> ConversationState.TOPIC_CHANGED
            ConversationState.DM_MEMBER_REMOVED_LEFT.value -> ConversationState.DM_MEMBER_REMOVED_LEFT
            ConversationState.DM_CM_BECOMES_MEMBER_DISABLE.value -> ConversationState.DM_CM_BECOMES_MEMBER_DISABLE
            ConversationState.DM_MEMBER_BECOME_CM.value -> ConversationState.DM_MEMBER_BECOME_CM
            ConversationState.DM_CM_BECOMES_MEMBER_ENABLE.value -> ConversationState.DM_CM_BECOMES_MEMBER_ENABLE
            ConversationState.DM_MEMBER_BECOMES_CM_ENABLE.value -> ConversationState.DM_MEMBER_BECOMES_CM_ENABLE
            ConversationState.DM_REQUEST_REJECTED.value -> ConversationState.DM_REQUEST_REJECTED
            ConversationState.DM_REQUEST_ACCEPTED.value -> ConversationState.DM_REQUEST_ACCEPTED
            else -> ConversationState.NORMAL
        }
    }
}