package com.likeminds.likemindschat

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.WorkInfo
import com.likeminds.likemindschat.chatroom.ChatroomClient
import com.likeminds.likemindschat.chatroom.model.*
import com.likeminds.likemindschat.community.CommunityClient
import com.likeminds.likemindschat.community.model.*
import com.likeminds.likemindschat.conversation.ConversationClient
import com.likeminds.likemindschat.conversation.model.*
import com.likeminds.likemindschat.dm.DMClient
import com.likeminds.likemindschat.dm.model.*
import com.likeminds.likemindschat.helper.HelperClient
import com.likeminds.likemindschat.helper.model.*
import com.likeminds.likemindschat.homefeed.HomeFeedClient
import com.likeminds.likemindschat.homefeed.model.ConfigResponse
import com.likeminds.likemindschat.homefeed.model.GetExploreTabCountResponse
import com.likeminds.likemindschat.homefeed.util.HomeChatroomListener
import com.likeminds.likemindschat.moderation.ModerationClient
import com.likeminds.likemindschat.moderation.model.*
import com.likeminds.likemindschat.notification.NotificationClient
import com.likeminds.likemindschat.notification.model.GetUnreadChatroomsRequest
import com.likeminds.likemindschat.notification.model.GetUnreadChatroomsResponse
import com.likeminds.likemindschat.poll.PollClient
import com.likeminds.likemindschat.poll.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.search.SearchClient
import com.likeminds.likemindschat.search.model.*
import com.likeminds.likemindschat.user.UserClient
import com.likeminds.likemindschat.user.model.*
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LMChatClient private constructor() {

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

    @Inject
    lateinit var notificationClient: NotificationClient

    @Inject
    lateinit var dmClient: DMClient

    class Builder(val application: Application) {
        private var lmChatSDKCallback: LMChatSDKCallback? = null

        fun lmChatSDKCallback(lmChatSDKCallback: LMChatSDKCallback?) = apply {
            this.lmChatSDKCallback = lmChatSDKCallback
        }

        fun build(): LMChatClient {
            lmChatClientInstance = LMChatClient()
            val sdkApplication = LikeMindsChatApplication.getInstance()
            sdkApplication.initChatSDKApplication(application, lmChatSDKCallback)
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
        return userClient.initiateUser(initiateUserRequest)
    }

    suspend fun validateUser(validateUserRequest: ValidateUserRequest): LMResponse<ValidateUserResponse> {
        return userClient.validateUser(validateUserRequest)
    }

    // Exposed function to process initiate user request
    suspend fun getMemberState(): LMResponse<MemberStateResponse> {
        return userClient.getMemberState()
    }

    // Exposed function to process logout request
    suspend fun logout(logoutRequest: LogoutRequest): LMResponse<Nothing> {
        return userClient.logout(logoutRequest)
    }

    // Exposed function to register device
    suspend fun registerDevice(registerDeviceRequest: RegisterDeviceRequest): LMResponse<Nothing> {
        return userClient.registerDevice(registerDeviceRequest)
    }

    // Exposed function to get explore tab count
    suspend fun getExploreTabCount(): LMResponse<GetExploreTabCountResponse> {
        return homeFeedClient.getExploreTabCount()
    }

    // Exposed function to start group chatroom sync
    fun loadGroupChatrooms(
        context: Context,
    ): Pair<LiveData<MutableList<WorkInfo>>?, LiveData<MutableList<WorkInfo>>?>? {
        return homeFeedClient.loadGroupChatrooms(context)
    }

    // Exposed function to start dm chatroom sync
    fun loadDMChatrooms(
        context: Context
    ): Pair<LiveData<MutableList<WorkInfo>>?, LiveData<MutableList<WorkInfo>>?>? {
        return dmClient.loadDMChatrooms(context)
    }

    // Exposed function to get chatrooms for home feed
    fun getChatrooms(listener: HomeChatroomListener): Observable<Unit>? {
        return homeFeedClient.getChatrooms(listener)
    }

    // Exposed function to get chatrooms for home feed
    fun observeLiveGroupChatroom(context: Context) {
        homeFeedClient.observeLiveGroupChatroom(context)
    }

    // Exposed function to remove home feed listener
    fun removeLiveGroupChatroomListener() {
        homeFeedClient.removeLiveGroupChatroomListener()
    }

    // Exposed function to get chatrooms for dm feed
    fun observeLiveDMChatroom(context: Context) {
        dmClient.observeLiveDMChatrooms(context)
    }

    // Exposed function to remove dm feed listener
    fun removeLiveDMChatroomListener() {
        dmClient.removeLiveDMChatroomListener()
    }

    //function to get config details
    suspend fun getConfig(): LMResponse<ConfigResponse> {
        return homeFeedClient.getConfig()
    }

    // Exposed function to get user from Db
    fun getLoggedInUser(): LMResponse<GetLoggedInUserResponse> {
        return userClient.getLoggedInUser()
    }

    // Exposed function to get member from Db
    fun getMember(getMemberRequest: GetMemberRequest): LMResponse<GetMemberResponse> {
        return userClient.getMember(getMemberRequest)
    }

    // Exposed function to get API Key
    fun getAPIKey(): LMResponse<String> {
        return userClient.getAPIKey()
    }

    // Exposed function to set tokens
    fun setTokens(setTokensRequest: SetTokensRequest): LMResponse<Nothing> {
        return userClient.setTokens(setTokensRequest)
    }

    // Exposed function to get tokens
    fun getTokens(): LMResponse<Pair<String, String>> {
        return userClient.getTokens()
    }

    // Exposed function to get chatroom from Db
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
    suspend fun submitPoll(
        context: Context, submitPollRequest: SubmitPollRequest
    ): LMResponse<Nothing> {
        return pollClient.submitPoll(context, submitPollRequest)
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

    // Exposed function to get whether DB is empty or not
    fun getDBEmpty(): LMResponse<GetDBEmptyResponse> {
        return helperClient.getDBEmpty()
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
    suspend fun observeConversations(observeConversationsRequest: ObserveConversationsRequest) {
        conversationClient.observeConversations(observeConversationsRequest)
    }

    //Exposed function to observe live conversations
    suspend fun observeLiveConversations(context: Context, chatroomId: String) {
        return conversationClient.observeLiveConversations(context, chatroomId)
    }

    //Exposed function to load conversation to db
    fun loadConversations(
        context: Context, type: LoadConversationType, chatroomId: String
    ): MediatorLiveData<WorkInfo.State> {
        return conversationClient.loadConversations(context, type, chatroomId)
    }

    // Exposed function to get conversations
    fun getConversations(getConversationsRequest: GetConversationsRequest): LMResponse<GetConversationsResponse> {
        return conversationClient.getConversations(getConversationsRequest)
    }

    // Exposed function to get conversations count
    fun getConversationsCount(getConversationsCountRequest: GetConversationsCountRequest): LMResponse<GetConversationsCountResponse> {
        return conversationClient.getConversationsCount(getConversationsCountRequest)
    }

    // Exposed function to delete a conversation permanently
    fun deleteConversationPermanently(deleteConversationPermanentlyRequest: DeleteConversationPermanentlyRequest) {
        return conversationClient.deleteConversationPermanently(deleteConversationPermanentlyRequest)
    }

    // Exposed function to save temporary conversation
    fun saveTemporaryConversation(saveConversationRequest: SaveConversationRequest) {
        conversationClient.saveTemporaryConversation(saveConversationRequest)
    }

    // Exposed function to update conversation worker uuid
    fun updateConversationWorkerUUID(updateConversationWorkerUUIDRequest: UpdateConversationWorkerUUIDRequest) {
        conversationClient.updateConversationWorkerUUID(
            updateConversationWorkerUUIDRequest
        )
    }

    // Exposed function to update conversation
    fun updateConversation(updateConversationRequest: UpdateConversationRequest) {
        conversationClient.updateConversation(updateConversationRequest)
    }


    // Exposed function to update conversation upload worker uuid
    fun updateTemporaryConversation(updateTemporaryConversationRequest: UpdateTemporaryConversationRequest) {
        conversationClient.updateTemporaryConversation(updateTemporaryConversationRequest)
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

    // Exposed function to fetch unread conversation for notification
    suspend fun getUnreadChatrooms(getUnreadChatroomsRequest: GetUnreadChatroomsRequest): LMResponse<GetUnreadChatroomsResponse> {
        return notificationClient.getUnreadChatrooms(getUnreadChatroomsRequest)
    }

    // Exposed function to set last seen to true and saves draft response
    fun updateLastSeenAndDraft(updateLastSeenAndDraftRequest: UpdateLastSeenAndDraftRequest) {
        chatroomClient.updateLastSeenAndDraft(updateLastSeenAndDraftRequest)
    }

    // Exposed function to edit a chatroom title
    suspend fun editChatroomTitle(editChatroomTitleRequest: EditChatroomTitleRequest): LMResponse<Nothing> {
        return chatroomClient.editChatroomTitle(editChatroomTitleRequest)
    }

    // Exposed function to save a posted conversation
    fun savePostedConversation(savePostedConversationRequest: SavePostedConversationRequest) {
        conversationClient.savePostedConversation(savePostedConversationRequest)
    }

    // Exposed function to find whether the conversation is within the limit of the provided conversation
    fun isConversationWithinLimit(conversationWithinLimitRequest: ConversationWithinLimitRequest): Boolean {
        return conversationClient.isConversationWithinLimit(conversationWithinLimitRequest)
    }

    // Exposed function to get content download settings
    suspend fun getContentDownloadSettings(): LMResponse<GetContentDownloadSettingsResponse> {
        return communityClient.getContentDownloadSettings()
    }

    // Exposed function to observe a community
    fun observeCommunity(): Observable<Community> {
        return communityClient.observeCommunity()
    }

    // Exposed function to check whether dm is enabled or not
    suspend fun checkDMTab(): LMResponse<CheckDMTabResponse> {
        return dmClient.checkDMTab()
    }

    // Exposed function to send a dm request
    suspend fun sendDMRequest(sendDMRequest: SendDMRequest): LMResponse<SendDMResponse> {
        return dmClient.sendDMRequest(sendDMRequest)
    }

    // Exposed function to check the status of the DM
    suspend fun checkDMStatus(checkDMStatusRequest: CheckDMStatusRequest): LMResponse<CheckDMStatusResponse> {
        return dmClient.checkDMStatus(checkDMStatusRequest)
    }

    // Exposed function to block a member
    suspend fun blockMember(blockMemberRequest: BlockMemberRequest): LMResponse<BlockMemberResponse> {
        return dmClient.blockMember(blockMemberRequest)
    }

    // Exposed function to check the DM limit
    suspend fun checkDMLimit(checkDMLimitRequest: CheckDMLimitRequest): LMResponse<CheckDMLimitResponse> {
        return dmClient.checkDMLimit(checkDMLimitRequest)
    }

    // Exposed function to create a DM chatroom
    suspend fun createDMChatroom(createDMChatroomRequest: CreateDMChatroomRequest): LMResponse<CreateDMChatroomResponse> {
        return dmClient.createDMChatroom(createDMChatroomRequest)
    }

    // Exposed function to get all the members in community
    suspend fun getAllMember(getAllMemberRequest: GetAllMemberRequest): LMResponse<GetAllMemberResponse> {
        return communityClient.getAllMember(getAllMemberRequest)
    }

    // Exposed function to search members in community
    suspend fun searchMember(searchMembersRequest: SearchMembersRequest): LMResponse<SearchMembersResponse> {
        return communityClient.searchMember(searchMembersRequest)
    }

    // Exposed function to observe DM chatrooms
    fun observeDMChatrooms(listener: HomeChatroomListener): Observable<Unit>? {
        return dmClient.observeDMChatrooms(listener)
    }

    // Exposed function to get community configurations
    suspend fun getCommunityConfigurations(): LMResponse<GetCommunityConfigurationsResponse> {
        return communityClient.getCommunityConfigurations()
    }

    // Exposed function to update status of channel invite
    suspend fun updateChannelInvite(updateChannelInviteRequest: UpdateChannelInviteRequest): LMResponse<Nothing> {
        return chatroomClient.updateChannelInvite(updateChannelInviteRequest)
    }

    // Exposed function to get secret channel invites
    suspend fun getChannelInvites(getChannelInviteRequest: GetChannelInviteRequest): LMResponse<GetChannelInviteResponse> {
        return chatroomClient.getChannelInvites(getChannelInviteRequest)
    }

    // Exposed function to edit user profile
    suspend fun editUserProfile(editUserProfileRequest: EditUserProfileRequest): LMResponse<Nothing> {
        return userClient.editUserProfile(editUserProfileRequest)
    }

    // Exposed function to get list of AI chatbots
    suspend fun getAIChatbots(getAIChatbotsRequest: GetAIChatbotsRequest): LMResponse<GetAIChatbotsResponse> {
        return communityClient.getAIChatbots(getAIChatbotsRequest)
    }
}