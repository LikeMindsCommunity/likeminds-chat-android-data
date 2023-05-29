package com.likeminds.likemindschat.initiateUser

import com.likeminds.internalsdk.ChatTokenManager
import com.likeminds.internalsdk.db.ROConvertor
import com.likeminds.internalsdk.sdk.model._InitiateUserRequest_
import com.likeminds.internalsdk.user.model._LogoutRequest_
import com.likeminds.internalsdk.user.model._RegisterDeviceRequest_
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.initiateUser.model.InitiateUserRequest
import com.likeminds.likemindschat.initiateUser.model.InitiateUserResponse
import com.likeminds.likemindschat.initiateUser.model.LogoutRequest
import com.likeminds.likemindschat.initiateUser.model.RegisterDeviceRequest
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class InitiateUserClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().initiateUserComponent()?.inject(this)
    }

    private val sdkApi by lazy {
        groupChatSDK.getSDKApi()
    }
    private val refreshTokenApi by lazy {
        groupChatSDK.getRefreshTokenApi()
    }
    private val userApi by lazy {
        groupChatSDK.getUserApi()
    }
    private val userDb by lazy {
        groupChatSDK.getUserDb()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param initiateUserRequest - client request model to initiate user
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated or required properties not provided
     * @return InitiateUserResponse - InitiateUserResponse model for initiateUserRequest
     */
    suspend fun initiateUser(initiateUserRequest: InitiateUserRequest): LMResponse<InitiateUserResponse> {
        // validates the client request
        RequestUtils.validate()
        validateInitiateUserRequest(initiateUserRequest)
        // builds internal request model
        val request =
            _InitiateUserRequest_.Builder().userId(initiateUserRequest.userId)
                .apiKey(initiateUserRequest.apiKey)
                .userName(initiateUserRequest.userName)
                .isGuest(initiateUserRequest.isGuest)
                .build()
        // calls api and processes the response accordingly
        return when (val response = sdkApi.initiateUser(request.apiKey!!, request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = false,
                    errorMessage = response.body.errorMessage,
                    InitiateUserResponse(
                        appAccess = false
                    )
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                val accessToken = body.data?.accessToken ?: ""
                val refreshToken = body.data?.refreshToken ?: ""
                val chatTokenManager = ChatTokenManager.getInstance()
                chatTokenManager.updateTokens(accessToken, refreshToken)

                if (body.data?.appAccess == false) {
                    // logout the user if app access is false
                    val logoutRequest = LogoutRequest.Builder()
                        .refreshToken(refreshToken)
                        .deviceId(initiateUserRequest.deviceId)
                        .build()
                    val logoutResponse = logout(logoutRequest)
                    LMResponse(
                        success = false,
                        body.errorMessage,
                        InitiateUserResponse(
                            appAccess = false,
                            logoutResponse = logoutResponse
                        )
                    )
                } else {
                    val userRO = ROConvertor.convertUser(body.data?.user)
                    userRO?.let {
                        userDb.saveUser(it)
                    }
                    ModelConverter.convertInitiateUserResponse(body)
                }
            }
        }
    }

    /**
     * validates [initiateUserRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateInitiateUserRequest(initiateUserRequest: InitiateUserRequest) {
        if (initiateUserRequest.userName.isEmpty()) {
            RequestUtils.throwException("userName")
        }

        if (initiateUserRequest.deviceId.isEmpty()) {
            RequestUtils.throwException("deviceId")
        }

        if (initiateUserRequest.isGuest == null) {
            RequestUtils.throwException("isGuest")
        }

        if (initiateUserRequest.apiKey.isEmpty()) {
            RequestUtils.throwException("apiKey")
        }
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param logoutRequest - client request model to logout user
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated or required properties not provided
     * @return LMResponse<Nothing> - Base LM response
     */
    suspend fun logout(logoutRequest: LogoutRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateLogoutResponse(logoutRequest)
        // builds internal request model
        val request =
            _LogoutRequest_.Builder()
                .refreshToken(logoutRequest.refreshToken)
                .deviceId(logoutRequest.deviceId)
                .build()

        return when (val response = userApi.logout(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                LMResponse(
                    success = response.body.success
                )
            }
        }
    }

    /**
     * validates [logoutRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateLogoutResponse(logoutRequest: LogoutRequest) {
        if (logoutRequest.refreshToken.isEmpty()) {
            RequestUtils.throwException("refreshToken")
        }
    }

    suspend fun registerDevice(registerDeviceRequest: RegisterDeviceRequest): LMResponse<Nothing> {
        //validate request
        RequestUtils.validate()
        validateRegisterDeviceRequest(registerDeviceRequest)

        //build internal request model
        val request = _RegisterDeviceRequest_.Builder()
            .token(registerDeviceRequest.token)
            .deviceId(registerDeviceRequest.deviceId)
            .build()

        //call api and process the response accordingly
        return when (val response = userApi.registerDevice(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }
            is NetworkResponse.Success -> {
                LMResponse(success = response.body.success)
            }
        }
    }

    /**
     * validate [registerDeviceRequest]
     * @throws IllegalArgumentException - when required properties not provided
     * */
    private fun validateRegisterDeviceRequest(registerDeviceRequest: RegisterDeviceRequest) {
        if (registerDeviceRequest.token.isEmpty()) {
            RequestUtils.throwException("token")
        }

        if (registerDeviceRequest.deviceId.isEmpty()) {
            RequestUtils.throwException("deviceId")
        }
    }
}