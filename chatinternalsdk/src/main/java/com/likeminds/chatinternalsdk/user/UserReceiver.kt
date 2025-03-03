package com.likeminds.chatinternalsdk.user

import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.db.models.MemberRO
import com.likeminds.chatinternalsdk.db.models.UserRO
import com.likeminds.chatinternalsdk.user.api.UserNetworkApi
import com.likeminds.chatinternalsdk.user.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import io.realm.Realm
import javax.inject.Inject

class UserReceiver @Inject constructor(private val userNetworkApi: UserNetworkApi) {

    /*
        API Functions
     */
    suspend fun logout(
        request: _LogoutRequest_
    ): NetworkResponse<APIResponse<Nothing>> {
        val deviceId = request.deviceId ?: ""
        val newRequest = request.toBuilder().deviceId(null).build()
        return userNetworkApi.logout(deviceId, newRequest)
    }

    suspend fun registerDevice(request: _RegisterDeviceRequest_): NetworkResponse<APIResponse<Nothing>> {
        val deviceId = request.deviceId ?: ""
        val newRequest = request.toBuilder().deviceId(null)
            .build()
        return userNetworkApi.registerDevice(deviceId, newRequest)
    }

    suspend fun getMemberState(): NetworkResponse<APIResponse<_MemberStateResponse_>> {
        return userNetworkApi.getMemberState()
    }

    suspend fun editUserProfile(request: _EditUserProfileRequest_): NetworkResponse<APIResponse<Nothing>> {
        return userNetworkApi.editUserProfile(request)
    }

    /*
        Db Functions
     */
    fun saveUser(userRO: UserRO) {
        ChatDBUtil.writeAsync({ realmWrite ->
            realmWrite.insertOrUpdate(userRO)
        })
    }

    fun getUser(realm: Realm): UserRO? {
        return realm.where(UserRO::class.java).findFirst()
    }

    fun saveMember(memberRO: MemberRO) {
        ChatDBUtil.writeAsync({ realmWrite ->
            realmWrite.insertOrUpdate(memberRO)
        })
    }
}