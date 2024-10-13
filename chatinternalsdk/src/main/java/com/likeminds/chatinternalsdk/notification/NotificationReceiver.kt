package com.likeminds.chatinternalsdk.notification

import com.likeminds.chatinternalsdk.chatroom.model._Chatroom_
import com.likeminds.chatinternalsdk.conversation.model._Conversation_
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.db.ROConverter
import com.likeminds.chatinternalsdk.db.models.ChatroomRO
import com.likeminds.chatinternalsdk.db.models.CommunityRO
import com.likeminds.chatinternalsdk.db.util.DbKey
import com.likeminds.chatinternalsdk.db.util.DbKey.LAST_CONVERSATION_CREATED_EPOCH
import com.likeminds.chatinternalsdk.db.util.toRealmList
import com.likeminds.chatinternalsdk.sdk.util.SDKPreferences
import io.realm.*
import javax.inject.Inject

class NotificationReceiver @Inject constructor(
    private val sdkPreferences: SDKPreferences
) {
    companion object {
        private const val UNREAD_CHATROOM_LIMIT = 7L
    }

    // fetches latest [UNREAD_CHATROOM_LIMIT] chatrooms from local db with unread conversations
    fun getUnreadChatrooms(
        realm: Realm,
        chatroom: _Chatroom_,
        chatroomLastConversation: _Conversation_
    ): RealmResults<ChatroomRO> {
        ChatDBUtil.write(realm) { realmWrite ->

            // get the existing chatroom from DB
            var chatroomRO = ChatDBUtil.getChatroom(realmWrite, chatroom.id)
            if (chatroomRO == null) {
                val chatroomCreatorRO = ROConverter.convertMember(
                    chatroom.member,
                    chatroom.communityId ?: ""
                )

                // insert the chatroom in DB if it doesn't exist already
                chatroomRO = ROConverter.convertChatroom(
                    realmWrite,
                    chatroom,
                    chatroomCreatorRO
                )

                if (chatroomRO != null) {
                    chatroomRO = realmWrite.copyToRealmOrUpdate(chatroomRO)
                }
            }

            chatroomRO = chatroomRO ?: return@write

            // create the conversationCreatorRO and add to add it to lastConversationRO
            val conversationCreatorRO = ROConverter.convertMember(
                chatroomLastConversation.member,
                chatroomRO.communityId
            )

            // creates lastConversationRO object
            val lastConversationRO = ROConverter.convertLastConversation(
                realm,
                chatroomLastConversation,
                conversationCreatorRO,
                chatroomLastConversation.attachments,
                widget = null
            ) ?: return@write

            // writes lastConversationRO to DB and updates chatroom's lastConversationRO
            chatroomRO.lastConversationRO = realmWrite.copyToRealmOrUpdate(lastConversationRO)

            //Update the unseen count of this chatroom
            chatroomRO.unseenCount += 1

            //Add inverse relationships to communities
            val communities = realmWrite.where(CommunityRO::class.java)
                .equalTo(DbKey.RELATIONSHIP_NEEDED, true)
                .findAll()
            communities.forEach { communityRO ->
                //Add inverse relationships for chatrooms
                communityRO.chatrooms = ChatDBUtil.getChatrooms(
                    realmWrite,
                    communityRO.id
                ).toRealmList()
            }
        }

        val communityId = sdkPreferences.getCommunityId()
        var query = realm.where(ChatroomRO::class.java)
        if (communityId != null) {
            query = query.equalTo(DbKey.COMMUNITY_ID, communityId)
        }

        return query.equalTo(DbKey.FOLLOW_STATUS, true) // filter out unfollowed chatrooms
            .equalTo(DbKey.MUTE_STATUS, false) // filter out muted chatrooms
            .greaterThan(DbKey.UNSEEN_COUNT, 0)  // Ensure unseen count is greater than 0
            .sort(
                LAST_CONVERSATION_CREATED_EPOCH,
                Sort.DESCENDING
            ) // sort by createdAt in descending order
            .limit(UNREAD_CHATROOM_LIMIT) // limits the count of chatroom by [UNREAD_CHATROOM_LIMIT]
            .findAll()
    }
}