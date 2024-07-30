package com.likeminds.chatinternalsdk.notification

import com.likeminds.chatinternalsdk.notification.model._GetConversationNotificationUnreadResponse_
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class NotificationReceiver @Inject constructor(
    private val notificationNetworkApi: NotificationNetworkApi
) {

    suspend fun getUnreadConversationNotification(): NetworkResponse<APIResponse<_GetConversationNotificationUnreadResponse_>> {
        return notificationNetworkApi.getUnreadConversationNotification()
    }
}