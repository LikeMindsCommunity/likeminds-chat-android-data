package com.likeminds.likemindschat.notification

import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.notification.model.GetUnreadChatroomsResponse
import com.likeminds.likemindschat.notification.model.GetUnreadChatroomsRequest
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
     * @param getUnreadChatroomsRequest - client request model to get unread conversations for notification
     * @return GetUnreadChatroomsResponse - GetUnreadChatroomsResponse model
     */
    suspend fun getUnreadChatrooms(getUnreadChatroomsRequest: GetUnreadChatroomsRequest): LMResponse<GetUnreadChatroomsResponse> {
        // validates the client request
        RequestUtils.validate()
        validateGetUnreadChatroomsRequest(getUnreadChatroomsRequest)

        val realm = Realm.getDefaultInstance()

        // inserts the last conversation and chatroom (if missing) to DB and fetches chatrooms with unread conversations
        val response = ModelConverter.convertGetUnreadChatroomsResponse(
            notificationDB.getUnreadChatrooms(
                realm,
                ModelConverter.createChatroom(getUnreadChatroomsRequest.chatroom),
                ModelConverter.createConversation(getUnreadChatroomsRequest.chatroomLastConversation)
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
     * validates [getUnreadChatroomsRequest]
     * @throws IllegalArgumentException - when required properties not provided
     */
    private fun validateGetUnreadChatroomsRequest(getUnreadChatroomsRequest: GetUnreadChatroomsRequest) {
        if (getUnreadChatroomsRequest.chatroomLastConversation.id.isNullOrEmpty()) {
            RequestUtils.throwException("chatroomLastConversation")
        }

        if (getUnreadChatroomsRequest.chatroom.id.isEmpty()) {
            RequestUtils.throwException("chatroom")
        }
    }
}