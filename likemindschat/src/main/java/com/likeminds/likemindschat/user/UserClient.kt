package com.likeminds.likemindschat.user

import com.likeminds.chatinternalsdk.ChatTokenManager
import com.likeminds.chatinternalsdk.db.ChatDBUtil
import com.likeminds.chatinternalsdk.db.ROConverter
import com.likeminds.chatinternalsdk.sdk.model._InitiateUserRequest_
import com.likeminds.chatinternalsdk.user.model._LogoutRequest_
import com.likeminds.chatinternalsdk.user.model._RegisterDeviceRequest_
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.community.model.GetMemberRequest
import com.likeminds.likemindschat.community.model.GetMemberResponse
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.user.model.*
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

    private val syncPreferences by lazy {
        chatSDK.getSyncPreference()
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
        val request = _InitiateUserRequest_.Builder()
            .userId(initiateUserRequest.userId)
            .apiKey(initiateUserRequest.apiKey)
            .userName(initiateUserRequest.userName)
            .isGuest(initiateUserRequest.isGuest)
            .tokenExpiryBeta(1) // for beta only
            .rtmTokenExpiryBeta(2) // for beta only
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

                // update tokens in token manager
                val chatTokenManager = ChatTokenManager.getInstance()
                chatTokenManager.updateTokens(accessToken, refreshToken)


                sdkPreferences.setAccessToken(accessToken)
                sdkPreferences.setRefreshToken(refreshToken)
                sdkPreferences.setAPIKey(initiateUserRequest.apiKey)


                if (body.data?.appAccess == false) {
                    val deviceId = initiateUserRequest.deviceId
                    if (!deviceId.isNullOrEmpty()) {
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
                        clearLocalStorage()

                        //return response
                        LMResponse(
                            success = false,
                            errorMessage = "App access is denied."
                        )
                    }
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
                clearLocalStorage()

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

    private fun clearLocalStorage() {
        //clear token manager
        ChatTokenManager.getInstance().clear()

        //clear db
        ChatDBUtil.clearDB()

        //clear preferences
        sdkPreferences.clear()
        userPreferences.clear()
        syncPreferences.clear()
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


    /**
     * Converts client request model to internal model and calls the api
     * @param validateUserRequest - client request model to validate user
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated or required properties not provided
     * @return [ValidateUserResponse] - ValidateUserResponse model for [ValidateUserRequest]
     */
    suspend fun validateUser(validateUserRequest: ValidateUserRequest): LMResponse<ValidateUserResponse> {
        // validates the client request
        RequestUtils.validate()
        validateValidateUserRequest(validateUserRequest)

        val accessToken = validateUserRequest.accessToken
        val refreshToken = validateUserRequest.refreshToken

        //save in token manager
        val chatTokenManager = ChatTokenManager.getInstance()
        chatTokenManager.updateTokens(accessToken, refreshToken)

        //save in local prefs
        sdkPreferences.setAccessToken(accessToken)
        sdkPreferences.setRefreshToken(refreshToken)

        return when (val response = sdkApi.validateUser()) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = false,
                    errorMessage = response.body.errorMessage,
                    null
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                val communityId = body.data?.community?.id ?: ""
                val user = body.data?.user


                if (body.data?.appAccess == false) {
                    val deviceId = validateUserRequest.deviceId
                    if (!deviceId.isNullOrEmpty()) {
                        // logout the user if app access is false
                        val logoutRequest = LogoutRequest.Builder()
                            .deviceId(validateUserRequest.deviceId)
                            .build()

                        val logoutResponse = logout(logoutRequest)
                        LMResponse(
                            success = false,
                            body.errorMessage,
                            ValidateUserResponse(
                                appAccess = false,
                                logoutResponse = logoutResponse
                            )
                        )
                    } else {
                        clearLocalStorage()

                        //return response
                        LMResponse(
                            success = false,
                            errorMessage = "App access is denied."
                        )
                    }
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

                    ModelConverter.convertValidateUserAPIResponse(body)
                }
            }
        }
    }

    /**
     * validates [validateUserRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateValidateUserRequest(validateUserRequest: ValidateUserRequest) {
        if (validateUserRequest.accessToken.isEmpty()) {
            RequestUtils.throwException("accessToken")
        }

        if (validateUserRequest.refreshToken.isEmpty()) {
            RequestUtils.throwException("refreshToken")
        }
    }


    /**
     * Get the API key from local preferences
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return LMResponse<String> - API key
     */
    fun getAPIKey(): LMResponse<String> {
        // validates the client request
        RequestUtils.validate()

        val apiKey = sdkPreferences.getAPIKey()
        return if (!apiKey.isNullOrEmpty()) {
            LMResponse(
                success = true,
                errorMessage = null,
                data = apiKey
            )
        } else {
            LMResponse(
                success = false,
                errorMessage = "API Key not found.",
                data = null
            )
        }
    }

    /**
     * Set the access token and refresh token in local preferences
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @param setTokensRequest - [SetTokensRequest] model to set access token and refresh token
     * @return LMResponse<Pair<String, String>> - access token and refresh token
     */
    fun setTokens(setTokensRequest: SetTokensRequest): LMResponse<Nothing> {
        // validates the client request
        RequestUtils.validate()
        validateSetTokensRequest(setTokensRequest)
        //update local prefs
        sdkPreferences.setAccessToken(setTokensRequest.accessToken)
        sdkPreferences.setRefreshToken(setTokensRequest.refreshToken)

        return LMResponse(success = true)
    }

    /**
     * validates [setTokensRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateSetTokensRequest(setTokensRequest: SetTokensRequest) {
        if (setTokensRequest.accessToken.isEmpty()) {
            RequestUtils.throwException("accessToken")
        }
        if (setTokensRequest.refreshToken.isEmpty()) {
            RequestUtils.throwException("refreshToken")
        }
    }

    /**
     * Get the access token and refresh token from local preferences
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return LMResponse<Pair<String, String>> - access token and refresh token
     */
    fun getTokens(): LMResponse<Pair<String, String>> {
        // validates the client request
        RequestUtils.validate()

        val accessToken = sdkPreferences.getAccessToken()
        val refreshToken = sdkPreferences.getRefreshToken()

        return if (accessToken != null && refreshToken != null) {
            LMResponse(
                success = true,
                errorMessage = null,
                data = Pair(accessToken, refreshToken)
            )
        } else {
            LMResponse(
                success = false,
                errorMessage = "Tokens not found!",
                data = null
            )
        }
    }
}