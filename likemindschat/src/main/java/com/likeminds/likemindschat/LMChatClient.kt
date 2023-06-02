package com.likeminds.likemindschat

import android.app.Application
import com.likeminds.likemindschat.chatroom.ChatroomClient
import com.likeminds.likemindschat.chatroom.model.*
import com.likeminds.likemindschat.helper.HelperClient
import com.likeminds.likemindschat.helper.model.DecodeUrlRequest
import com.likeminds.likemindschat.helper.model.DecodeUrlResponse
import com.likeminds.likemindschat.helper.model.GetTaggingListRequest
import com.likeminds.likemindschat.helper.model.GetTaggingListResponse
import com.likeminds.likemindschat.initiateUser.InitiateUserClient
import com.likeminds.likemindschat.initiateUser.model.InitiateUserRequest
import com.likeminds.likemindschat.initiateUser.model.InitiateUserResponse
import com.likeminds.likemindschat.initiateUser.model.LogoutRequest
import com.likeminds.likemindschat.initiateUser.model.RegisterDeviceRequest
import com.likeminds.likemindschat.moderation.ModerationClient
import com.likeminds.likemindschat.moderation.model.GetReportTagsRequest
import com.likeminds.likemindschat.moderation.model.GetReportTagsResponse
import com.likeminds.likemindschat.moderation.model.PostReportRequest
import com.likeminds.likemindschat.poll.PollClient
import com.likeminds.likemindschat.poll.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.search.SearchClient
import com.likeminds.likemindschat.search.model.SearchChatroomRequest
import com.likeminds.likemindschat.search.model.SearchChatroomResponse
import com.likeminds.likemindschat.search.model.SearchConversationRequest
import com.likeminds.likemindschat.search.model.SearchConversationResponse
import com.likeminds.likemindschat.user.UserClient
import com.likeminds.likemindschat.user.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LMChatClient private constructor() {

    @Inject
    lateinit var initiateUserClient: InitiateUserClient

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

    @Inject
    lateinit var searchClient: SearchClient

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

    // Exposed function to search a chatroom
    suspend fun searchChatroom(searchChatroomRequest: SearchChatroomRequest): LMResponse<SearchChatroomResponse> {
        return searchClient.searchChatroom(searchChatroomRequest)
    }

    // Exposed function to search a conversation
    suspend fun searchConversation(searchConversationRequest: SearchConversationRequest): LMResponse<SearchConversationResponse> {
        return searchClient.searchConversation(searchConversationRequest)
    }
}