package com.likeminds.internalsdk.utils.retrofit

import com.likeminds.internalsdk.BuildConfig
import com.likeminds.internalsdk.ChatTokenManager
import com.likeminds.internalsdk.sdk.util.SDKPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CommonHeaderInterceptor @Inject constructor(
    private val sdkPreferences: SDKPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val chatTokenManager = ChatTokenManager.getInstance()

        val accessToken = if (!chatTokenManager.accessToken.isNullOrEmpty()) {
            chatTokenManager.accessToken.toString()
        } else if (sdkPreferences.getAccessToken().isNotEmpty()) {
            sdkPreferences.getAccessToken()
        } else {
            ""
        }
        if (accessToken.isNotEmpty()) {
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