package com.likeminds.internalsdk.utils.retrofit

import android.util.Log
import com.likeminds.internalsdk.ChatTokenManager
import com.likeminds.internalsdk.CollabmatesChatSDK.Companion.LOG_TAG
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class RefreshTokenAuthenticator @Inject constructor() : Authenticator {
    companion object {
        const val INVALID_RTM = "Invalid RTM!"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val body = response.body?.string()
        Log.d(
            LOG_TAG,
            "refreshing refresh token"
        )
        return if (body?.contains(INVALID_RTM, true) == true) {
            Log.d(LOG_TAG, "refresh token is expired, clearing tokens")
            val chatTokenManager = ChatTokenManager.getInstance()
            chatTokenManager.clear()
//            val lmInternalCallback = CollabmatsSDK.getInstance().lmInternalCallback
//            lmInternalCallback?.login()
            null
        } else {
            Log.d(LOG_TAG, "refresh token failed, return null")
            null
        }
    }
}