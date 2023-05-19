package com.likeminds.internalsdk.user

import android.util.Log
import com.likeminds.internalsdk.CollabmatesChatSDK
import com.likeminds.internalsdk.CollabmatesChatSDK.Companion.LOG_TAG
import com.likeminds.internalsdk.db.models.UserRO
import com.likeminds.internalsdk.user.api.UserNetworkApi
import com.likeminds.internalsdk.user.model._LogoutRequest_
import com.likeminds.internalsdk.user.model._RegisterDeviceRequest_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import io.realm.kotlin.Realm
import io.sentry.protocol.User
import retrofit2.http.Body
import retrofit2.http.Header
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

    /*
        Db Functions
     */
    suspend fun saveUser(userRO: UserRO) {
        val realm = Realm.open(CollabmatesChatSDK.getRealmConfiguration())
        realm.write {
            val user =
                this.query(UserRO::class, "userUniqueId == $0", userRO.userUniqueId).first().find()
            if (user != null) {
                Log.d(LOG_TAG, "updating user")
                delete(user)
                copyToRealm(userRO)
            } else {
                Log.d(LOG_TAG, "inserting user")
                copyToRealm(userRO)
            }
        }
        realm.close()
    }
}