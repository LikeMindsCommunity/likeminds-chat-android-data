package com.likeminds.likemindschat.util

object ResponseUtils {

    fun generateRouteForChatroom(communityId: String, communityName: String): String {
        return "route://chatroom_followed_feed?community_id=${communityId}&community_name=${communityName}"
    }

    fun generateRouteChildForChatroom(chatroomId: String): String {
        return "route://collabcard?collabcard_id=${chatroomId}"
    }
}