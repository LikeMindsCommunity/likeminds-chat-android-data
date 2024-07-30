package com.likeminds.chatinternalsdk

interface LMChatInternalCallback {
    // callback when access token is expired and refreshed
    fun onAccessTokenExpiredAndRefreshed(accessToken: String, refreshToken: String)

    // callback when refresh token is expired
    fun onRefreshTokenExpired(): Pair<String?, String?>
}