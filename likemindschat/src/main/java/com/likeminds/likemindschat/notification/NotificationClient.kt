package com.likeminds.likemindschat.notification

import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.notification.model.GetConversationNotificationUnreadResponse
import com.likeminds.likemindschat.notification.model.GetUnreadConversationNotificationRequest
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.util.RequestUtils
import io.realm.Realm
import javax.inject.Inject

class NotificationClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().notificationSubComponent()?.inject(this)
    }

    private val notificationDB by lazy {
        chatSDK.getNotificationDB()
    }

    /**
     * Converts client request model to internal model and calls the api
     * @throws IllegalArgumentException - when LMFeedClient is not instantiated
     * @param getUnreadConversationNotificationRequest - client request model to get unread conversations for notification
     * @return GetConversationNotificationUnreadResponse - GetConversationNotificationUnreadResponse model
     */
    suspend fun getUnreadConversationNotification(getUnreadConversationNotificationRequest: GetUnreadConversationNotificationRequest): LMResponse<GetConversationNotificationUnreadResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetUnreadConversationNotification(getUnreadConversationNotificationRequest)

        val realm = Realm.getDefaultInstance()

        // inserts the last conversation and chatroom (if missing) to DB and fetches chatrooms with unread conversations
        val response = ModelConverter.convertGetConversationNotificationUnreadResponse(
            notificationDB.getUnreadConversationNotification(
                realm,
                ModelConverter.createChatroom(getUnreadConversationNotificationRequest.chatroom),
                ModelConverter.createConversation(getUnreadConversationNotificationRequest.chatroomLastConversation)
            )
        )
        realm.close()

        return LMResponse(
            success = true,
            errorMessage = null,
            response
        )
    }

    /**
     * validates [getUnreadConversationNotificationRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetUnreadConversationNotification(getUnreadConversationNotificationRequest: GetUnreadConversationNotificationRequest) {
        if (getUnreadConversationNotificationRequest.chatroomLastConversation.id.isNullOrEmpty()) {
            RequestUtils.throwException("chatroomLastConversation")
        }

        if (getUnreadConversationNotificationRequest.chatroom.id.isEmpty()) {
            RequestUtils.throwException("chatroom")
        }
    }
}