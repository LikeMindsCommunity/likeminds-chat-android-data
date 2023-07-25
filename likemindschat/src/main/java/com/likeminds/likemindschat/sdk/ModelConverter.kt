package com.likeminds.likemindschat.sdk

import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.community.model.*
import com.likeminds.internalsdk.conversation.model.*
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.helper.model.*
import com.likeminds.internalsdk.homefeed.model.*
import com.likeminds.internalsdk.moderation.model._GetReportTagsResponse_
import com.likeminds.internalsdk.moderation.model._ReportTag_
import com.likeminds.internalsdk.notification.model._ChatroomNotificationData_
import com.likeminds.internalsdk.notification.model._GetConversationNotificationUnreadResponse_
import com.likeminds.internalsdk.poll.model.*
import com.likeminds.internalsdk.sdk.model._InitiateUserResponse_
import com.likeminds.internalsdk.search.model.*
import com.likeminds.internalsdk.user.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.chatroom.model.*
import com.likeminds.likemindschat.community.model.*
import com.likeminds.likemindschat.conversation.model.*
import com.likeminds.likemindschat.helper.model.*
import com.likeminds.likemindschat.homefeed.model.*
import com.likeminds.likemindschat.initiateUser.model.InitiateUserResponse
import com.likeminds.likemindschat.moderation.model.GetReportTagsResponse
import com.likeminds.likemindschat.moderation.model.ReportTag
import com.likeminds.likemindschat.notification.model.ChatroomNotificationData
import com.likeminds.likemindschat.notification.model.GetConversationNotificationUnreadResponse
import com.likeminds.likemindschat.poll.model.*
import com.likeminds.likemindschat.search.model.*
import com.likeminds.likemindschat.user.model.*

object ModelConverter {

    /**--------------------------------
     * Internal Model -> Client Model
    --------------------------------*/

    // converts api InitiateUserResponse model to LM InitiateUserResponse model
    fun convertInitiateUserAPIResponse(
        apiResponse: APIResponse<_InitiateUserResponse_>
    ): LMResponse<InitiateUserResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertInitiateUserResponse(apiResponse.data)
        )
    }

    // converts internal InitiateUserResponse model to client model
    private fun convertInitiateUserResponse(
        _initiateUserResponse_: _InitiateUserResponse_?
    ): InitiateUserResponse? {
        if (_initiateUserResponse_ == null) return null
        return InitiateUserResponse(
            _initiateUserResponse_.accessToken,
            _initiateUserResponse_.refreshToken,
            convertUser(_initiateUserResponse_.user),
            convertCommunity(_initiateUserResponse_.community),
            _initiateUserResponse_.appAccess
        )
    }

    private fun convertUsers(_users_: List<_User_>): List<User> {
        return _users_.map {
            convertUser(it)
        }
    }

    // converts internal User model to client model
    private fun convertUser(
        _user_: _User_
    ): User {
        return User(
            _user_.id,
            _user_.imageUrl,
            _user_.isGuest,
            _user_.name,
            _user_.organisationName,
            convertSDKClientInfo(_user_.sdkClientInfo),
            _user_.isDeleted,
            _user_.customTitle,
            _user_.updatedAt,
            _user_.userUniqueId,
            _user_.uuid
        )
    }

    // converts internal SDKClientInfo model to client model
    private fun convertSDKClientInfo(
        _sdkClientInfo_: _SDKClientInfo_?
    ): SDKClientInfo? {
        return _sdkClientInfo_?.let {
            SDKClientInfo(
                it.community,
                it.user,
                it.userUniqueId,
                it.uuid
            )
        }
    }

    // converts internal Community model to client model
    private fun convertCommunity(
        _community_: _Community_
    ): Community {
        return Community(
            _community_.id,
            _community_.name,
            _community_.imageUrl,
            _community_.membersCount,
            _community_.updatedAt,
        )
    }

    //converts API GetExploreTabCountResponse model to LM model
    fun convertGetExploreTabCountAPIResponse(
        apiResponse: APIResponse<_GetExploreTabCountResponse_>
    ): LMResponse<GetExploreTabCountResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetExploreTabCountResponse(apiResponse.data)
        )
    }

    //converts internal GetExploreTabCountResponse model to client model
    private fun convertGetExploreTabCountResponse(_getExploreTabCountResponse_: _GetExploreTabCountResponse_?): GetExploreTabCountResponse? {
        if (_getExploreTabCountResponse_ == null) return null
        return GetExploreTabCountResponse(
            _getExploreTabCountResponse_.unseenChatroomCount,
            _getExploreTabCountResponse_.totalChatroomCount
        )
    }

    //converts API ConfigResponse model to LM model
    fun convertConfigAPIResponse(
        apiResponse: APIResponse<_ConfigResponse_>
    ): LMResponse<ConfigResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertConfigResponse(apiResponse.data)
        )
    }

    //converts internal ConfigResponse model to client model
    private fun convertConfigResponse(_configResponse_: _ConfigResponse_?): ConfigResponse? {
        if (_configResponse_ == null) return null
        return ConfigResponse(
            _configResponse_.access,
            _configResponse_.enableAudio,
            _configResponse_.enableGifs,
            _configResponse_.enableVoiceNote,
            _configResponse_.enableMicroPolls,
            convertUserDetails(_configResponse_.userDetails)
        )
    }

    //converts internal UserDetail model to client model
    private fun convertUserDetails(_userDetails_: _UserDetail_): UserDetail {
        return UserDetail(
            convertMember(_userDetails_.member),
            convertUserMetrics(_userDetails_.userMetrics)
        )
    }

    //converts internal UserMetrics model to client model
    private fun convertUserMetrics(_userMetrics_: _UserMetrics_): UserMetrics {
        return UserMetrics(
            _userMetrics_.firstLogin,
            _userMetrics_.firstLoginEpoch,
            _userMetrics_.countCommunitiesJoined,
            _userMetrics_.nameCommunitiesJoined,
            _userMetrics_.isAnyCommunityPromoter,
            _userMetrics_.uniqueChatroomResponded,
            _userMetrics_.countChatroomCreated,
            _userMetrics_.countChatroomFollowed
        )
    }

    // converts api GetChatroomResponse model to LM GetChatroomResponse model
    fun convertGetChatroomActionsAPIResponse(
        apiResponse: APIResponse<_GetChatroomActionsResponse_>
    ): LMResponse<GetChatroomActionsResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetChatroomActionsResponse(apiResponse.data)
        )
    }

    // converts internal GetChatroomResponse model to client model
    private fun convertGetChatroomActionsResponse(
        _getChatroomActionsResponse_: _GetChatroomActionsResponse_?
    ): GetChatroomActionsResponse? {
        if (_getChatroomActionsResponse_ == null) return null
        return GetChatroomActionsResponse(
            _getChatroomActionsResponse_.canAccessSecretChatroom,
            convertChatroomActions(_getChatroomActionsResponse_.chatroomActions),
            _getChatroomActionsResponse_.participantCount,
            _getChatroomActionsResponse_.placeHolder
        )
    }

    // converts internal ChatroomAction model list to client model list
    private fun convertChatroomActions(
        _chatroomActions_: List<_ChatroomAction_>
    ): List<ChatroomAction> {
        return _chatroomActions_.map {
            convertChatroomAction(it)
        }
    }

    // converts internal ChatroomAction model to client model
    private fun convertChatroomAction(
        _chatroomAction_: _ChatroomAction_
    ): ChatroomAction {
        return ChatroomAction(
            _chatroomAction_.id,
            _chatroomAction_.title,
            _chatroomAction_.route
        )
    }

    // converts api GetChatroomParticipantsResponse model to LM GetChatroomParticipantsResponse model
    fun convertGetParticipantsAPIResponse(
        apiResponse: APIResponse<_GetParticipantsResponse_>
    ): LMResponse<GetParticipantsResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetParticipantsResponse(apiResponse.data)
        )
    }

    // converts internal GetParticipantsResponse model to client model
    private fun convertGetParticipantsResponse(
        _getParticipantsResponse_: _GetParticipantsResponse_?
    ): GetParticipantsResponse? {
        if (_getParticipantsResponse_ == null) return null
        return GetParticipantsResponse(
            _getParticipantsResponse_.canEditParticipant,
            convertMembers(_getParticipantsResponse_.participants),
            _getParticipantsResponse_.totalParticipantsCount
        )
    }

    // converts api GetReportTagsResponse model to LM GetReportTagsResponse model
    fun convertGetReportTagsAPIResponse(
        apiResponse: APIResponse<_GetReportTagsResponse_>
    ): LMResponse<GetReportTagsResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetReportTagsResponse(apiResponse.data)
        )
    }

    // converts internal GetReportTagsResponse model to client model
    private fun convertGetReportTagsResponse(
        _getReportTagsResponse_: _GetReportTagsResponse_?
    ): GetReportTagsResponse? {
        if (_getReportTagsResponse_ == null) {
            return null
        }
        return GetReportTagsResponse(
            convertReportTags(_getReportTagsResponse_.tags)
        )
    }

    // converts internal ReportTag model list to client model list
    private fun convertReportTags(
        _tags_: List<_ReportTag_>
    ): List<ReportTag> {
        return _tags_.map {
            convertReportTag(it)
        }
    }

    // converts internal ReportTag model to client model
    private fun convertReportTag(
        _reportTag_: _ReportTag_
    ): ReportTag {
        return ReportTag(
            _reportTag_.id,
            _reportTag_.name
        )
    }

    // converts api AddPollOptionResponse model to LM AddPollOptionResponse model
    fun convertAddPollOptionAPIResponse(
        apiResponse: APIResponse<_AddPollOptionResponse_>
    ): LMResponse<AddPollOptionResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertAddPollOptionResponse(apiResponse.data)
        )
    }

    // converts internal AddPollOptionResponse model to client model
    private fun convertAddPollOptionResponse(
        _addPollOptionResponse_: _AddPollOptionResponse_?
    ): AddPollOptionResponse? {
        if (_addPollOptionResponse_ == null) return null
        return AddPollOptionResponse(convertPoll(_addPollOptionResponse_.poll))
    }

    // converts api GetPollUsersResponse model to LM GetPollUsersResponse model
    fun convertGetPollUsersAPIResponse(
        apiResponse: APIResponse<_GetPollUsersResponse_>
    ): LMResponse<GetPollUsersResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetPollUsersResponse(apiResponse.data)
        )
    }

    // converts internal GetPollUsersResponse model to client model
    private fun convertGetPollUsersResponse(
        _getPollUsersResponse_: _GetPollUsersResponse_?
    ): GetPollUsersResponse? {
        if (_getPollUsersResponse_ == null) return null
        return GetPollUsersResponse(convertMembers(_getPollUsersResponse_.members))
    }

    // converts api PostPollConversationResponse model to LM PostPollConversationResponse model
    fun convertPostPollConversationAPIResponse(
        apiResponse: APIResponse<_PostPollConversationResponse_>
    ): LMResponse<PostPollConversationResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertPostPollConversationResponse(apiResponse.data)
        )
    }

    // converts internal PostPollConversationResponse model to client model
    private fun convertPostPollConversationResponse(
        _postPollConversationResponse_: _PostPollConversationResponse_?
    ): PostPollConversationResponse? {
        if (_postPollConversationResponse_ == null) return null
        return PostPollConversationResponse(
            _postPollConversationResponse_.id,
            convertConversation(_postPollConversationResponse_.conversation)
        )
    }

    // converts internal Poll model list to client model list
    private fun convertPolls(
        _polls_: List<_Poll_>?
    ): List<Poll>? {
        if (_polls_ == null) return null
        return _polls_.map {
            convertPoll(it)
        }
    }

    // converts internal Poll model to client model
    private fun convertPoll(
        _poll_: _Poll_
    ): Poll {
        return Poll.Builder()
            .id(_poll_.id)
            .text(_poll_.text)
            .isSelected(_poll_.isSelected)
            .percentage(_poll_.percentage)
            .subText(_poll_.subText)
            .noVotes(_poll_.noVotes)
            .member(convertMember(_poll_.member))
            .userId(_poll_.userId)
            .build()
    }

    // converts api SearchChatroomResponse model to LM SearchChatroomResponse model
    fun convertSearchChatroomAPIResponse(
        apiResponse: APIResponse<_SearchChatroomResponse_>
    ): LMResponse<SearchChatroomResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertSearchChatroomResponse(apiResponse.data)
        )
    }

    // converts internal SearchChatroomResponse model to client model
    private fun convertSearchChatroomResponse(
        _searchChatroomResponse_: _SearchChatroomResponse_?
    ): SearchChatroomResponse? {
        if (_searchChatroomResponse_ == null) return null
        return SearchChatroomResponse(convertSearchChatrooms(_searchChatroomResponse_.conversations))
    }

    // converts internal SearchChatroom model list to client model list
    private fun convertSearchChatrooms(
        _chatrooms_: List<_SearchChatroom_>
    ): List<SearchChatroom> {
        return _chatrooms_.map {
            convertSearchChatroom(it)
        }
    }

    // converts internal SearchChatroom model to client model
    private fun convertSearchChatroom(
        _chatroom_: _SearchChatroom_
    ): SearchChatroom {
        return SearchChatroom(
            convertAttachments(_chatroom_.attachments) ?: listOf(),
            _chatroom_.attendingStatus,
            convertChatroom(_chatroom_.chatroom),
            convertCommunity(_chatroom_.community),
            _chatroom_.followStatus,
            _chatroom_.id,
            _chatroom_.isGuest,
            _chatroom_.isTagged,
            convertMember(_chatroom_.member),
            _chatroom_.muteStatus,
            _chatroom_.secretChatroomLeft,
            _chatroom_.state,
            _chatroom_.updatedAt,
            _chatroom_.isDisabled
        )
    }

    // converts api SearchConversationResponse model to LM SearchConversationResponse model
    fun convertSearchConversationAPIResponse(
        apiResponse: APIResponse<_SearchConversationResponse_>
    ): LMResponse<SearchConversationResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertSearchConversationResponse(apiResponse.data)
        )
    }

    // converts internal SearchConversationResponse model to client model
    private fun convertSearchConversationResponse(
        _searchConversationResponse_: _SearchConversationResponse_?
    ): SearchConversationResponse? {
        if (_searchConversationResponse_ == null) return null
        return SearchConversationResponse(convertSearchConversations(_searchConversationResponse_.conversations))
    }

    // converts internal SearchConversation model list to client model list
    private fun convertSearchConversations(
        _conversations_: List<_SearchConversation_>
    ): List<SearchConversation> {
        return _conversations_.map {
            convertSearchConversation(it)
        }
    }

    // converts internal SearchConversation model to client model
    private fun convertSearchConversation(
        _conversation_: _SearchConversation_
    ): SearchConversation {
        return SearchConversation(
            _conversation_.answer,
            _conversation_.attachmentCount,
            convertAttachments(_conversation_.attachments) ?: listOf(),
            _conversation_.attachmentsUploaded,
            convertChatroom(_conversation_.chatroom),
            convertCommunity(_conversation_.community),
            _conversation_.createdAt,
            _conversation_.id,
            _conversation_.isDeleted,
            _conversation_.isEdited,
            _conversation_.lastUpdated,
            convertMember(_conversation_.member),
            _conversation_.state,
        )
    }

    // converts api GetExploreFeedResponse model to LM GetExploreFeedResponse model
    fun convertGetExploreFeedAPIResponse(
        apiResponse: APIResponse<_GetExploreFeedResponse_>
    ): LMResponse<GetExploreFeedResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetExploreFeedResponse(apiResponse.data)
        )
    }

    // converts internal GetExploreFeedResponse model to client model
    private fun convertGetExploreFeedResponse(
        _getExploreFeedResponse_: _GetExploreFeedResponse_?
    ): GetExploreFeedResponse? {
        if (_getExploreFeedResponse_ == null) return null
        return GetExploreFeedResponse(
            convertChatrooms(_getExploreFeedResponse_.chatrooms),
            _getExploreFeedResponse_.pinnedChatroomCount
        )
    }

    // converts api GetConversationNotificationUnreadResponse model to LM GetConversationNotificationUnreadResponse model
    fun convertGetConversationNotificationUnreadResponse(
        apiResponse: APIResponse<_GetConversationNotificationUnreadResponse_>
    ): LMResponse<GetConversationNotificationUnreadResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetConversationNotificationUnreadResponse(apiResponse.data)
        )
    }

    // converts internal GetConversationNotificationUnreadResponse model to client model
    private fun convertGetConversationNotificationUnreadResponse(
        _getConversationNotificationUnreadResponse_: _GetConversationNotificationUnreadResponse_?
    ): GetConversationNotificationUnreadResponse? {
        if (_getConversationNotificationUnreadResponse_ == null) {
            return null
        }
        return GetConversationNotificationUnreadResponse(
            convertChatroomNotificationDataList(_getConversationNotificationUnreadResponse_.unreadConversation)
        )
    }

    // converts internal ChatroomNotificationData model list to client model list
    private fun convertChatroomNotificationDataList(
        _unreadConversation_: List<_ChatroomNotificationData_>
    ): List<ChatroomNotificationData> {
        return _unreadConversation_.map {
            convertChatroomNotificationData(it)
        }
    }

    // converts internal ChatroomNotificationData model to client model
    private fun convertChatroomNotificationData(
        _chatroomNotificationData_: _ChatroomNotificationData_
    ): ChatroomNotificationData {
        return ChatroomNotificationData(
            _chatroomNotificationData_.communityName,
            _chatroomNotificationData_.chatroomName,
            _chatroomNotificationData_.chatroomTitle,
            _chatroomNotificationData_.chatroomUserName,
            _chatroomNotificationData_.chatroomUserImage,
            _chatroomNotificationData_.chatroomId,
            _chatroomNotificationData_.communityImage,
            _chatroomNotificationData_.communityId,
            _chatroomNotificationData_.route,
            _chatroomNotificationData_.chatroomUnreadConversationCount,
            _chatroomNotificationData_.chatroomLastConversation,
            _chatroomNotificationData_.chatroomLastConversationUserName,
            _chatroomNotificationData_.chatroomLastConversationUserImage,
            _chatroomNotificationData_.routeChild,
            _chatroomNotificationData_.chatroomLastConversationUserTimestamp,
            convertAttachments(_chatroomNotificationData_.attachments),
            _chatroomNotificationData_.sortKey
        )
    }

    // converts internal Member model list to client model list
    private fun convertMembers(
        _members_: List<_Member_>
    ): List<Member> {
        return _members_.map {
            convertMember(it)
        }
    }

    // convert internal Member model to client model
    private fun convertMember(
        _member_: _Member_?
    ): Member {
        if (_member_ == null) {
            return Member.Builder().build()
        }
        return Member.Builder()
            .id(_member_.id)
            .userUniqueId(_member_.userUniqueId)
            .name(_member_.name)
            .imageUrl(_member_.imageUrl ?: "")
            .questionAnswers(convertQuestions(_member_.questionAnswers))
            .state(_member_.state)
            .isGuest(_member_.isGuest)
            .customIntroText(_member_.customIntroText)
            .customClickText(_member_.customClickText)
            .memberSince(_member_.memberSince)
            .communityName(_member_.communityName)
            .isOwner(_member_.isOwner)
            .customTitle(_member_.customTitle)
            .menu(convertMemberActionMenus(_member_.menu))
            .communityId(_member_.communityId)
            .chatroomId(_member_.chatroomId)
            .route(_member_.route)
            .attendingStatus(_member_.attendingStatus)
            .hasProfileImage(_member_.hasProfileImage)
            .updatedAt(_member_.updatedAt)
            .sdkClientInfo(convertSDKClientInfo(_member_.sdkClientInfo))
            .uuid(_member_.uuid)
            .build()
    }

    // converts internal Question model list to client model list
    private fun convertQuestions(
        _questions_: List<_Question_>?
    ): List<Question>? {
        if (_questions_ == null) return null
        return _questions_.map {
            convertQuestion(it)
        }
    }

    // converts internal Question model to client model
    private fun convertQuestion(
        _question_: _Question_
    ): Question {
        return Question.Builder()
            .id(_question_.id)
            .questionTitle(_question_.questionTitle)
            .state(_question_.state)
            .value(_question_.value)
            .optional(_question_.optional)
            .helpText(_question_.helpText)
            .field(_question_.field)
            .isCompulsory(_question_.isCompulsory)
            .isHidden(_question_.isHidden)
            .communityId(_question_.communityId)
            .memberId(_question_.memberId)
            .directoryFields(_question_.directoryFields)
            .imageUrl(_question_.imageUrl)
            .canAddOtherOptions(_question_.canAddOtherOptions)
            .questionChangeState(_question_.questionChangeState)
            .isAnswerEditable(_question_.isAnswerEditable)
            .build()
    }

    // converts internal MemberAction model list to client model list
    private fun convertMemberActionMenus(
        _memberActions_: List<_MemberAction_>?
    ): List<MemberAction>? {
        if (_memberActions_ == null) return null
        return _memberActions_.map {
            convertMemberActionMenu(it)
        }
    }

    // converts internal MemberAction model to client model
    private fun convertMemberActionMenu(
        _memberAction_: _MemberAction_
    ): MemberAction {
        return MemberAction(
            _memberAction_.title,
            _memberAction_.route
        )
    }

    // converts internal Chatroom model list to client model list
    private fun convertChatrooms(
        _chatrooms_: List<_Chatroom_>
    ): List<Chatroom> {
        return _chatrooms_.map {
            convertChatroom(it)
        }
    }

    // converts internal Chatroom model to client model
    private fun convertChatroom(
        _chatroom_: _Chatroom_
    ): Chatroom {
        return Chatroom.Builder()
            .member(convertMember(_chatroom_.member))
            .id(_chatroom_.id)
            .title(_chatroom_.title)
            .createdAt(_chatroom_.createdAt)
            .answerText(_chatroom_.answerText)
            .state(_chatroom_.state)
            .unseenCount(_chatroom_.unseenCount)
            .shareUrl(_chatroom_.shareUrl)
            .communityId(_chatroom_.communityId)
            .communityName(_chatroom_.communityName)
            .type(_chatroom_.type)
            .about(_chatroom_.about)
            .header(_chatroom_.header)
            .showFollowTelescope(_chatroom_.showFollowTelescope)
            .showFollowAutoTag(_chatroom_.showFollowAutoTag)
            .cardCreationTime(_chatroom_.cardCreationTime)
            .participantsCount(_chatroom_.participantsCount)
            .totalResponseCount(_chatroom_.totalResponseCount?.toInt() ?: 0)
            .muteStatus(_chatroom_.muteStatus)
            .followStatus(_chatroom_.followStatus)
            .hasBeenNamed(_chatroom_.hasBeenNamed)
            .hasReactions(_chatroom_.hasReactions)
            .date(_chatroom_.date)
            .isTagged(_chatroom_.isTagged)
            .isPending(_chatroom_.isPending)
            .isPinned(_chatroom_.isPinned)
            .isDeleted(_chatroom_.isDeleted)
            .userId(_chatroom_.userId)
            .deletedBy(_chatroom_.deletedBy)
            .deletedByMember(convertMember(_chatroom_.deletedByMember))
            .updatedAt(_chatroom_.updatedAt)
            .lastSeenConversationId(_chatroom_.lastSeenConversationId)
            .lastConversationId(_chatroom_.lastConversationId)
            .dateEpoch(_chatroom_.dateEpoch)
            .isSecret(_chatroom_.isSecret)
            .secretChatroomParticipants(_chatroom_.secretChatroomParticipants)
            .secretChatroomLeft(_chatroom_.secretChatroomLeft)
            .reactions(convertReactions(_chatroom_.reactions))
            .topicId(_chatroom_.topicId)
            .topic(_chatroom_.topic?.let {
                convertConversation(it)
            })
            .autoFollowDone(_chatroom_.autoFollowDone)
            .isEdited(_chatroom_.isEdited)
            .memberCanMessage(_chatroom_.memberCanMessage)
            .cohorts(convertCohorts(_chatroom_.cohorts))
            .externalSeen(_chatroom_.externalSeen)
            .unreadConversationCount(_chatroom_.unreadConversationCount)
            .chatroomImageUrl(_chatroom_.chatroomImageUrl)
            .accessWithoutSubscription(_chatroom_.accessWithoutSubscription)
            .build()
    }

    // converts internal Cohort model list to client model list
    private fun convertCohorts(
        _cohorts_: List<_Cohort_>?
    ): List<Cohort>? {
        return _cohorts_?.map {
            convertCohort(it)
        }
    }

    // converts internal Cohort model to client model
    private fun convertCohort(
        _cohort_: _Cohort_
    ): Cohort {
        return Cohort.Builder()
            .id(_cohort_.id)
            .totalMembers(_cohort_.totalMembers)
            .name(_cohort_.name)
            .members(_cohort_.members?.let {
                convertMembers(it)
            })
            .build()
    }

    // converts list of internal Conversation model to client model
    private fun convertConversations(conversations: List<_Conversation_>): List<Conversation> {
        return conversations.map { conversation ->
            convertConversation(conversation)
        }
    }

    // converts internal Conversation model to client model
    private fun convertConversation(
        _conversation_: _Conversation_
    ): Conversation {
        return Conversation.Builder()
            .id(_conversation_.id)
            .chatroomId(_conversation_.chatroomId)
            .communityId(_conversation_.communityId)
            .member(convertMember(_conversation_.member))
            .answer(_conversation_.answer)
            .createdAt(_conversation_.createdAt)
            .state(_conversation_.state)
            .attachments(convertAttachments(_conversation_.attachments))
            .lastSeen(_conversation_.lastSeen)
            .ogTags(_conversation_.ogTags?.let { convertOGTags(it) })
            .date(_conversation_.date)
            .isEdited(_conversation_.isEdited)
            .memberId(_conversation_.memberId)
            .replyConversationId(_conversation_.replyConversationId)
            .deletedBy(_conversation_.deletedBy)
            .createdEpoch(_conversation_.createdEpoch)
            .attachmentCount(_conversation_.attachmentCount)
            .attachmentUploaded(_conversation_.attachmentUploaded)
            .uploadWorkerUUID(_conversation_.uploadWorkerUUID)
            .temporaryId(_conversation_.temporaryId)
            .localCreatedEpoch(_conversation_.localCreatedEpoch)
            .reactions(convertReactions(_conversation_.reactions))
            .isAnonymous(_conversation_.isAnonymous)
            .allowAddOption(_conversation_.allowAddOption)
            .pollType(_conversation_.pollType)
            .pollTypeText(_conversation_.pollTypeText)
            .submitTypeText(_conversation_.submitTypeText)
            .expiryTime(_conversation_.expiryTime)
            .multipleSelectNum(_conversation_.multipleSelectNum)
            .multipleSelectState(_conversation_.multipleSelectState)
            .polls(convertPolls(_conversation_.polls))
            .toShowResults(_conversation_.toShowResults)
            .pollAnswerText(_conversation_.pollAnswerText)
            .replyChatroomId(_conversation_.replyChatroomId)
            .deviceId(_conversation_.deviceId)
            .hasFiles(_conversation_.hasFiles)
            .hasReactions(_conversation_.hasReactions)
            .lastUpdated(_conversation_.lastUpdated)
            .build()
    }

    // converts internal Attachment model list to client model list
    private fun convertAttachments(
        _attachments_: List<_Attachment_>?
    ): List<Attachment>? {
        if (_attachments_ == null) return null
        return _attachments_.map {
            convertAttachment(it)
        }
    }

    // converts internal Attachment model to client model
    private fun convertAttachment(
        _attachment_: _Attachment_
    ): Attachment {
        return Attachment.Builder()
            .id(_attachment_.id)
            .name(_attachment_.name)
            .url(_attachment_.url)
            .type(_attachment_.type)
            .index(_attachment_.index)
            .width(_attachment_.width)
            .height(_attachment_.height)
            .awsFolderPath(_attachment_.awsFolderPath)
            .localFilePath(_attachment_.localFilePath)
            .thumbnailUrl(_attachment_.thumbnailUrl)
            .thumbnailAWSFolderPath(_attachment_.thumbnailAWSFolderPath)
            .thumbnailLocalFilePath(_attachment_.thumbnailLocalFilePath)
            .meta(convertAttachmentMeta(_attachment_.meta))
            .createdAt(_attachment_.createdAt)
            .updatedAt(_attachment_.updatedAt)
            .build()
    }

    // converts internal AttachmentMeta model to client model
    private fun convertAttachmentMeta(
        _attachmentMeta_: _AttachmentMeta_?
    ): AttachmentMeta? {
        if (_attachmentMeta_ == null) return null
        return AttachmentMeta.Builder()
            .numberOfPage(_attachmentMeta_.numberOfPage)
            .size(_attachmentMeta_.size)
            .duration(_attachmentMeta_.duration)
            .build()
    }

    // converts internal Reaction model list to client model list
    private fun convertReactions(
        _reactions_: List<_Reaction_>?
    ): List<Reaction>? {
        if (_reactions_ == null) return null
        return _reactions_.map {
            convertReaction(it)
        }
    }

    // converts internal Reaction model to client model
    private fun convertReaction(
        _reaction_: _Reaction_
    ): Reaction {
        return Reaction.Builder()
            .member(convertMember(_reaction_.member))
            .reaction(_reaction_.reaction)
            .build()
    }

    // converts api DecodeUrlResponse model to LM DecodeUrlResponse model
    fun convertDecodeUrlAPIResponse(
        apiResponse: APIResponse<_DecodeUrlResponse_>
    ): LMResponse<DecodeUrlResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertDecodeUrlResponse(apiResponse.data)
        )
    }

    // converts internal DecodeUrlResponse model to client model
    private fun convertDecodeUrlResponse(
        _decodeUrlResponse_: _DecodeUrlResponse_?
    ): DecodeUrlResponse? {
        if (_decodeUrlResponse_ == null) {
            return null
        }
        return DecodeUrlResponse(convertOGTags(_decodeUrlResponse_.ogTags))
    }

    // converts internal LinkOGTags model to client model
    private fun convertOGTags(
        _ogTags_: _LinkOGTags_
    ): LinkOGTags {
        return LinkOGTags.Builder()
            .title(_ogTags_.title)
            .image(_ogTags_.image)
            .description(_ogTags_.description)
            .url(_ogTags_.url)
            .build()
    }

    // converts api GetTaggingListResponse model to LM GetTaggingListResponse model
    fun convertGetTaggingListAPIResponse(
        apiResponse: APIResponse<_GetTaggingListResponse_>
    ): LMResponse<GetTaggingListResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetTaggingListResponse(apiResponse.data)
        )
    }

    // converts internal GetTaggingListResponse model to client model
    private fun convertGetTaggingListResponse(
        _getTaggingListResponse_: _GetTaggingListResponse_?
    ): GetTaggingListResponse? {
        if (_getTaggingListResponse_ == null) {
            return null
        }
        return GetTaggingListResponse(
            convertGroupTags(_getTaggingListResponse_.groupTags),
            convertChatroomParticipants(_getTaggingListResponse_.chatroomParticipants),
            convertCommunityMembers(_getTaggingListResponse_.communityMembers),
        )
    }

    // converts internal GroupTag model list to client model list
    private fun convertGroupTags(
        _groupTags_: List<_GroupTag_>
    ): List<GroupTag> {
        return _groupTags_.map {
            convertGroupTag(it)
        }
    }

    // converts internal GroupTag model to client model
    private fun convertGroupTag(
        _groupTag_: _GroupTag_
    ): GroupTag {
        return GroupTag(
            _groupTag_.description,
            _groupTag_.name,
            _groupTag_.route,
            _groupTag_.tag,
            _groupTag_.imageUrl,
        )
    }

    // converts internal chatroomParticipants list to client model list
    private fun convertChatroomParticipants(
        _chatroomParticipants_: List<_Member_>
    ): List<Member> {
        return _chatroomParticipants_.map {
            convertMember(it)
        }
    }

    // converts internal communityMembers list to client model list
    private fun convertCommunityMembers(
        _communityMembers_: List<_Member_>
    ): List<Member> {
        return _communityMembers_.map {
            convertMember(it)
        }
    }

    //converts API PostConversationResponse model to LM model
    fun convertPostConversationAPIResponse(
        apiResponse: APIResponse<_PostConversationResponse_>
    ): LMResponse<PostConversationResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertPostConversationResponse(apiResponse.data)
        )
    }

    //converts internal PostConversationResponse model to client model
    private fun convertPostConversationResponse(
        _postConversationResponse_: _PostConversationResponse_?
    ): PostConversationResponse? {
        if (_postConversationResponse_ == null) return null
        return PostConversationResponse(
            convertConversation(_postConversationResponse_.conversation),
            _postConversationResponse_.id
        )
    }

    //converts API EditConversationResponse model to LM model
    fun convertEditConversationAPIResponse(
        apiResponse: APIResponse<_EditConversationResponse_>
    ): LMResponse<EditConversationResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertEditConversationResponse(apiResponse.data)
        )
    }

    //converts internal EditConversationResponse model to client model
    private fun convertEditConversationResponse(_editConversationResponse_: _EditConversationResponse_?): EditConversationResponse? {
        if (_editConversationResponse_ == null) return null
        return EditConversationResponse(convertConversation(_editConversationResponse_.conversation))
    }

    //converts API DeleteConversationResponse model to LM model
    fun convertDeleteConversationsAPIResponse(
        apiResponse: APIResponse<_DeleteConversationsResponse_>
    ): LMResponse<DeleteConversationsResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertDeleteConversationsResponse(apiResponse.data)
        )
    }

    //converts internal DeleteConversationResponse model to client model
    private fun convertDeleteConversationsResponse(_deleteConversationsResponse_: _DeleteConversationsResponse_?): DeleteConversationsResponse? {
        if (_deleteConversationsResponse_ == null) return null
        return DeleteConversationsResponse(
            convertConversations(_deleteConversationsResponse_.conversations)
        )
    }

    //converts API PutMultimediaResponse model to LM model
    fun convertPutMultimediaAPIResponse(apiResponse: APIResponse<_PutMultimediaResponse_>): LMResponse<PutMultimediaResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertPutMultimediaResponse(apiResponse.data)
        )
    }

    //converts internal PutMultimediaResponse model to client model
    private fun convertPutMultimediaResponse(data: _PutMultimediaResponse_?): PutMultimediaResponse {
        return PutMultimediaResponse(data?.conversation?.let { convertConversation(it) })
    }

    // converts api MemberStateResponse model to LM MemberStateResponse model
    fun convertMemberStateResponse(
        apiResponse: APIResponse<_MemberStateResponse_>
    ): LMResponse<MemberStateResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertMemberStateResponse(apiResponse.data)
        )
    }

    // converts internal MemberStateResponse model to client model
    private fun convertMemberStateResponse(
        _memberStateResponse_: _MemberStateResponse_?
    ): MemberStateResponse? {
        val member = _memberStateResponse_?.member
        if (_memberStateResponse_ == null || member == null) return null
        return MemberStateResponse(
            member.id,
            _memberStateResponse_.state,
            member.userUniqueId,
            member.customTitle,
            member.imageUrl ?: "",
            member.isGuest,
            member.isOwner,
            member.name,
            convertManagerRights(_memberStateResponse_.managerRights),
            convertMemberRights(_memberStateResponse_.memberRights),
            member.updatedAt ?: 0L,
            convertSDKClientInfo(member.sdkClientInfo)
        )
    }

    // converts internal ManagementRightPermissionData model list of manager rights to client model list
    private fun convertManagerRights(
        _rights_: List<_ManagementRightPermissionData_>?
    ): List<ManagementRightPermissionData>? {
        if (_rights_ == null) return null
        return _rights_.map {
            convertManagementRightPermission(it)
        }
    }

    // converts internal ManagementRightPermissionData model list of member rights client model list
    private fun convertMemberRights(
        _rights_: List<_ManagementRightPermissionData_>
    ): List<ManagementRightPermissionData> {
        return _rights_.map {
            convertManagementRightPermission(it)
        }
    }

    // converts internal ManagementRightPermissionData model to client model
    private fun convertManagementRightPermission(
        _right_: _ManagementRightPermissionData_
    ): ManagementRightPermissionData {
        return ManagementRightPermissionData(
            _right_.id,
            _right_.isLocked,
            _right_.isSelected,
            _right_.state,
            _right_.title,
            _right_.subtitle
        )
    }

    /**--------------------------------
     * Client Model -> Internal Model
    --------------------------------*/

    //create internal Conversation from client model
    fun createConversation(conversation: Conversation): _Conversation_ {
        return _Conversation_.Builder()
            .id(conversation.id)
            .chatroomId(conversation.chatroomId)
            .communityId(conversation.communityId)
            .member(createMember(conversation.member))
            .answer(conversation.answer)
            .createdAt(conversation.createdAt)
            .state(conversation.state)
            .attachments(createAttachments(conversation.attachments))
            .lastSeen(conversation.lastSeen)
            .ogTags(createLinkOGTags(conversation.ogTags))
            .date(conversation.date)
            .isEdited(conversation.isEdited)
            .memberId(conversation.memberId)
            .replyConversationId(conversation.replyConversationId)
            .deletedBy(conversation.deletedBy)
            .createdEpoch(conversation.createdEpoch)
            .attachmentCount(conversation.attachmentCount)
            .attachmentUploaded(conversation.attachmentUploaded)
            .uploadWorkerUUID(conversation.uploadWorkerUUID)
            .temporaryId(conversation.temporaryId)
            .localCreatedEpoch(conversation.localCreatedEpoch)
            .reactions(createReactions(conversation.reactions))
            .isAnonymous(conversation.isAnonymous)
            .allowAddOption(conversation.allowAddOption)
            .pollType(conversation.pollType)
            .pollTypeText(conversation.pollTypeText)
            .submitTypeText(conversation.submitTypeText)
            .expiryTime(conversation.expiryTime)
            .multipleSelectNum(conversation.multipleSelectNum)
            .multipleSelectState(conversation.multipleSelectState)
            .polls(createPolls(conversation.polls))
            .toShowResults(conversation.toShowResults)
            .pollAnswerText(conversation.pollAnswerText)
            .replyChatroomId(conversation.replyChatroomId)
            .deviceId(conversation.deviceId)
            .hasFiles(conversation.hasFiles)
            .hasReactions(conversation.hasReactions)
            .lastUpdated(conversation.lastUpdated)
            .build()
    }

    //create list of internal Reaction from client model
    private fun createReactions(reactions: List<Reaction>?): List<_Reaction_>? {
        if (reactions.isNullOrEmpty()) return null
        return reactions.map { reaction ->
            createReaction(reaction)
        }
    }

    //create internal Reaction from client model
    private fun createReaction(reaction: Reaction): _Reaction_ {
        return _Reaction_.Builder()
            .reaction(reaction.reaction)
            .member(createMember(reaction.member))
            .build()
    }

    // creates internal Poll model list from client model list
    fun createPolls(
        polls: List<Poll>?
    ): List<_Poll_>? {
        if (polls.isNullOrEmpty()) return null
        return polls.map {
            createPoll(it)
        }
    }

    // creates internal Poll model from client model
    fun createPoll(
        poll: Poll
    ): _Poll_ {
        return _Poll_.Builder()
            .id(poll.id)
            .text(poll.text)
            .isSelected(poll.isSelected)
            .percentage(poll.percentage)
            .subText(poll.subText)
            .noVotes(poll.noVotes)
            .member(createMember(poll.member))
            .userId(poll.userId)
            .build()
    }

    // creates internal Member model from client model
    private fun createMember(
        member: Member?
    ): _Member_? {
        if (member == null) return null
        return _Member_.Builder()
            .id(member.id)
            .userUniqueId(member.userUniqueId)
            .name(member.name)
            .imageUrl(member.imageUrl)
            .questionAnswers(createQuestions(member.questionAnswers))
            .state(member.state)
            .isGuest(member.isGuest)
            .customIntroText(member.customIntroText)
            .customClickText(member.customClickText)
            .memberSince(member.memberSince)
            .communityName(member.communityName)
            .isOwner(member.isOwner)
            .customTitle(member.customTitle)
            .menu(createMemberActionMenus(member.menu))
            .communityId(member.communityId)
            .chatroomId(member.chatroomId)
            .route(member.route)
            .attendingStatus(member.attendingStatus)
            .hasProfileImage(member.hasProfileImage)
            .updatedAt(member.updatedAt)
            .sdkClientInfo(createSDKClientInfo(member.sdkClientInfo))
            .uuid(member.uuid)
            .build()
    }

    // creates internal Question model list from client model list
    private fun createQuestions(
        questions: List<Question>?
    ): List<_Question_>? {
        if (questions == null) return null
        return questions.map {
            createQuestion(it)
        }
    }

    // creates internal Question model from client model
    private fun createQuestion(
        question: Question
    ): _Question_ {
        return _Question_.Builder()
            .id(question.id)
            .questionTitle(question.questionTitle)
            .state(question.state)
            .value(question.value)
            .optional(question.optional)
            .helpText(question.helpText)
            .field(question.field)
            .isCompulsory(question.isCompulsory)
            .isHidden(question.isHidden)
            .communityId(question.communityId)
            .memberId(question.memberId)
            .directoryFields(question.directoryFields)
            .imageUrl(question.imageUrl)
            .canAddOtherOptions(question.canAddOtherOptions)
            .questionChangeState(question.questionChangeState)
            .isAnswerEditable(question.isAnswerEditable)
            .build()
    }

    // creates internal MemberAction model list from client model list
    private fun createMemberActionMenus(
        memberActions: List<MemberAction>?
    ): List<_MemberAction_>? {
        if (memberActions == null) return null
        return memberActions.map {
            createMemberActionMenu(it)
        }
    }

    // creates internal MemberAction model from client model
    private fun createMemberActionMenu(
        memberAction: MemberAction
    ): _MemberAction_ {
        return _MemberAction_(
            memberAction.title,
            memberAction.route
        )
    }

    // creates internal LinkOGTags model from client model
    fun createLinkOGTags(linkOGTags: LinkOGTags?): _LinkOGTags_? {
        if (linkOGTags == null) return null
        return _LinkOGTags_.Builder()
            .url(linkOGTags.url)
            .title(linkOGTags.title)
            .image(linkOGTags.image)
            .description(linkOGTags.description)
            .build()
    }

    // creates internal Attachment model list from client model list
    private fun createAttachments(attachments: List<Attachment>?): List<_Attachment_>? {
        if (attachments.isNullOrEmpty()) return null
        return attachments.map {
            createAttachment(it)
        }
    }

    // creates internal Attachment model from client model
    private fun createAttachment(attachment: Attachment): _Attachment_ {
        return _Attachment_.Builder()
            .id(attachment.id)
            .name(attachment.name)
            .url(attachment.url)
            .type(attachment.type)
            .index(attachment.index)
            .width(attachment.width)
            .height(attachment.height)
            .awsFolderPath(attachment.awsFolderPath)
            .localFilePath(attachment.localFilePath)
            .thumbnailUrl(attachment.thumbnailUrl)
            .thumbnailAWSFolderPath(attachment.thumbnailAWSFolderPath)
            .thumbnailLocalFilePath(attachment.thumbnailLocalFilePath)
            .meta(createAttachmentMeta(attachment.meta))
            .createdAt(attachment.createdAt)
            .updatedAt(attachment.updatedAt)
            .build()
    }

    // creates internal AttachmentMeta model from client model
    fun createAttachmentMeta(attachmentMeta: AttachmentMeta?): _AttachmentMeta_? {
        if (attachmentMeta == null) return null
        return _AttachmentMeta_.Builder()
            .numberOfPage(attachmentMeta.numberOfPage)
            .duration(attachmentMeta.duration)
            .size(attachmentMeta.size)
            .build()
    }

    fun createSDKClientInfo(sdkClientInfo: SDKClientInfo?): _SDKClientInfo_? {
        if (sdkClientInfo == null) return null
        return _SDKClientInfo_(
            sdkClientInfo.community,
            sdkClientInfo.user,
            sdkClientInfo.userUniqueId,
            sdkClientInfo.uuid
        )
    }

    /**--------------------------------
     * Db Model -> Client Response Model
    --------------------------------*/

    //convert [UserRO] to [GetUserResponse]
    fun convertGetUserResponse(userRO: UserRO?): GetUserResponse {
        return GetUserResponse(convertUserRO(userRO))
    }

    //convert [MemberRO] to [GetMemberResponse]
    fun convertGetMemberResponse(memberRO: MemberRO?): GetMemberResponse {
        return GetMemberResponse(convertMemberRO(memberRO))
    }

    // convert [chatroomRO] to [GetChatroomResponse]
    fun convertGetChatroomResponse(chatroomRO: ChatroomRO?): GetChatroomResponse {
        return GetChatroomResponse(convertChatroomRO(chatroomRO))
    }

    //convert [ConversationRO] to [GetConversationResponse]
    fun convertGetConversationResponse(conversationRO: ConversationRO?): GetConversationResponse {
        return GetConversationResponse(convertConversationRO(conversationRO))
    }

    //convert list of [ConversationRO] to [GetConversationsResponse]
    fun convertGetConversationsResponse(conversationsRO: List<ConversationRO>): GetConversationsResponse {
        return GetConversationsResponse(
            convertConversationsRO(conversationsRO.toList()),
            conversationsRO.count()
        )
    }

    //convert list of [ConversationRO] to [GetConversationsResponse]
    fun convertGetConversationsCountResponse(count: Int): GetConversationsCountResponse {
        return GetConversationsCountResponse(count)
    }

    /**--------------------------------
     * Db Model -> Client Model
    --------------------------------*/

    // converts UserRO model to client model
    private fun convertUserRO(userRO: UserRO?): User? {
        if (userRO == null) return null
        return User(
            userRO.id,
            userRO.imageUrl,
            userRO.isGuest,
            userRO.name,
            userRO.organizationName,
            convertSDKClientInfoRO(userRO.sdkClientInfoRO),
            userRO.isDeleted,
            userRO.customTitle,
            userRO.updatedAt,
            userRO.userUniqueId,
            userRO.uuid
        )
    }

    // converts SDKClientInfoRO model to client model
    private fun convertSDKClientInfoRO(sdkClientInfoRO: SDKClientInfoRO?): SDKClientInfo? {
        return sdkClientInfoRO?.let {
            SDKClientInfo(
                it.community,
                it.user,
                it.userUniqueId,
                it.uuid
            )
        }
    }

    // converts ChatroomRO model to client model
    fun convertChatroomRO(chatroomRO: ChatroomRO?): Chatroom? {
        if (chatroomRO == null) return null
        return Chatroom.Builder()
            .id(chatroomRO.id)
            .member(convertMemberRO(chatroomRO.member))
            .communityId(chatroomRO.communityId)
            .communityName(chatroomRO.communities?.firstOrNull()?.name)
            .title(chatroomRO.title)
            .state(chatroomRO.state)
            .createdAt(chatroomRO.createdAt)
            .type(chatroomRO.type)
            .chatroomImageUrl(chatroomRO.chatroomImageUrl)
            .header(chatroomRO.header)
            .cardCreationTime(chatroomRO.cardCreationTime)
            .totalResponseCount(chatroomRO.totalResponseCount)
            .muteStatus(chatroomRO.muteStatus)
            .followStatus(chatroomRO.followStatus)
            .hasBeenNamed(chatroomRO.hasBeenNamed)
            .date(chatroomRO.date)
            .isTagged(chatroomRO.isTagged)
            .isPending(chatroomRO.isPending)
            .deletedBy(chatroomRO.deletedBy)
            .updatedAt(chatroomRO.updatedAt)
            .lastConversationId(chatroomRO.lastConversationId)
            .lastConversation(
                convertConversationRO(chatroomRO.lastConversation)
                    ?: convertLastConversationRO(chatroomRO.lastConversationRO)
            )
            .lastSeenConversationId(chatroomRO.lastSeenConversationId)
            .lastSeenConversation(convertConversationRO(chatroomRO.lastSeenConversation))
            .dateEpoch(chatroomRO.dateEpoch)
            .unseenCount(chatroomRO.unseenCount)
            .draftConversation(chatroomRO.draftConversation)
            .isSecret(chatroomRO.isSecret)
            .secretChatroomParticipants(chatroomRO.secretChatRoomParticipants.toList())
            .secretChatroomLeft(chatroomRO.secretChatRoomLeft)
            .topicId(chatroomRO.topicId)
            .topic(convertConversationRO(chatroomRO.topic))
            .autoFollowDone(chatroomRO.autoFollowDone)
            .memberCanMessage(chatroomRO.memberCanMessage)
            .isEdited(chatroomRO.isEdited)
            .reactions(convertReactionsRO(chatroomRO.reactions))
            .unreadConversationCount(chatroomRO.unreadConversationsCount)
            .accessWithoutSubscription(chatroomRO.accessWithoutSubscription)
            .externalSeen(chatroomRO.externalSeen)
            .isConversationStored(chatroomRO.isConversationStored)
            .isDraft(chatroomRO.isDraft)
            .totalAllResponseCount(chatroomRO.totalAllResponseCount)
            .build()
    }

    // converts LastConversationRO model to client model
    private fun convertLastConversationRO(lastConversationRO: LastConversationRO?): Conversation? {
        if (lastConversationRO == null) return null
        return Conversation.Builder()
            .id(lastConversationRO.id)
            .member(convertMemberRO(lastConversationRO.member))
            .createdAt(lastConversationRO.createdAt)
            .answer(lastConversationRO.answer)
            .state(lastConversationRO.state)
            .attachments(convertAttachmentsRO(lastConversationRO.attachments))
            .date(lastConversationRO.date)
            .deletedBy(lastConversationRO.deletedBy)
            .attachmentCount(lastConversationRO.attachmentCount)
            .attachmentUploaded(lastConversationRO.attachmentsUploaded)
            .uploadWorkerUUID(lastConversationRO.uploadWorkerUUID)
            .createdEpoch(lastConversationRO.createdEpoch)
            .chatroomId(lastConversationRO.chatroomId)
            .communityId(lastConversationRO.communityId)
            .ogTags(convertLinkRO(lastConversationRO.link))
            .build()
    }

    // converts list of ConversationRO model to client model
    private fun convertConversationsRO(conversationsRO: List<ConversationRO>?): List<Conversation>? {
        return conversationsRO?.mapNotNull {
            convertConversationRO(it)
        }
    }

    // converts ConversationRO model to client model
    fun convertConversationRO(conversationRO: ConversationRO?): Conversation? {
        if (conversationRO == null) return null
        return Conversation.Builder()
            .id(conversationRO.id)
            .chatroomId(conversationRO.chatroomId)
            .communityId(conversationRO.communityId)
            .member(convertMemberRO(conversationRO.member))
            .answer(conversationRO.answer)
            .state(conversationRO.state)
            .createdAt(conversationRO.createdAt)
            .createdEpoch(conversationRO.createdEpoch)
            .attachments(convertAttachmentsRO(conversationRO.attachments.toList()))
            .ogTags(convertLinkRO(conversationRO.link))
            .date(conversationRO.date)
            .isEdited(conversationRO.isEdited)
            .lastSeen(conversationRO.lastSeen)
            .replyConversationId(conversationRO.replyConversationId)
            .replyConversation(convertConversationRO(conversationRO.replyConversation))
            .deletedBy(conversationRO.deletedBy)
            .attachmentCount(conversationRO.attachmentCount)
            .attachmentUploaded(conversationRO.attachmentsUploaded)
            .uploadWorkerUUID(conversationRO.uploadWorkerUUID)
            .temporaryId(conversationRO.temporaryId)
            .reactions(convertReactionsRO(conversationRO.reactions.toList()))
            .isAnonymous(conversationRO.isAnonymous)
            .allowAddOption(conversationRO.allowAddOption)
            .pollType(conversationRO.pollType)
            .pollTypeText(conversationRO.pollTypeText)
            .submitTypeText(conversationRO.submitTypeText)
            .expiryTime(conversationRO.expiryTime)
            .multipleSelectNum(conversationRO.multipleSelectNum)
            .multipleSelectState(conversationRO.multipleSelectState)
            .polls(convertPollsRO(conversationRO.polls.toList()))
            .pollAnswerText(conversationRO.pollAnswerText)
            .toShowResults(conversationRO.toShowResults)
            .replyChatroomId(conversationRO.replyChatRoomId)
            .lastUpdated(conversationRO.lastUpdatedAt)
            .deletedByMember(convertMemberRO(conversationRO.deletedByMember))
            .build()
    }

    // converts MemberRO model to client model
    private fun convertMemberRO(memberRO: MemberRO?): Member? {
        if (memberRO == null) return null
        return Member.Builder()
            .id(memberRO.id)
            .userUniqueId(memberRO.userUniqueId)
            .name(memberRO.name)
            .imageUrl(memberRO.imageUrl)
            .state(memberRO.state)
            .customIntroText(memberRO.customIntroText)
            .customClickText(memberRO.customClickText)
            .customTitle(memberRO.customTitle)
            .communityId(memberRO.communityId)
            .isOwner(memberRO.isOwner)
            .isGuest(memberRO.isGuest)
            .sdkClientInfo(convertSDKClientInfoRO(memberRO.sdkClientInfoRO))
            .uuid(memberRO.uuid)
            .build()
    }

    // converts list of AttachmentRO model to client model
    private fun convertAttachmentsRO(attachmentsRO: List<AttachmentRO>?): List<Attachment>? {
        if (attachmentsRO.isNullOrEmpty()) return null
        return attachmentsRO.map { attachmentRO ->
            convertAttachmentRO(attachmentRO)
        }
    }

    // converts AttachmentRO model to client model
    private fun convertAttachmentRO(attachmentRO: AttachmentRO): Attachment {
        return Attachment.Builder()
            .id(attachmentRO.id)
            .name(attachmentRO.name)
            .url(attachmentRO.url)
            .type(attachmentRO.type)
            .index(attachmentRO.index)
            .width(attachmentRO.width)
            .height(attachmentRO.height)
            .awsFolderPath(attachmentRO.awsFolderPath)
            .localFilePath(attachmentRO.localFilePath)
            .thumbnailUrl(attachmentRO.thumbnailUrl)
            .thumbnailAWSFolderPath(attachmentRO.thumbnailAWSFolderPath)
            .thumbnailLocalFilePath(attachmentRO.thumbnailLocalFilePath)
            .meta(convertAttachmentMetaRO(attachmentRO.metaRO))
            .createdAt(attachmentRO.createdAt)
            .updatedAt(attachmentRO.updatedAt)
            .build()
    }

    // converts AttachmentMetaRO model to client model
    private fun convertAttachmentMetaRO(attachmentMetaRO: AttachmentMetaRO?): AttachmentMeta? {
        if (attachmentMetaRO == null) return null
        return AttachmentMeta.Builder()
            .numberOfPage(attachmentMetaRO.numberOfPage)
            .duration(attachmentMetaRO.duration)
            .size(attachmentMetaRO.size)
            .build()
    }

    // converts LinkRO model to client model
    private fun convertLinkRO(linkRO: LinkRO?): LinkOGTags? {
        if (linkRO == null) return null
        return LinkOGTags.Builder()
            .url(linkRO.url)
            .title(linkRO.title)
            .url(linkRO.url)
            .description(linkRO.description)
            .build()
    }

    // converts list of ReactionRO to client model
    private fun convertReactionsRO(reactionsRO: List<ReactionRO>?): List<Reaction>? {
        if (reactionsRO.isNullOrEmpty()) return null
        return reactionsRO.map { reactionRO ->
            convertReactionRO(reactionRO)
        }
    }

    // converts ReactionRO model to client model
    private fun convertReactionRO(reactionRO: ReactionRO): Reaction {
        return Reaction.Builder()
            .reaction(reactionRO.reaction)
            .member(convertMemberRO(reactionRO.member))
            .build()
    }

    // converts list of PollRO model to client model
    private fun convertPollsRO(pollsRO: List<PollRO>): List<Poll> {
        return pollsRO.map { pollRO ->
            convertPollRO(pollRO)
        }
    }

    // converts PollRO model to client model
    private fun convertPollRO(pollRO: PollRO): Poll {
        return Poll.Builder()
            .id(pollRO.id)
            .text(pollRO.text)
            .isSelected(pollRO.isSelected)
            .percentage(pollRO.percentage)
            .subText(pollRO.subText)
            .noVotes(pollRO.noVotes)
            .member(convertMemberRO(pollRO.member))
            .build()
    }
}