package com.likeminds.likemindschat

import androidx.annotation.Keep

@Keep
interface LMChatSDKCallback {
    fun onAccessTokenExpiredAndRefreshed(accessToken: String, refreshToken: String)
    fun onRefreshTokenExpired(): Pair<String?, String?>
}