package com.likeminds.likemindschat

import android.app.Application
import com.likeminds.likemindschat.chatroom.ChatroomClient
import com.likeminds.likemindschat.chatroom.model.GetChatroomRequest
import com.likeminds.likemindschat.chatroom.model.GetChatroomResponse
import com.likeminds.likemindschat.initiateUser.InitiateUserClient
import com.likeminds.likemindschat.initiateUser.model.InitiateUserRequest
import com.likeminds.likemindschat.initiateUser.model.InitiateUserResponse
import com.likeminds.likemindschat.initiateUser.model.LogoutRequest
import com.likeminds.likemindschat.initiateUser.model.RegisterDeviceRequest
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.user.UserClient
import com.likeminds.likemindschat.user.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LMChatClient private constructor() {

    @Inject
    lateinit var initiateUserClient: InitiateUserClient

    @Inject
    lateinit var userClient: UserClient

    @Inject
    lateinit var chatroomClient: ChatroomClient

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

    // Exposed function to register device
    suspend fun registerDevice(registerDeviceRequest: RegisterDeviceRequest): LMResponse<Nothing> {
        return initiateUserClient.registerDevice(registerDeviceRequest)
    }

    // Exposed function to get user from Db
    suspend fun getUser(): LMResponse<User> {
        return userClient.getUser()
    }

    // Exposed function to get chatroom
    suspend fun getChatroom(getChatroomRequest: GetChatroomRequest): LMResponse<GetChatroomResponse> {
        return chatroomClient.getChatroom(getChatroomRequest)
    }
}