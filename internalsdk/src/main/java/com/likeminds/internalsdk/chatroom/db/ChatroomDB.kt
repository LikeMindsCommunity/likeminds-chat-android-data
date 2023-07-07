package com.likeminds.internalsdk.chatroom.db

import com.likeminds.internalsdk.db.models.ChatroomRO
import io.realm.Realm

interface ChatroomDB {

    //query to get chatroom
    fun getChatroom(realm: Realm, chatroomId: String): ChatroomRO?

    //query to update follow status
    fun updateChatroomFollowStatus(chatroomId: String, value: Boolean)

    //query to update mute status
    fun updateChatroomMuteStatus(chatroomId: String, value: Boolean)

    //query to update leave status for secret chatroom
    fun updateSecretChatroomLeaveStatus(chatroomId: String)

    //query to update chatroom title
    fun updateChatroomTitle(chatroomId: String, updatedTitle: String)

    //query to update chatroom topic
    fun updateChatroomTopic(chatroomId: String, topicId: String)

    //query to add chatroom reaction
    fun updateChatroomReaction(reaction: String, chatroomId: String, memberId: String)

    //query to remove chatroom reaction
    fun removeChatroomReaction(chatroomId: String)

}