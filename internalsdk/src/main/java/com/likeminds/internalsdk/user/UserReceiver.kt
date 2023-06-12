package com.likeminds.internalsdk.user

import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.models.UserRO
import com.likeminds.internalsdk.user.api.UserNetworkApi
import com.likeminds.internalsdk.user.model.*
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
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

    suspend fun getUserMeta(): NetworkResponse<APIResponse<_UserMetaResponse_>> {
        return userNetworkApi.getUserMeta()
    }

    /*
        Db Functions
     */
    fun saveUser(userRO: UserRO) {
        ChatDBUtil.writeAsync({ realmWrite ->
            realmWrite.insertOrUpdate(userRO)
        })
    }

    fun getUser(): UserRO? {
        val realm = Realm.getDefaultInstance()
        val userRO = realm.where(UserRO::class.java).findFirst()
        realm.close()
        return userRO
    }
}