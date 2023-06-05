package com.likeminds.internalsdk.db

import com.likeminds.internalsdk.community.model._Community_
import com.likeminds.internalsdk.community.model._Member_
import com.likeminds.internalsdk.db.models.*
import com.likeminds.internalsdk.user.model._SDKClientInfo_
import com.likeminds.internalsdk.user.model._User_

object ROConverter {

    /**--------------------------------
     * Internal Model -> Db Model
    --------------------------------*/

    //convert _User_ -> UserRO
    fun convertUser(user: _User_?): UserRO? {
        if (user == null) return null
        return UserRO().apply {
            id = user.id
            userUniqueId = user.userUniqueId
            imageUrl = user.imageUrl
            name = user.name
            isGuest = user.isGuest
            organizationName = user.organisationName
            updatedAt = user.updatedAt
            sdkClientInfoRO = convertSDKClientInfo(user.sdkClientInfo)
            isDeleted = user.isDeleted
            customTitle = user.customTitle
        }
    }

    //convert _SDKClientInfo_ -> SDKClientInfoRO
    private fun convertSDKClientInfo(sdkClientInfo: _SDKClientInfo_?): SDKClientInfoRO? {
        if (sdkClientInfo == null) return null
        return SDKClientInfoRO().apply {
            community = sdkClientInfo.community
            user = sdkClientInfo.user
            userUniqueId = sdkClientInfo.userUniqueId
        }
    }

    //convert _Community_ -> CommunityRO
    fun convertCommunity(community: _Community_?): CommunityRO? {
        if (community == null) return null
        return CommunityRO().apply {
            id = community.id
            name = community.name
            imageUrl = community.imageUrl
            membersCount = community.membersCount
            updatedAt = community.updatedAt
        }
    }

    fun convertMember(member_: _Member_):
}