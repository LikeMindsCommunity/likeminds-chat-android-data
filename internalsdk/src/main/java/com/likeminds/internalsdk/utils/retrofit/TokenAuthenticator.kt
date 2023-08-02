package com.likeminds.internalsdk.utils.retrofit

import android.util.Log
import com.likeminds.internalsdk.ChatTokenManager
import com.likeminds.internalsdk.GroupChatSDK.Companion.LOG_TAG
import com.likeminds.internalsdk.refreshtoken.RefreshTokenNetworkApi
import com.likeminds.internalsdk.sdk.util.SDKPreferences
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import kotlinx.coroutines.runBlocking
import okhttp3.*
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val refreshNetworkApi: RefreshTokenNetworkApi,
    private val sdkPreferences: SDKPreferences
) : Authenticator {

    companion object {

        private const val AUTH = "Authorization"
        const val INVALID_LTM = "Invalid LTM!"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        return getUpdatedRequest(response)
    }

    private fun getUpdatedRequest(response: Response): Request? {
        val body = response.body?.string()
        val chatTokenManager = ChatTokenManager.getInstance()
        return when {
            chatTokenManager.refreshToken.isNullOrEmpty() &&
                    sdkPreferences.getRefreshToken().isEmpty() -> {
                Log.e(LOG_TAG, "refresh token is empty")
                null
            }

            (body?.contains(INVALID_LTM, true) == true) -> {
                Log.d(LOG_TAG, "refreshing access token")
                runBlocking {

                    val refreshToken = if (!chatTokenManager.refreshToken.isNullOrEmpty()) {
                        chatTokenManager.refreshToken
                    } else {
                        sdkPreferences.getRefreshToken()
                    }

                    when (val refreshResponse =
                        refreshNetworkApi.refreshAccessToken("Bearer $refreshToken")) {
                        is NetworkResponse.Error -> {
                            Log.d(
                                LOG_TAG,
                                "access token refresh failed: ${refreshResponse.body.errorMessage}"
                            )
                            null
                        }

                        is NetworkResponse.Success -> {
                            Log.d(LOG_TAG, "access token refreshed")
                            val newAccessToken = refreshResponse.body.data?.accessToken
                            val newRefreshToken = refreshResponse.body.data?.refreshToken
                            val updatedToken = "Bearer $newAccessToken"

                            chatTokenManager.updateTokens(newAccessToken, newRefreshToken)
                            response.request.newBuilder()
                                .header(AUTH, updatedToken)
                                .build()
                        }
                    }
                }
            }

            else -> {
                null
            }
        }
    }
}