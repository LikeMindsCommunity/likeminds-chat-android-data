package com.likeminds.likemindschat.util

object ResponseUtils {

    // generates the route for chatroom with provided communityName and communityId
    fun generateRouteForChatroom(communityId: String, communityName: String): String {
        return "route://chatroom_followed_feed?community_id=${communityId}&community_name=${communityName}"
    }

    // generates the child route for chatroom with provided chatroomId
    fun generateRouteChildForChatroom(chatroomId: String): String {
        return "route://collabcard?collabcard_id=${chatroomId}"
    }

    // generates the chatroom name with the unseen messages count
    fun generateChatroomNameWithMessagesCount(chatroomHeader: String, unseenCount: Int): String {
        return if (unseenCount == 1) {
            "$chatroomHeader (1 message)"
        } else {
            "$chatroomHeader ($unseenCount messages)"
        }
    }
}