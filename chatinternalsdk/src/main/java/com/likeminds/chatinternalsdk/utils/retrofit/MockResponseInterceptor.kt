package com.likeminds.chatinternalsdk.utils.retrofit

import okhttp3.*
import javax.inject.Inject

// to be used for testing only!
class MockResponseInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
            .newBuilder()
            .code(502)
            .protocol(Protocol.HTTP_2)
            .message("Bad Gateway")
            .addHeader("content-type", "application/json")
            .build()
    }
}