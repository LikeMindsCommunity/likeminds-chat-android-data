package com.likeminds.samplechatapp

import android.app.Application
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.LMChatSDKCallback

class MainApplication : Application(), LMChatSDKCallback {

    override fun onCreate() {
        super.onCreate()
        val client = LMChatClient.Builder(this)
            .lmChatSDKCallback(this)
            .build()
    }

    override fun onAccessTokenExpiredAndRefreshed(accessToken: String, refreshToken: String) {

    }

    override fun onRefreshTokenExpired(): Pair<String?, String?> {
        return Pair("", "")
    }
}