package com.likeminds.likemindschat.user

import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.community.model.GetMemberRequest
import com.likeminds.likemindschat.community.model.GetMemberResponse
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.user.model.GetUserResponse
import com.likeminds.likemindschat.user.model.MemberStateResponse
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.Realm
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

        val realm = Realm.getDefaultInstance()
        val userRO = userDb.getUser(realm)
        val response = if (userRO == null) {
            LMResponse(success = false, errorMessage = "User doesn't exist")
        } else {
            LMResponse(
                success = true,
                null,
                ModelConverter.convertGetUserResponse(userRO)
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
        val communityId = groupChatSDK.sdkPreferences.getCommunityId() ?: ""
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
            RequestUtils.throwException("memberId")
        }
    }
}