package com.likeminds.chatinternalsdk

interface LMChatInternalCallback {
    fun onAccessTokenExpiredAndRefreshed(accessToken: String, refreshToken: String)
    fun onRefreshTokenExpired(): Pair<String?, String?>
}