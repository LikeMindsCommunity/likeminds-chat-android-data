package com.likeminds.internalsdk.notification

import com.likeminds.internalsdk.notification.model._GetConversationNotificationUnreadResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse

interface NotificationApi {

    // api to fetch unread conversation for notification
    suspend fun getUnreadConversationNotification(): NetworkResponse<APIResponse<_GetConversationNotificationUnreadResponse_>>
}