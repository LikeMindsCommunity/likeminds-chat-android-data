package com.likeminds.chatinternalsdk.notification

import com.likeminds.chatinternalsdk.notification.model._GetConversationNotificationUnreadResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET

interface NotificationNetworkApi {

    @GET("conversation/notification/unread")
    suspend fun getUnreadConversationNotification(): NetworkResponse<APIResponse<_GetConversationNotificationUnreadResponse_>>
}