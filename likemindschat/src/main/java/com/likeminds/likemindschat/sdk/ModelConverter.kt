package com.likeminds.likemindschat.sdk

import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.homefeed.model._GetExploreTabCountResponse_
import com.likeminds.internalsdk.sdk.model._InitiateUserResponse_
import com.likeminds.internalsdk.user.model._SDKClientInfo_
import com.likeminds.internalsdk.user.model._User_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.community.model.Community
import com.likeminds.likemindschat.homefeed.model.GetExploreTabCountResponse
import com.likeminds.likemindschat.initiateUser.model.InitiateUserResponse
import com.likeminds.likemindschat.user.model.SDKClientInfo
import com.likeminds.likemindschat.user.model.User

object ModelConverter {

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
}