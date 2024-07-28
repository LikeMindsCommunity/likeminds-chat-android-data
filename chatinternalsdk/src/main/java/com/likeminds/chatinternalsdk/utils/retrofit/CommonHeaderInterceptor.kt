package com.likeminds.chatinternalsdk.utils.retrofit

import com.likeminds.chatinternalsdk.BuildConfig
import com.likeminds.chatinternalsdk.ChatTokenManager
import com.likeminds.chatinternalsdk.sdk.util.SDKPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CommonHeaderInterceptor @Inject constructor(
    private val sdkPreferences: SDKPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.toString()
        val requestBuilder = chain.request().newBuilder()
        val chatTokenManager = ChatTokenManager.getInstance()

        val accessToken = if (!chatTokenManager.accessToken.isNullOrEmpty()) {
            chatTokenManager.accessToken.toString()
        } else if (!sdkPreferences.getAccessToken().isNullOrEmpty()) {
            sdkPreferences.getAccessToken()
        } else {
            ""
        }

        if (!accessToken.isNullOrEmpty() && !url.contains("user/refresh", false)) {
            requestBuilder.addHeader(AUTH, "Bearer $accessToken")
        }
        requestBuilder.addHeader(X_PLATFORM_CODE, "an")
        requestBuilder.addHeader(X_SDK_SOURCE, "chat")
        requestBuilder.addHeader(X_VERSION_CODE, BuildConfig.APP_VERSION_CODE.toString())
        return chain.proceed(requestBuilder.build())
    }

    companion object {
        private const val X_PLATFORM_CODE = "x-platform-code"
        private const val X_SDK_SOURCE = "x-sdk-source"
        private const val X_VERSION_CODE = "x-version-code"
        private const val AUTH = "Authorization"
    }
}