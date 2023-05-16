package com.likeminds.internalsdk.utils.retrofit

import com.likeminds.internalsdk.BuildConfig
import com.likeminds.internalsdk.ChatTokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CommonHeaderInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val feedTokenManager = ChatTokenManager.getInstance()
        if (!feedTokenManager.accessToken.isNullOrEmpty()) {
            requestBuilder.addHeader(AUTH, "Bearer ${feedTokenManager.accessToken}")
        }
        requestBuilder.addHeader(X_PLATFORM_CODE, "an")
        requestBuilder.addHeader(X_SDK_SOURCE, "feed")
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