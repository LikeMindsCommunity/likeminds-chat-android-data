package com.likeminds.likemindschat

import android.app.Application
import com.likeminds.likemindschat.initiateUser.InitiateUserClient
import com.likeminds.likemindschat.initiateUser.model.*
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LMChatClient private constructor() {

    @Inject
    lateinit var initiateUserClient: InitiateUserClient

    class Builder(val application: Application) {

        fun build(): LMChatClient {
            lmChatClientInstance = LMChatClient()
            val sdkApplication = LikeMindsChatApplication.getInstance()
            sdkApplication.initChatSDKApplication(application)
            sdkApplication.likeMindsChatComponent?.inject(lmChatClientInstance!!)
            return lmChatClientInstance!!
        }
    }

    companion object {

        @JvmStatic
        private var lmChatClientInstance: LMChatClient? = null

        @JvmStatic
        fun getInstance(): LMChatClient {
            if (lmChatClientInstance == null) {
                throw IllegalAccessException("LMChatClient is not created, please call LMChatClient.build()")
            }
            return lmChatClientInstance!!
        }
    }

    // Exposed function to process initiate user request
    suspend fun initiateUser(initiateUserRequest: InitiateUserRequest): LMResponse<InitiateUserResponse> {
        return initiateUserClient.initiateUser(initiateUserRequest)
    }

    // Exposed function to process logout request
    suspend fun logout(logoutRequest: LogoutRequest): LMResponse<Nothing> {
        return initiateUserClient.logout(logoutRequest)
    }

    suspend fun registerDevice(registerDeviceRequest: RegisterDeviceRequest): LMResponse<Nothing> {
        return initiateUserClient.registerDevice(registerDeviceRequest)
    }
}