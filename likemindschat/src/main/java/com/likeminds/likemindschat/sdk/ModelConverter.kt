package com.likeminds.likemindschat.sdk

import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.homefeed.model.*
import com.likeminds.internalsdk.db.models.SDKClientInfoRO
import com.likeminds.internalsdk.db.models.UserRO
import com.likeminds.internalsdk.helper.model._DecodeUrlResponse_
import com.likeminds.internalsdk.helper.model._GetTaggingListResponse_
import com.likeminds.internalsdk.helper.model._GroupTag_
import com.likeminds.internalsdk.helper.model._UserTag_
import com.likeminds.internalsdk.moderation.model._GetReportTagsResponse_
import com.likeminds.internalsdk.moderation.model._ReportTag_
import com.likeminds.internalsdk.sdk.model._InitiateUserResponse_
import com.likeminds.internalsdk.user.model._SDKClientInfo_
import com.likeminds.internalsdk.user.model._User_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.chatroom.model.*
import com.likeminds.likemindschat.community.model.Community
import com.likeminds.likemindschat.homefeed.model.*
import com.likeminds.likemindschat.community.model.LinkOGTags
import com.likeminds.likemindschat.helper.model.DecodeUrlResponse
import com.likeminds.likemindschat.helper.model.GetTaggingListResponse
import com.likeminds.likemindschat.helper.model.GroupTag
import com.likeminds.likemindschat.helper.model.UserTag
import com.likeminds.likemindschat.initiateUser.model.InitiateUserResponse
import com.likeminds.likemindschat.moderation.model.GetReportTagsResponse
import com.likeminds.likemindschat.moderation.model.ReportTag
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

    fun convertConfigAPIResponse(
        apiResponse: APIResponse<_ConfigResponse_>
    ): LMResponse<ConfigResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertConfigAPIResponse(apiResponse.data)
        )
    }

    private fun convertConfigAPIResponse(_configResponse_: _ConfigResponse_?): ConfigResponse? {
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

    private fun convertUserDetails(_userDetails_: _UserDetail_): UserDetail {
        return UserDetail(
            convertUser(_userDetails_.user),
            convertUserMetrics(_userDetails_.userMetrics)
        )
    }

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

    // converts api DecodeUrlResponse model to LM DecodeUrlResponse model
    fun convertDecodeUrlResponse(
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
        _chatroomParticipants_: List<_UserTag_>
    ): List<UserTag> {
        return _chatroomParticipants_.map {
            convertUserTag(it)
        }
    }

    // converts internal communityMembers list to client model list
    private fun convertCommunityMembers(
        _communityMembers_: List<_UserTag_>
    ): List<UserTag> {
        return _communityMembers_.map {
            convertUserTag(it)
        }
    }

    // converts internal UserTag model to client model
    private fun convertUserTag(
        _userTag_: _UserTag_
    ): UserTag {
        return UserTag(
            _userTag_.id,
            _userTag_.imageUrl,
            _userTag_.isGuest,
            _userTag_.name,
            _userTag_.userUniqueId,
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