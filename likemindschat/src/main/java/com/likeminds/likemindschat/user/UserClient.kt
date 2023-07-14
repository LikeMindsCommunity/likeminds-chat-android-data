package com.likeminds.likemindschat.user

import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.user.model.GetUserResponse
import com.likeminds.likemindschat.user.model.MemberStateResponse
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class UserClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().userComponent()?.inject(this)
    }

    private val userApi by lazy {
        groupChatSDK.getUserApi()
    }

    private val userDb by lazy {
        groupChatSDK.getUserDb()
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
    fun getUser(): LMResponse<GetUserResponse> {
        // validates the client request
        RequestUtils.validate()

        val userRO = userDb.getUser()
        return if (userRO == null) {
            LMResponse(success = false, errorMessage = "User doesn't exist")
        } else {
            LMResponse(
                success = true,
                null,
                ModelConverter.convertGetUserResponse(userRO)
            )
        }
    }
}