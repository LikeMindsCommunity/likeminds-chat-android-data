package com.likeminds.internalsdk.db

import com.likeminds.internalsdk.db.models.SDKClientInfoRO
import com.likeminds.internalsdk.db.models.UserRO
import com.likeminds.internalsdk.user.model._SDKClientInfo_
import com.likeminds.internalsdk.user.model._User_

object ROConvertor {

    /**--------------------------------
     * Internal Model -> Db Model
    --------------------------------*/

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

    private fun convertSDKClientInfo(sdkClientInfo: _SDKClientInfo_?): SDKClientInfoRO? {
        if (sdkClientInfo == null) return null
        return SDKClientInfoRO().apply {
            community = sdkClientInfo.community
            user = sdkClientInfo.user
            userUniqueId = sdkClientInfo.userUniqueId
        }
    }
}