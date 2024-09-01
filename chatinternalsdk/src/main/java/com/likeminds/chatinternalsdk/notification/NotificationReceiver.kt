package com.likeminds.chatinternalsdk.notification

import android.util.Log
import com.likeminds.chatinternalsdk.chatroom.model._Chatroom_
import com.likeminds.chatinternalsdk.conversation.model._Conversation_
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.db.ROConverter
import com.likeminds.chatinternalsdk.db.models.ChatroomRO
import com.likeminds.chatinternalsdk.db.util.DbKey
import com.likeminds.chatinternalsdk.sdk.util.SDKPreferences
import io.realm.*
import javax.inject.Inject

class NotificationReceiver @Inject constructor(
    private val sdkPreferences: SDKPreferences
) {
    companion object {
        private const val UNREAD_CHATROOM_LIMIT = 10L
    }

    // fetches latest [UNREAD_CHATROOM_LIMIT] chatrooms from local db with unread conversations
    fun getUnreadConversationNotification(
        realm: Realm,
        chatroom: _Chatroom_,
        chatroomLastConversation: _Conversation_
    ): RealmResults<ChatroomRO> {
        ChatDBUtil.write(realm) { realmWrite ->
            val chatroomRO = ChatDBUtil.getChatroom(realmWrite, chatroom.id)

            val memberRO = ChatDBUtil.getMember(realmWrite, chatroom.communityId, chatroomLastConversation.member?.sdkClientInfo?.uuid)

            if (chatroomRO == null) {
                //todo:
                // chatroom doesn't exist so insert it first
            } else {
                val conversationCreatorRO = ROConverter.convertMember(
                    chatroomLastConversation.member,
                    chatroomRO.communityId
                )

                val lastConversationRO = ROConverter.convertLastConversation(
                    realm,
                    chatroomLastConversation,
                    conversationCreatorRO,
                    chatroomLastConversation.attachments,
                    widget = null
                ) ?: return@write

                Log.d(
                    "PUI",
                    "getUnreadConversationNotification: communityId: ${chatroomLastConversation.communityId} chatroomId: ${chatroomLastConversation.chatroomId} createdEpoch: ${chatroomLastConversation.createdEpoch} state: ${chatroomLastConversation.state} answer: ${chatroomLastConversation.answer} id: ${chatroomLastConversation.id}"
                )

//                realmWrite.copyToRealm(lastConversationRO)
//                if (memberRO == null && conversationCreatorRO != null) {
//                    realmWrite.copyToRealmOrUpdate(conversationCreatorRO)
//                }
                Log.d("PUI", "validity: ${chatroomRO.isManaged} ${lastConversationRO.isManaged} ${conversationCreatorRO?.isManaged}")

                chatroomRO.lastConversationRO = realmWrite.copyToRealmOrUpdate(lastConversationRO)
            }
        }

        val communityId = sdkPreferences.getCommunityId()
        var query = realm.where(ChatroomRO::class.java)
        if (communityId != null) {
            query = query.equalTo(DbKey.COMMUNITY_ID, communityId)
        }

        return query.equalTo(DbKey.FOLLOW_STATUS, true) // Filter out unfollowed chatrooms
            .equalTo(DbKey.MUTE_STATUS, false) // Filter out muted chatrooms
            .greaterThan(DbKey.UNSEEN_COUNT, 0)  // Ensure unseenCount is greater than 0
            .sort(DbKey.UPDATED_AT, Sort.DESCENDING) // Sort by updatedAt in descending order
            .limit(UNREAD_CHATROOM_LIMIT)
            .findAll()
    }
}