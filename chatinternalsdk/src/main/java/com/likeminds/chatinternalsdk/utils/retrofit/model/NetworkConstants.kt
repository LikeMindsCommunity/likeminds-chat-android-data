package com.likeminds.chatinternalsdk.utils.retrofit.model

object NetworkConstants {
    // error codes
    const val UNAUTHORIZED = 401
    const val SERVER_ERROR = 500
    const val BAD_GATEWAY = 502
    const val SERVICE_UNAVAILABLE = 503
    const val GATEWAY_TIMEOUT = 504
    const val TOO_MANY_REQUESTS = 429

    //headers
    const val X_PLATFORM_CODE = "x-platform-code"
    const val X_SDK_SOURCE = "x-sdk-source"
    const val X_VERSION_CODE = "x-version-code"
    const val AUTH = "Authorization"
}