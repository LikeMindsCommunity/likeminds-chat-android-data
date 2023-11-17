package com.likeminds.internalsdk.chatroom.db

import com.likeminds.internalsdk.chatroom.model._Chatroom_
import com.likeminds.internalsdk.db.models.ChatroomRO
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.rx.CollectionChange

interface ChatroomDB {

    // query to save chatroom in DB
    fun saveChatroom(_chatroom_: _Chatroom_)

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
    fun updateChatroomReaction(reaction: String, chatroomId: String)

    //query to remove chatroom reaction
    fun removeChatroomReaction(chatroomId: String)

    //query to update last seen and draft message of the chatroom
    fun updateLastSeenAndDraft(chatroomId: String, draft: String?)

    //query to update chat request state of chatroom
    fun updateChatRequestState(chatroomId: String, chatRequestState: Int?, chatRequestedById: String?)

    // query to observe DM chatrooms
    fun observeDMChatrooms(realm: Realm): Observable<CollectionChange<RealmResults<ChatroomRO>>>?
}