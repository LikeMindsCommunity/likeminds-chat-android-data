package com.likeminds.internalsdk.notification

import com.likeminds.internalsdk.notification.model._GetConversationNotificationUnreadResponse_
import com.likeminds.internalsdk.utils.retrofit.model.APIResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class NotificationReceiver @Inject constructor(
    private val notificationNetworkApi: NotificationNetworkApi
) {

    suspend fun getUnreadConversationNotification(): NetworkResponse<APIResponse<_GetConversationNotificationUnreadResponse_>> {
        return notificationNetworkApi.getUnreadConversationNotification()
    }
}