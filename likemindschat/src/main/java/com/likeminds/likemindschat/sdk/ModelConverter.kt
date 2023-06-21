package com.likeminds.likemindschat.sdk

import com.likeminds.internalsdk.chatroom.model.*
import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.db.models.SDKClientInfoRO
import com.likeminds.internalsdk.db.models.UserRO
import com.likeminds.internalsdk.sdk.model._InitiateUserResponse_
import com.likeminds.internalsdk.user.model._SDKClientInfo_
import com.likeminds.internalsdk.user.model._User_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.chatroom.model.*
import com.likeminds.likemindschat.community.model.Community
import com.likeminds.likemindschat.initiateUser.model.InitiateUserResponse
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

    // converts api GetParticipantsResponse model to LM GetParticipantsResponse model
    fun convertGetParticipantsResponse(
        apiResponse: APIResponse<_GetParticipantsResponse_>
    ): LMResponse<GetParticipantsResponse> {
        return LMResponse(
            apiResponse.success,
            apiResponse.errorMessage,
            convertGetChatroomParticipantsResponse(apiResponse.data)
        )
    }

    // converts internal GetParticipantsResponse model to client model
    private fun convertGetChatroomParticipantsResponse(
        _getParticipantsResponse_: _GetParticipantsResponse_?
    ): GetParticipantsResponse? {
        if (_getParticipantsResponse_ == null) return null
        return GetParticipantsResponse(
            _getParticipantsResponse_.canEditParticipant,
            convertParticipantsData(_getParticipantsResponse_.participants),
            _getParticipantsResponse_.totalParticipantsCount
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