package com.likeminds.internalsdk.notification

import com.likeminds.internalsdk.notification.model._GetConversationNotificationUnreadResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import retrofit2.http.GET

interface NotificationNetworkApi {

    @GET("conversation/notification/unread")
    suspend fun getUnreadConversationNotification(): NetworkResponse<APIResponse<_GetConversationNotificationUnreadResponse_>>
}