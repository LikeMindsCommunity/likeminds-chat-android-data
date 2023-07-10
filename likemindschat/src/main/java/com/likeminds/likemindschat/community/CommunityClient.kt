package com.likeminds.likemindschat.community

import android.util.Log
import com.likeminds.internalsdk.community.model._GetExploreFeedRequest_
import com.likeminds.internalsdk.db.ChatDBUtil
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.community.model.GetExploreFeedRequest
import com.likeminds.likemindschat.community.model.GetExploreFeedResponse
import com.likeminds.likemindschat.community.model.GetMemberRequest
import com.likeminds.likemindschat.community.model.GetMemberResponse
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.Realm
import javax.inject.Inject

class CommunityClient @Inject constructor() : BaseClient() {
    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().communityComponent()?.inject(this)
    }

    private val communityApi by lazy {
        groupChatSDK.getCommunityApi()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @param getExploreFeedRequest - client request model to get explore feed
     * @throws IllegalArgumentException - when LMChatClient is not instantiated or required properties not provided
     * @return GetExploreFeedResponse - GetExploreFeedResponse model for getExploreFeedRequest
     */
    suspend fun getExploreFeed(getExploreFeedRequest: GetExploreFeedRequest): LMResponse<GetExploreFeedResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetExploreFeedRequest(getExploreFeedRequest)

        // builds internal request model
        val request =
            _GetExploreFeedRequest_.Builder()
                .orderType(getExploreFeedRequest.orderType)
                .isPinned(getExploreFeedRequest.isPinned)
                .page(getExploreFeedRequest.page)
                .build()

        // calls api and processes the response accordingly
        return when (val response = communityApi.getExploreFeed(request)) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage
                )
            }
            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertGetExploreFeedAPIResponse(body)
            }
        }
    }

    /**
     * validates [getExploreFeedRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetExploreFeedRequest(getExploreFeedRequest: GetExploreFeedRequest) {
        if (getExploreFeedRequest.orderType == -1) {
            RequestUtils.throwException("orderType")
        }
    }

    /**
     * Fetches the user from local db
     * @param getMemberRequest - client request model to get member
     * @return GetMemberResponse - GetMemberResponse model for getMember request
     */
    fun getMember(getMemberRequest: GetMemberRequest): LMResponse<GetMemberResponse> {
        val realm = Realm.getDefaultInstance()
        val communityId = groupChatSDK.sdkPreferences.getCommunityId() ?: ""
        Log.d("SDK", "getMember: $communityId")
        val memberRO = ChatDBUtil.getMember(
            realm,
            communityId,
            getMemberRequest.memberId
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
}