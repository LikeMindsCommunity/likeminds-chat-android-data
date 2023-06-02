package com.likeminds.likemindschat.sdk

import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.community.model._MemberAction_
import com.likeminds.internalsdk.community.model._Member_
import com.likeminds.internalsdk.community.model._Question_
import com.likeminds.internalsdk.db.models.SDKClientInfoRO
import com.likeminds.internalsdk.db.models.UserRO
import com.likeminds.internalsdk.moderation.model._GetReportTagsResponse_
import com.likeminds.internalsdk.moderation.model._ReportTag_
import com.likeminds.internalsdk.poll.model._AddPollOptionResponse_
import com.likeminds.internalsdk.poll.model._GetPollUsersResponse_
import com.likeminds.internalsdk.poll.model._Poll_
import com.likeminds.internalsdk.sdk.model._InitiateUserResponse_
import com.likeminds.internalsdk.user.model._SDKClientInfo_
import com.likeminds.internalsdk.user.model._User_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.chatroom.model.*
import com.likeminds.likemindschat.community.model.Community
import com.likeminds.likemindschat.community.model.Member
import com.likeminds.likemindschat.community.model.MemberAction
import com.likeminds.likemindschat.community.model.Question
import com.likeminds.likemindschat.initiateUser.model.InitiateUserResponse
import com.likeminds.likemindschat.moderation.model.GetReportTagsResponse
import com.likeminds.likemindschat.moderation.model.ReportTag
import com.likeminds.likemindschat.poll.model.AddPollOptionResponse
import com.likeminds.likemindschat.poll.model.GetPollUsersResponse
import com.likeminds.likemindschat.poll.model.Poll
import com.likeminds.likemindschat.user.model.SDKClientInfo
import com.likeminds.likemindschat.user.model.User

object ModelConverter {

    /**--------------------------------
     * Internal Model -> Client Model
    --------------------------------*/

    // converts api InitiateUserResponse model to LM InitiateUserResponse model
    fun convertInitiateUserResponse(
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
            _user_.userUniqueId
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
                it.userUniqueId
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

    // converts api GetChatroomResponse model to LM GetChatroomResponse model
    fun convertGetChatroomResponse(
        apiResponse: APIResponse<_GetChatroomResponse_>
    ): LMResponse<GetChatroomResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetChatroomResponse(apiResponse.data)
        )
    }

    // converts internal GetChatroomResponse model to client model
    private fun convertGetChatroomResponse(
        _getChatroomResponse_: _GetChatroomResponse_?
    ): GetChatroomResponse? {
        if (_getChatroomResponse_ == null) return null
        return GetChatroomResponse(
            _getChatroomResponse_.canAccessSecretChatroom,
            convertChatroomActions(_getChatroomResponse_.chatroomActions),
            _getChatroomResponse_.participantCount,
            _getChatroomResponse_.placeHolder
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

    // converts api ShareChatroomUrlResponse model to LM ShareChatroomUrlResponse model
    fun convertShareChatroomUrlResponse(
        apiResponse: APIResponse<_ShareChatroomUrlResponse_>
    ): LMResponse<ShareChatroomUrlResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertShareChatroomUrlResponse(apiResponse.data)
        )
    }

    // converts internal ShareChatroomUrlResponse model to client model
    private fun convertShareChatroomUrlResponse(
        _shareChatroomUrlResponse_: _ShareChatroomUrlResponse_?
    ): ShareChatroomUrlResponse? {
        if (_shareChatroomUrlResponse_ == null) return null
        return ShareChatroomUrlResponse(
            convertShareChatroomUrl(_shareChatroomUrlResponse_.shareChatroomUrl)
        )
    }

    // converts internal convertShareChatroomUrl model to client model
    private fun convertShareChatroomUrl(
        _shareChatroomUrl_: _ShareChatroomUrl_
    ): ShareChatroomUrl {
        return ShareChatroomUrl(
            _shareChatroomUrl_.shareUrl,
            _shareChatroomUrl_.creatorShareUrl,
            _shareChatroomUrl_.linkCreatedAt
        )
    }

    // converts api GetChatroomParticipantsResponse model to LM GetChatroomParticipantsResponse model
    fun convertGetChatroomParticipantsResponse(
        apiResponse: APIResponse<_GetChatroomParticipantsResponse_>
    ): LMResponse<GetChatroomParticipantsResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetChatroomParticipantsResponse(apiResponse.data)
        )
    }

    // converts internal GetChatroomParticipantsResponse model to client model
    private fun convertGetChatroomParticipantsResponse(
        _getChatroomParticipantsResponse_: _GetChatroomParticipantsResponse_?
    ): GetChatroomParticipantsResponse? {
        if (_getChatroomParticipantsResponse_ == null) return null
        return GetChatroomParticipantsResponse(
            _getChatroomParticipantsResponse_.canEditParticipant,
            convertParticipantsData(_getChatroomParticipantsResponse_.participants),
            _getChatroomParticipantsResponse_.totalParticipantsCount
        )
    }

    // converts internal ParticipantData model list to client model list
    private fun convertParticipantsData(
        _participants_: List<_ParticipantData_>
    ): List<ParticipantData> {
        return _participants_.map {
            convertParticipantData(it)
        }
    }

    // converts internal ParticipantData model to client model
    private fun convertParticipantData(
        _participant_: _ParticipantData_
    ): ParticipantData {
        return ParticipantData(
            _participant_.id,
            _participant_.imageUrl,
            _participant_.isGuest,
            _participant_.name,
            _participant_.userUniqueId,
            _participant_.customTitle,
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
    fun convertAddPollOptionResponse(
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
    fun convertGetPollUsersResponse(
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

    // creates internal Poll model to client model
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

    fun convertMembers(
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
            .email(_member_.email)
            .headline(_member_.headline)
            .city(_member_.city)
            .imageUrl(_member_.imageUrl)
            .questionAnswers(convertQuestions(_member_.questionAnswers))
            .state(_member_.state)
            .removeState(_member_.removeState)
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

    /**--------------------------------
     * Client Model -> Internal Model
    --------------------------------*/

    // creates internal Poll model list from client model list
    fun createPolls(
        polls: List<Poll>
    ): List<_Poll_> {
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
            .email(member.email)
            .headline(member.headline)
            .city(member.city)
            .imageUrl(member.imageUrl)
            .questionAnswers(createQuestions(member.questionAnswers))
            .state(member.state)
            .removeState(member.removeState)
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

    /**--------------------------------
     * Db Model -> Client Model
    --------------------------------*/

    // converts User db model to client model
    fun convertUser(userRO: UserRO): User {
        return User(
            userRO.id,
            userRO.imageUrl,
            userRO.isGuest,
            userRO.name,
            userRO.organizationName,
            convertSDKClientInfo(userRO.sdkClientInfoRO),
            userRO.isDeleted,
            userRO.customTitle,
            userRO.updatedAt,
            userRO.userUniqueId
        )
    }

    // converts SDKClientInfo db model to client model
    private fun convertSDKClientInfo(sdkClientInfoRO: SDKClientInfoRO?): SDKClientInfo? {
        return sdkClientInfoRO?.let {
            SDKClientInfo(
                it.community,
                it.user,
                it.userUniqueId
            )
        }
    }
}