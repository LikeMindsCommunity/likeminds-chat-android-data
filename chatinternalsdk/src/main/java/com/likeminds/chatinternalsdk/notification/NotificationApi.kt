package com.likeminds.chatinternalsdk.notification

import com.likeminds.chatinternalsdk.notification.model._GetConversationNotificationUnreadResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse

interface NotificationApi {

    // api to fetch unread conversation for notification
    suspend fun getUnreadConversationNotification(): NetworkResponse<APIResponse<_GetConversationNotificationUnreadResponse_>>
}