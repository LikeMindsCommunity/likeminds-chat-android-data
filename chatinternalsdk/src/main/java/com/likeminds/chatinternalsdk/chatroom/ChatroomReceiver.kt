package com.likeminds.chatinternalsdk.chatroom

import android.os.Build
import com.likeminds.chatinternalsdk.chatroom.api.ChatroomNetworkApi
import com.likeminds.chatinternalsdk.chatroom.model.TYPE_ANNOUNCEMENT
import com.likeminds.chatinternalsdk.chatroom.model.TYPE_DIRECT_MESSAGE
import com.likeminds.chatinternalsdk.chatroom.model.TYPE_NORMAL
import com.likeminds.chatinternalsdk.chatroom.model._Chatroom_
import com.likeminds.chatinternalsdk.chatroom.model._EditChatroomTitleRequest_
import com.likeminds.chatinternalsdk.chatroom.model._FollowChatroomRequest_
import com.likeminds.chatinternalsdk.chatroom.model._GetChannelInviteRequest_
import com.likeminds.chatinternalsdk.chatroom.model._GetChannelInviteResponse_
import com.likeminds.chatinternalsdk.chatroom.model._GetChatroomActionsRequest_
import com.likeminds.chatinternalsdk.chatroom.model._GetChatroomActionsResponse_
import com.likeminds.chatinternalsdk.chatroom.model._GetParticipantsRequest_
import com.likeminds.chatinternalsdk.chatroom.model._GetParticipantsResponse_
import com.likeminds.chatinternalsdk.chatroom.model._LeaveSecretChatroomRequest_
import com.likeminds.chatinternalsdk.chatroom.model._MarkReadChatroomRequest_
import com.likeminds.chatinternalsdk.chatroom.model._MuteChatroomRequest_
import com.likeminds.chatinternalsdk.chatroom.model._SetChatroomTopicRequest_
import com.likeminds.chatinternalsdk.chatroom.model._UpdateChannelInviteRequest_
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.db.ROConverter
import com.likeminds.chatinternalsdk.db.models.ChatroomRO
import com.likeminds.chatinternalsdk.db.models.ReactionRO
import com.likeminds.chatinternalsdk.db.models.UserRO
import com.likeminds.chatinternalsdk.db.util.DbKey
import com.likeminds.chatinternalsdk.sdk.util.SDKPreferences
import com.likeminds.chatinternalsdk.user.util.UserPreferences
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.rx.CollectionChange
import javax.inject.Inject


class ChatroomReceiver @Inject constructor(
    private val chatroomNetworkApi: ChatroomNetworkApi,
    private val sdkPreferences: SDKPreferences,
    private val userPreferences: UserPreferences
) {

    companion object {
        private const val IS_SECRET_KEY = "is_secret"
        private const val CHATROOM_ID_KEY = "chatroom_id"
        private const val PARTICIPANT_NAME_KEY = "participant_name"
        private const val PAGE_KEY = "page"
        private const val PAGE_SIZE_KEY = "page_size"
        private const val CHANNEL_TYPE_KEY = "channel_type"
    }

    /**
     * API Functions
     */

    suspend fun getChatroomActions(
        request: _GetChatroomActionsRequest_
    ): NetworkResponse<APIResponse<_GetChatroomActionsResponse_>> {
        return chatroomNetworkApi.getChatroomActions(request.chatroomId)
    }

    suspend fun followChatroom(
        request: _FollowChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.followChatroom(request)
    }

    suspend fun leaveSecretChatroom(
        request: _LeaveSecretChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.leaveSecretChatroom(request)
    }

    suspend fun muteChatroom(
        request: _MuteChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.muteChatroom(request)
    }

    suspend fun markReadChatroom(
        request: _MarkReadChatroomRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.markReadChatroom(request)
    }

    suspend fun setChatroomTopic(
        request: _SetChatroomTopicRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.setChatroomTopic(request)
    }

    suspend fun getParticipants(
        request: _GetParticipantsRequest_
    ): NetworkResponse<APIResponse<_GetParticipantsResponse_>> {
        val queries = HashMap<String, Any?>()
        // Set query parameters for request
        queries[IS_SECRET_KEY] = request.isChatroomSecret
        queries[CHATROOM_ID_KEY] = request.chatroomId
        if (request.participantName != null) {
            queries[PARTICIPANT_NAME_KEY] = request.participantName
        }
        queries[PAGE_KEY] = request.page
        queries[PAGE_SIZE_KEY] = request.pageSize

        return chatroomNetworkApi.getParticipants(queries)
    }

    suspend fun editChatroomTitle(
        request: _EditChatroomTitleRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.editChatroomTitle(request)
    }

    suspend fun updateChannelInvite(
        request: _UpdateChannelInviteRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        return chatroomNetworkApi.updateChannelInvite(request)
    }

    suspend fun getChannelInvites(
        request: _GetChannelInviteRequest_
    ): NetworkResponse<APIResponse<_GetChannelInviteResponse_>> {
        val queries = HashMap<String, Any>()
        // Set query parameters for request
        queries[CHANNEL_TYPE_KEY] = request.channelType
        queries[PAGE_KEY] = request.page
        queries[PAGE_SIZE_KEY] = request.pageSize

        return chatroomNetworkApi.getChannelInvites(queries)
    }

    /**
     * DB Functions
     */

    fun getChatroom(realm: Realm, chatroomId: String): ChatroomRO? {
        return ChatDBUtil.getChatroom(realm, chatroomId)
    }

    fun updateChatroomFollowStatus(chatroomId: String, value: Boolean) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatroomRO ->
                chatroomRO.followStatus = value
                if (value) {
                    val currentMillis = System.currentTimeMillis()
                    chatroomRO.updatedAt = currentMillis
                }
            }
        })
    }

    fun updateChatroomMuteStatus(chatroomId: String, value: Boolean) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatroomRO ->
                chatroomRO.muteStatus = value
            }
        })
    }

    fun updateSecretChatroomLeaveStatus(chatroomId: String) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatroomRO ->
                chatroomRO.followStatus = false
                chatroomRO.secretChatRoomLeft = true
            }
        })
    }

    fun updateChatroomTitle(chatroomId: String, updatedTitle: String) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatRoom ->
                chatRoom.title = updatedTitle
                chatRoom.isEdited = true
            }
        })
    }

    fun updateChatroomTopic(chatroomId: String, topicId: String) {
        ChatDBUtil.writeAsync({
            ChatDBUtil.getChatroom(it, chatroomId)?.let { chatroomRO ->
                chatroomRO.topicId = topicId
                val topic = ChatDBUtil.getConversation(it, topicId)
                if (topic != null) {
                    chatroomRO.topic = topic
                }
            }
        })
    }

    fun updateChatroomReaction(
        reaction: String,
        chatroomId: String
    ) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getChatroom(realm, chatroomId)?.let { chatroomRO ->

                //get logged in member
                val userRO = realm.where(UserRO::class.java).findFirst()

                val index = chatroomRO.reactions.indexOfFirst {
                    it.member?.sdkClientInfoRO?.uuid == userRO?.sdkClientInfoRO?.uuid
                }
                val memberObj =
                    ChatDBUtil.getMember(
                        realm,
                        chatroomRO.communityId,
                        userRO?.sdkClientInfoRO?.uuid
                    ) ?: return@let
                val messageReaction = ReactionRO.build() {
                    this.reaction = reaction
                    member = memberObj
                }
                if (index >= 0) {
                    chatroomRO.reactions[index] = messageReaction
                } else {
                    chatroomRO.reactions.add(0, messageReaction)
                }
            }
        })
    }

    fun removeChatroomReaction(chatroomId: String) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getChatroom(realm, chatroomId)?.let { chatroom ->
                //get logged in member
                val userRO = realm.where(UserRO::class.java).findFirst()

                //Remove member previous reactions if any
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    chatroom.reactions.removeIf { reaction ->
                        reaction.member?.id == userRO?.id
                    }
                } else {
                    val reactionRO = chatroom.reactions.find { reaction ->
                        reaction.member?.id == userRO?.id
                    }
                    chatroom.reactions.remove(reactionRO)
                }
            }
        })
    }

    fun updateLastSeenAndDraft(chatroomId: String, draft: String?) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getChatroom(realm, chatroomId)?.let { chatroomRO ->
                if (chatroomRO.unseenCount != 0) {
                    chatroomRO.unseenCount = 0
                }
                if ((chatroomRO.draftConversation ?: "") != draft) {
                    chatroomRO.draftConversation = draft
                }

                val conversations = chatroomRO.conversations
                if (conversations.isNotEmpty()) {
                    chatroomRO.lastSeenConversation = conversations.last()
                }
            }
        })
    }

    // updates the chat request state of the DM chatroom
    fun updateChatRequestState(
        chatroomId: String,
        chatRequestState: Int?,
        chatRequestedById: String?
    ) {
        ChatDBUtil.writeAsync({ realm ->
            ChatDBUtil.getChatroom(realm, chatroomId)?.let { chatroomRO ->
                chatroomRO.chatRequestState = chatRequestState
                chatroomRO.chatRequestedById = chatRequestedById
            }
        })
    }

    fun observeDMChatrooms(realm: Realm): Observable<CollectionChange<RealmResults<ChatroomRO>>>? {
        val communityId = sdkPreferences.getCommunityId()
        var query = realm.where(ChatroomRO::class.java)
        if (communityId != null) {
            query = query.equalTo(DbKey.COMMUNITY_ID, communityId)
        }
        return query.isNull(DbKey.DELETED_BY)
            .equalTo(DbKey.TYPE, TYPE_DIRECT_MESSAGE)
            .greaterThan(DbKey.TOTAL_RESPONSE_COUNT, 0)
            .beginGroup()
            .equalTo(DbKey.MEMBER_OBJECT_UUID, userPreferences.getClientUUID())
            .or()
            .equalTo(DbKey.CHATROOM_WITH_USER_ID, userPreferences.getLMMemberId())
            .endGroup()
            .sort(DbKey.UPDATED_AT, Sort.DESCENDING)
            .findAllAsync()
            .asChangesetObservable()
            .filter {
                it.collection.isLoaded && it.changeset != null
            }
    }

    //saves the chatroom object to the local DB
    fun saveChatroom(chatroom: _Chatroom_) {
        ChatDBUtil.write { realm ->
            val communityId = chatroom.communityId ?: ""

            //gets the creator of the chatroom
            val chatroomCreator =
                ROConverter.convertMember(chatroom.member, communityId)
                    ?: return@write

            realm.insertOrUpdate(chatroomCreator)

            // gets the member object for chatroomWithUser
            val chatroomWithUser = chatroom.chatroomWithUser
            val chatroomWithUserRO = ROConverter.convertMember(chatroomWithUser, communityId)
            if (chatroomWithUserRO != null) {
                realm.insertOrUpdate(chatroomWithUserRO)
            }

            //dumps the chatroom object to the db
            ROConverter.convertChatroom(
                realm,
                chatroom,
                chatroomCreator,
                chatroomWithUserRO = chatroomWithUserRO
            )?.let { chatroomRO ->
                realm.insertOrUpdate(chatroomRO)
            }
        }
    }

    fun getJoinedChatroomsCount(realm: Realm): Pair<Int, Int> {
        val joinedGroupChatrooms = realm.where(ChatroomRO::class.java)
            .equalTo(DbKey.FOLLOW_STATUS, true)
            .beginGroup()
            .equalTo(DbKey.TYPE, TYPE_NORMAL)
            .or()
            .equalTo(DbKey.TYPE, TYPE_ANNOUNCEMENT)
            .endGroup()
            .count()

        val joinedDMChatrooms = realm.where(ChatroomRO::class.java)
            .equalTo(DbKey.TYPE, TYPE_DIRECT_MESSAGE)
            .beginGroup()
            .equalTo(DbKey.MEMBER_OBJECT_UUID, userPreferences.getClientUUID())
            .or()
            .equalTo(DbKey.CHATROOM_WITH_USER_ID, userPreferences.getLMMemberId())
            .endGroup()
            .count()

        return Pair(joinedGroupChatrooms.toInt(), joinedDMChatrooms.toInt())
    }

    fun getUnreadConversationsCount(realm: Realm): Pair<Int, Int> {
        val unreadGroupChatroomConversations = realm.where(ChatroomRO::class.java)
            .equalTo(DbKey.FOLLOW_STATUS, true)
            .beginGroup()
            .equalTo(DbKey.TYPE, TYPE_NORMAL)
            .or()
            .equalTo(DbKey.TYPE, TYPE_ANNOUNCEMENT)
            .endGroup()
            .sum(DbKey.UNSEEN_COUNT)
            .toInt()

        val unreadDMChatroomConversations = realm.where(ChatroomRO::class.java)
            .equalTo(DbKey.TYPE, TYPE_DIRECT_MESSAGE)
            .beginGroup()
            .equalTo(DbKey.MEMBER_OBJECT_UUID, userPreferences.getClientUUID())
            .or()
            .equalTo(DbKey.CHATROOM_WITH_USER_ID, userPreferences.getLMMemberId())
            .endGroup()
            .sum(DbKey.UNSEEN_COUNT)
            .toInt()

        return Pair(unreadGroupChatroomConversations, unreadDMChatroomConversations)
    }

    fun getExistingDMChatroom(realm: Realm, userUUID: String): ChatroomRO? {
        return realm.where(ChatroomRO::class.java)
            .equalTo(DbKey.TYPE, TYPE_DIRECT_MESSAGE)
            .beginGroup()
            .equalTo(DbKey.MEMBER_OBJECT_UUID, userUUID)
            .or()
            .equalTo(DbKey.CHATROOM_WITH_USER_OBJECT_UUID, userUUID)
            .endGroup()
            .findFirst()
    }
}