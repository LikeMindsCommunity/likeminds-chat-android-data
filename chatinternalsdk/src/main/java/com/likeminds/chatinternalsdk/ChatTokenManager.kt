package com.likeminds.chatinternalsdk

import android.util.Log
import javax.inject.Singleton

@Singleton
class ChatTokenManager {
    var accessToken: String? = null
    var refreshToken: String? = null

    companion object {
        @JvmStatic
        private var chatTokenManagerInstance: ChatTokenManager? = null

        fun getInstance(): ChatTokenManager {
            if (chatTokenManagerInstance == null) {
                chatTokenManagerInstance = ChatTokenManager()
            }

            return chatTokenManagerInstance!!
        }
    }


    // updates tokens and memberId in TokenManager
    fun updateTokens(
        accessToken: String? = null,
        refreshToken: String? = null
    ) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }


    // clears existing tokens inside TokenManager
    fun clear() {
        accessToken = null
        refreshToken = null
    }
}