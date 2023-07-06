package com.likeminds.likemindschat

import android.app.Application
import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.work.WorkInfo
import com.likeminds.likemindschat.chatroom.ChatroomClient
import com.likeminds.likemindschat.chatroom.model.*
import com.likeminds.likemindschat.community.CommunityClient
import com.likeminds.likemindschat.community.model.GetExploreFeedRequest
import com.likeminds.likemindschat.community.model.GetExploreFeedResponse
import com.likeminds.likemindschat.conversation.ConversationClient
import com.likeminds.likemindschat.conversation.model.*
import com.likeminds.likemindschat.conversation.util.LoadConversationType
import com.likeminds.likemindschat.helper.HelperClient
import com.likeminds.likemindschat.helper.model.*
import com.likeminds.likemindschat.homefeed.HomeFeedClient
import com.likeminds.likemindschat.homefeed.model.ConfigResponse
import com.likeminds.likemindschat.homefeed.model.GetExploreTabCountResponse
import com.likeminds.likemindschat.homefeed.util.HomeFeedChangeListener
import com.likeminds.likemindschat.initiateUser.InitiateUserClient
import com.likeminds.likemindschat.initiateUser.model.*
import com.likeminds.likemindschat.moderation.ModerationClient
import com.likeminds.likemindschat.moderation.model.*
import com.likeminds.likemindschat.poll.PollClient
import com.likeminds.likemindschat.poll.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.search.SearchClient
import com.likeminds.likemindschat.search.model.*
import com.likeminds.likemindschat.user.UserClient
import com.likeminds.likemindschat.user.model.GetUserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LMChatClient private constructor() {

    @Inject
    lateinit var initiateUserClient: InitiateUserClient

    @Inject
    lateinit var homeFeedClient: HomeFeedClient

    @Inject
    lateinit var userClient: UserClient

    @Inject
    lateinit var chatroomClient: ChatroomClient

    @Inject
    lateinit var communityClient: CommunityClient

    @Inject
    lateinit var moderationClient: ModerationClient

    @Inject
    lateinit var pollClient: PollClient

    @Inject
    lateinit var helperClient: HelperClient

    @Inject
    lateinit var searchClient: SearchClient

    @Inject
    lateinit var conversationClient: ConversationClient

    class Builder(val application: Application) {

        fun build(): LMChatClient {
            lmChatClientInstance = LMChatClient()
            val sdkApplication = LikeMindsChatApplication.getInstance()
            sdkApplication.initChatSDKApplication(application)
            sdkApplication.likeMindsChatComponent?.inject(lmChatClientInstance!!)
            return lmChatClientInstance!!
        }
    }

    companion object {

        @JvmStatic
        private var lmChatClientInstance: LMChatClient? = null

        const val TAG = "LMChatClient"

        @JvmStatic
        fun getInstance(): LMChatClient {
            if (lmChatClientInstance == null) {
                throw IllegalAccessException("LMChatClient is not created, please call LMChatClient.build()")
            }
            return lmChatClientInstance!!
        }
    }

    // Exposed function to process initiate user request
    suspend fun initiateUser(initiateUserRequest: InitiateUserRequest): LMResponse<InitiateUserResponse> {
        return initiateUserClient.initiateUser(initiateUserRequest)
    }

    // Exposed function to process logout request
    suspend fun logout(logoutRequest: LogoutRequest): LMResponse<Nothing> {
        return initiateUserClient.logout(logoutRequest)
    }

    // Exposed function to register device
    suspend fun registerDevice(registerDeviceRequest: RegisterDeviceRequest): LMResponse<Nothing> {
        return initiateUserClient.registerDevice(registerDeviceRequest)
    }

    // Exposed function to get explore tab count
    suspend fun getExploreTabCount(): LMResponse<GetExploreTabCountResponse> {
        return homeFeedClient.getExploreTabCount()
    }

    // Exposed function to get config details
    //function to get chatrooms for home feed
    suspend fun getChatrooms(context: Context, listener: HomeFeedChangeListener) {
        homeFeedClient.getChatrooms(context, listener)
    }

    //function to get config details
    suspend fun getConfig(): LMResponse<ConfigResponse> {
        return homeFeedClient.getConfig()
    }

    // Exposed function to get user from Db
    fun getUser(): LMResponse<GetUserResponse> {
        return userClient.getUser()
    }

    fun getChatroom(getChatroomRequest: GetChatroomRequest): LMResponse<GetChatroomResponse> {
        return chatroomClient.getChatroom(getChatroomRequest)
    }

    // Exposed function to get chatroom actions
    suspend fun getChatroomActions(getChatroomActionsRequest: GetChatroomActionsRequest): LMResponse<GetChatroomActionsResponse> {
        return chatroomClient.getChatroomActions(getChatroomActionsRequest)
    }

    // Exposed function to follow chatroom
    suspend fun followChatroom(followChatroomRequest: FollowChatroomRequest): LMResponse<Nothing> {
        return chatroomClient.followChatroom(followChatroomRequest)
    }

    // Exposed function to leave a secret chatroom
    suspend fun leaveSecretChatroom(leaveSecretChatroomRequest: LeaveSecretChatroomRequest): LMResponse<Nothing> {
        return chatroomClient.leaveSecretChatroom(leaveSecretChatroomRequest)
    }

    // Exposed function to mute a chatroom
    suspend fun muteChatroom(muteChatroomRequest: MuteChatroomRequest): LMResponse<Nothing> {
        return chatroomClient.muteChatroom(muteChatroomRequest)
    }

    // Exposed function to mark a chatroom as read
    suspend fun markReadChatroom(markReadChatroomRequest: MarkReadChatroomRequest): LMResponse<Nothing> {
        return chatroomClient.markReadChatroom(markReadChatroomRequest)
    }

    // Exposed function to set chatroom's topic
    suspend fun setChatroomTopic(setChatroomTopicRequest: SetChatroomTopicRequest): LMResponse<Nothing> {
        return chatroomClient.setChatroomTopic(setChatroomTopicRequest)
    }

    // Exposed function to get list of participants in chatroom
    suspend fun getParticipants(getParticipantsRequest: GetParticipantsRequest): LMResponse<GetParticipantsResponse> {
        return chatroomClient.getParticipants(getParticipantsRequest)
    }

    // Exposed function to get list of participants in chatroom
    suspend fun getExploreFeed(getExploreFeedRequest: GetExploreFeedRequest): LMResponse<GetExploreFeedResponse> {
        return communityClient.getExploreFeed(getExploreFeedRequest)
    }

    // Exposed function to process request to fetch report tags
    suspend fun getReportTags(getReportTagsRequest: GetReportTagsRequest): LMResponse<GetReportTagsResponse> {
        return moderationClient.getReportTags(getReportTagsRequest)
    }

    // Exposed function to process request to post report on the entity
    suspend fun postReport(postReportRequest: PostReportRequest): LMResponse<Nothing> {
        return moderationClient.postReport(postReportRequest)
    }

    // Exposed function to process request to add poll option in micro poll
    suspend fun addPollOption(addPollOptionRequest: AddPollOptionRequest): LMResponse<AddPollOptionResponse> {
        return pollClient.addPollOption(addPollOptionRequest)
    }

    // Exposed function to process request to submit polls selected
    suspend fun submitPoll(submitPollRequest: SubmitPollRequest): LMResponse<Nothing> {
        return pollClient.submitPoll(submitPollRequest)
    }

    // Exposed function to process request to get users who have voted on that particular poll option
    suspend fun getPollUsers(getPollUsersRequest: GetPollUsersRequest): LMResponse<GetPollUsersResponse> {
        return pollClient.getPollUsers(getPollUsersRequest)
    }

    // Exposed function to post a poll conversation
    suspend fun postPollConversation(postPollConversationRequest: PostPollConversationRequest): LMResponse<PostPollConversationResponse> {
        return pollClient.postPollConversation(postPollConversationRequest)
    }

    // Exposed function to decode url and fetch ogTags
    suspend fun decodeUrl(decodeUrlRequest: DecodeUrlRequest): LMResponse<DecodeUrlResponse> {
        return helperClient.decodeUrl(decodeUrlRequest)
    }

    // Exposed function to fetch tagging list
    suspend fun getTaggingList(getTaggingListRequest: GetTaggingListRequest): LMResponse<GetTaggingListResponse> {
        return helperClient.getTaggingList(getTaggingListRequest)
    }

    // Exposed function to search a chatroom
    suspend fun searchChatroom(searchChatroomRequest: SearchChatroomRequest): LMResponse<SearchChatroomResponse> {
        return searchClient.searchChatroom(searchChatroomRequest)
    }

    // Exposed function to search a conversation
    suspend fun searchConversation(searchConversationRequest: SearchConversationRequest): LMResponse<SearchConversationResponse> {
        return searchClient.searchConversation(searchConversationRequest)
    }

    // Exposed function to observe new conversations
    suspend fun observeConversations(
        observeConversationsRequest: ObserveConversationsRequest
    ) {
        conversationClient.observeConversations(observeConversationsRequest)
    }

    //Exposed function to load conversation to db
    fun loadConversations(
        context: Context,
        type: LoadConversationType,
        chatroomId: String
    ): MediatorLiveData<WorkInfo.State> {
        return conversationClient.loadConversations(context, type, chatroomId)
    }

    // Exposed function to get conversations
    fun getConversations(getConversationsRequest: GetConversationsRequest): LMResponse<GetConversationsResponse> {
        return conversationClient.getConversations(getConversationsRequest)
    }

    // Exposed function to save temporary conversation
    fun saveTemporaryConversation(saveConversationRequest: SaveConversationRequest) {
        conversationClient.saveTemporaryConversation(saveConversationRequest)
    }

    // Exposed function to get a single conversation
    fun getConversation(getConversationRequest: GetConversationRequest): LMResponse<GetConversationResponse> {
        return conversationClient.getConversation(getConversationRequest)
    }

    // Exposed function to post conversation
    suspend fun postConversation(postConversationRequest: PostConversationRequest): LMResponse<PostConversationResponse> {
        return conversationClient.postConversation(postConversationRequest)
    }

    // Exposed function to edit conversation
    suspend fun editConversation(editConversationRequest: EditConversationRequest): LMResponse<EditConversationResponse> {
        return conversationClient.editConversation(editConversationRequest)
    }

    // Exposed function to delete conversation
    suspend fun deleteConversations(deleteConversationsRequest: DeleteConversationsRequest): LMResponse<DeleteConversationsResponse> {
        return conversationClient.deleteConversations(deleteConversationsRequest)
    }

    // Exposed function to put a reaction on a conversation
    suspend fun putReaction(putReactionRequest: PutReactionRequest): LMResponse<Nothing> {
        return conversationClient.putReaction(putReactionRequest)
    }

    // Exposed function to delete a reaction on a conversation
    suspend fun deleteReaction(deleteReactionRequest: DeleteReactionRequest): LMResponse<Nothing> {
        return conversationClient.deleteReaction(deleteReactionRequest)
    }

    // Exposed function to upload a conversation media
    suspend fun putMultimedia(putMultimediaRequest: PutMultimediaRequest): LMResponse<PutMultimediaResponse> {
        return conversationClient.putMultimedia(putMultimediaRequest)
    }
}