package com.likeminds.likemindschat.notification

import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.notification.model.GetConversationNotificationUnreadResponse
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import javax.inject.Inject

class NotificationClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().notificationSubComponent()?.inject(this)
    }

    private val notificationApi by lazy {
        chatSDK.getNotificationApi()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @return GetConversationNotificationUnreadResponse - GetConversationNotificationUnreadResponse model
     */
    suspend fun getUnreadConversationNotification(): LMResponse<GetConversationNotificationUnreadResponse> {
        // validates the client request
        RequestUtils.validate()

        // calls api and processes the response accordingly
        return when (val response = notificationApi.getUnreadConversationNotification()) {
            is NetworkResponse.Error -> {
                LMResponse(
                    success = response.body.success,
                    errorMessage = response.body.errorMessage,
                )
            }
            is NetworkResponse.Success -> {
                val body = response.body
                ModelConverter.convertGetConversationNotificationUnreadResponse(body)
            }
        }
    }
}