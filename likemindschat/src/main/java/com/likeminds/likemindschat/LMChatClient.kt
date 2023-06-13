package com.likeminds.likemindschat

import android.app.Application
import android.content.Context
import com.likeminds.likemindschat.chatroom.ChatroomClient
import com.likeminds.likemindschat.chatroom.model.*
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
import com.likeminds.likemindschat.user.UserClient
import com.likeminds.likemindschat.user.model.User
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
    lateinit var moderationClient: ModerationClient

    @Inject
    lateinit var pollClient: PollClient

    @Inject
    lateinit var helperClient: HelperClient

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

        @JvmStatic
        fun getInstance(): LMChatClient {
            if (lmChatClientInstance == null) {
                throw IllegalAccessException("LMChatClient is not created, please call LMChatClient.build()")
            }
            return lmChatClientInstance!!
        }
    }

    //function to process initiate user request
    suspend fun initiateUser(initiateUserRequest: InitiateUserRequest): LMResponse<InitiateUserResponse> {
        return initiateUserClient.initiateUser(initiateUserRequest)
    }

    //function to process logout request
    suspend fun logout(logoutRequest: LogoutRequest): LMResponse<Nothing> {
        return initiateUserClient.logout(logoutRequest)
    }

    // Exposed function to register device
    suspend fun registerDevice(registerDeviceRequest: RegisterDeviceRequest): LMResponse<Nothing> {
        return initiateUserClient.registerDevice(registerDeviceRequest)
    }

    //function to get explore tab count
    suspend fun getExploreTabCount(): LMResponse<GetExploreTabCountResponse> {
        return homeFeedClient.getExploreTabCount()
    }

    //function to get chatrooms for home feed
    suspend fun getChatrooms(context: Context, listener: HomeFeedChangeListener) {
        homeFeedClient.getChatrooms(context, listener)
    }

    //function to get config details
    suspend fun getConfig(): LMResponse<ConfigResponse> {
        return homeFeedClient.getConfig()
    }

    // Exposed function to get user from Db
    suspend fun getUser(): LMResponse<User> {
        return userClient.getUser()
    }

    // Exposed function to get chatroom
    suspend fun getChatroom(getChatroomRequest: GetChatroomRequest): LMResponse<GetChatroomResponse> {
        return chatroomClient.getChatroom(getChatroomRequest)
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

    // Exposed function to get chatroom's share url
    suspend fun shareChatroomUrl(shareChatroomUrlRequest: ShareChatroomUrlRequest): LMResponse<ShareChatroomUrlResponse> {
        return chatroomClient.shareChatroomUrl(shareChatroomUrlRequest)
    }

    // Exposed function to set chatroom's topic
    suspend fun setChatroomTopic(setChatroomTopicRequest: SetChatroomTopicRequest): LMResponse<Nothing> {
        return chatroomClient.setChatroomTopic(setChatroomTopicRequest)
    }

    // Exposed function to get list of participants in chatroom
    suspend fun getChatroomParticipants(getChatroomParticipantsRequest: GetChatroomParticipantsRequest): LMResponse<GetChatroomParticipantsResponse> {
        return chatroomClient.getChatroomParticipants(getChatroomParticipantsRequest)
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
}