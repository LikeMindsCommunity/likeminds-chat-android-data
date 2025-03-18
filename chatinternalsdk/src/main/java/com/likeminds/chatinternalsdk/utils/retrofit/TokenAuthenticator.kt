package com.likeminds.chatinternalsdk.utils.retrofit

import android.util.Log
import com.likeminds.chatinternalsdk.ChatTokenManager
import com.likeminds.chatinternalsdk.LMChatSDK
import com.likeminds.chatinternalsdk.LMChatSDK.Companion.LOG_TAG
import com.likeminds.chatinternalsdk.sdk.util.SDKPreferences
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val sdkPreferences: SDKPreferences
) : Authenticator {

    companion object {

        private const val AUTH = "Authorization"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val endPoint = response.request.url.toString()
        val code = response.code
        val chatTokenManager = ChatTokenManager.getInstance()
        val chatSDK = LMChatSDK.getInstance()
        val lmInternalCallback = chatSDK.lmChatInternalCallback
        val refreshTokenNetworkApi = chatSDK.getRefreshTokenApi()

        return if (code == 401) {
            if (!endPoint.contains("user/refresh", false)) {
                Log.d(LOG_TAG, "new access token required")
                val refreshToken = chatTokenManager.refreshToken ?: sdkPreferences.getRefreshToken()
                runBlocking {
                    when (val refreshResponse =
                        refreshTokenNetworkApi.refreshAccessToken("Bearer $refreshToken")) {
                        is NetworkResponse.Error -> {
                            Log.d(
                                LOG_TAG,
                                "access token refresh failed: ${refreshResponse.body.errorMessage}"
                            )
                            null
                        }

                        is NetworkResponse.Success -> {
                            Log.d(LOG_TAG, "access token refreshed")

                            val newAccessToken = refreshResponse.body.data?.accessToken ?: ""
                            val newRefreshToken = refreshResponse.body.data?.refreshToken ?: ""
                            val updatedToken = "Bearer $newAccessToken"

                            //update token manager
                            chatTokenManager.updateTokens(newAccessToken, newRefreshToken)

                            //update local prefs
                            sdkPreferences.setAccessToken(newAccessToken)
                            sdkPreferences.setRefreshToken(newRefreshToken)

                            //through callback
                            lmInternalCallback?.onAccessTokenExpiredAndRefreshed(
                                newAccessToken,
                                newRefreshToken
                            )

                            //retry api
                            response.request.newBuilder()
                                .header(AUTH, updatedToken)
                                .build()
                        }
                    }
                }
            } else {
                Log.d(LOG_TAG, "new refresh token required")
                chatTokenManager.clear()
                val tokens = lmInternalCallback?.onRefreshTokenExpired()

                val newAccessToken = tokens?.first
                val newRefreshToken = tokens?.second

                if (!newAccessToken.isNullOrEmpty() || !newRefreshToken.isNullOrEmpty()) {
                    //update token manager
                    chatTokenManager.updateTokens(newAccessToken, newRefreshToken)

                    //update local prefs
                    sdkPreferences.setAccessToken(newAccessToken ?: "")
                    sdkPreferences.setRefreshToken(newRefreshToken ?: "")

                    response.request.newBuilder()
                        .header(AUTH, newRefreshToken ?: "")
                        .build()
                } else {
                    null
                }
            }
        } else {
            response.request
        }
    }
}