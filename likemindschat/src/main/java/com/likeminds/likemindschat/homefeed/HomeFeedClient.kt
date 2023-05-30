package com.likeminds.likemindschat.homefeed

import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.homefeed.model.GetExploreTabCountResponse
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils

class HomeFeedClient : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().homeFeedComponent()?.inject(this)
    }

    private val homeFeedApi by lazy {
        groupChatSDK.homeFeedApi()
    }

    /**
     * @throws IllegalArgumentException - when LMChatClient is not instantiated
     * @return GetExploreTabCountResponse - GetExploreTabCountResponse model for getExploreTabCount
     */
    suspend fun getExploreTabCount(): LMResponse<GetExploreTabCountResponse> {
        //validates the client request
        RequestUtils.validate()
        // calls api and processes the response accordingly
        return when (val response = homeFeedApi.getExploreTabCount()) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = false,
                    errorMessage = response.body.errorMessage
                )
            }

            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertGetExploreTabCountAPIResponse(body)
            }
        }
    }
}