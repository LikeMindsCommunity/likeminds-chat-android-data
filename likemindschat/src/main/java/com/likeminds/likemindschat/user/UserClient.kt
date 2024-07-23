package com.likeminds.likemindschat.user

import com.likeminds.internalsdk.ChatTokenManager
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.db.ROConverter
import com.likeminds.internalsdk.sdk.model._InitiateUserRequest_
import com.likeminds.internalsdk.user.model._LogoutRequest_
import com.likeminds.internalsdk.user.model._RegisterDeviceRequest_
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.community.model.GetMemberRequest
import com.likeminds.likemindschat.community.model.GetMemberResponse
import com.likeminds.likemindschat.user.model.InitiateUserRequest
import com.likeminds.likemindschat.user.model.InitiateUserResponse
import com.likeminds.likemindschat.user.model.LogoutRequest
import com.likeminds.likemindschat.user.model.RegisterDeviceRequest
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.user.model.GetLoggedInUserResponse
import com.likeminds.likemindschat.user.model.MemberStateResponse
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.Realm
import javax.inject.Inject

class UserClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().userComponent()?.inject(this)
    }

    private val sdkApi by lazy {
        chatSDK.getSDKApi()
    }

    private val userApi by lazy {
        chatSDK.getUserApi()
    }

    private val userDb by lazy {
        chatSDK.getUserDb()
    }

    private val sdkPreferences by lazy {
        chatSDK.getSDKPreferences()
    }

    private val userPreferences by lazy {
        chatSDK.getUserPreference()
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
                val communityId = body.data?.community?.id ?: ""
                val user = body.data?.user

                // todo: remove these tokens
                sdkPreferences.setAccessToken(accessToken)
                sdkPreferences.setRefreshToken(refreshToken)

                val chatTokenManager = ChatTokenManager.getInstance()
                chatTokenManager.updateTokens(accessToken, refreshToken)

                if (body.data?.appAccess == false) {
                    // logout the user if app access is false
                    val logoutRequest = LogoutRequest.Builder()
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
                    val lmUUID = user?.uuid ?: ""
                    val lmMemberId = user?.id ?: ""
                    val clientUUID = user?.sdkClientInfo?.uuid ?: ""
                    val userRO = ROConverter.convertUser(user)


                    sdkPreferences.setCommunityId(communityId)
                    userPreferences.setLMUUID(lmUUID)
                    userPreferences.setLMMemberId(lmMemberId)
                    userPreferences.setClientUUID(clientUUID)

                    userRO?.let {
                        userDb.saveUser(it)
                    }
                    ModelConverter.convertInitiateUserAPIResponse(body)
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
                .refreshToken(ChatTokenManager.getInstance().refreshToken ?: "")
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
                ChatTokenManager.getInstance().clear()
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
        if (logoutRequest.deviceId.isEmpty()) {
            RequestUtils.throwException("deviceId")
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


    /**
     * Calls the MemberState api
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return MemberStateResponse - MemberStateResponse model for MemberState api call
     */
    suspend fun getMemberState(): LMResponse<MemberStateResponse> {
        // validates the client request
        RequestUtils.validate()

        // calls api and processes the response accordingly
        return when (val response = userApi.getMemberState()) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = false,
                    errorMessage = response.body.errorMessage,
                    null
                )
            }

            is NetworkResponse.Success -> {
                ModelConverter.convertMemberStateResponse(response.body)
            }
        }
    }

    /**
     * Fetches the user from local db
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return GetUserResponse - GetUserResponse model for getUser request
     */
    fun getLoggedInUser(): LMResponse<GetLoggedInUserResponse> {
        // validates the client request
        RequestUtils.validate()

        val realm = Realm.getDefaultInstance()
        val userRO = userDb.getUser(realm)
        val response = if (userRO == null) {
            LMResponse(success = false, errorMessage = "User doesn't exist")
        } else {
            LMResponse(
                success = true,
                null,
                ModelConverter.convertGetLoggedInUserResponse(userRO)
            )
        }
        realm.close()
        return response
    }

    /**
     * Fetches the member from local db
     * @param getMemberRequest - client request model to get member
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated or required properties are not provided
     * @return GetMemberResponse - GetMemberResponse model for getMember request
     */
    fun getMember(getMemberRequest: GetMemberRequest): LMResponse<GetMemberResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetMemberRequest(getMemberRequest)

        val realm = Realm.getDefaultInstance()
        val communityId = chatSDK.sdkPreferences.getCommunityId() ?: ""
        val memberRO = ChatDBUtil.getMember(
            realm,
            communityId,
            getMemberRequest.uuid
        )
        val getMemberResponse = ModelConverter.convertGetMemberResponse(memberRO)
        val member = getMemberResponse.member
        realm.close()
        return if (member == null) {
            LMResponse(success = false, errorMessage = "User doesn't exist")
        } else {
            LMResponse(
                success = true,
                null,
                getMemberResponse
            )
        }
    }

    /**
     * validates [getMemberRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetMemberRequest(getMemberRequest: GetMemberRequest) {
        if (getMemberRequest.uuid.isEmpty()) {
            RequestUtils.throwException("uuid")
        }
    }
}