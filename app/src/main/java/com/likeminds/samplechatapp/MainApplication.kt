package com.likeminds.samplechatapp

import android.app.Application
import com.likeminds.likemindschat.LMChatClient
import com.likeminds.likemindschat.LMChatSDKCallback
import kotlinx.coroutines.*

class MainApplication : Application(), LMChatSDKCallback {

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.IO).launch {
            val client = LMChatClient.Builder(this@MainApplication)
                .lmChatSDKCallback(this@MainApplication)
                .build()
        }
    }

    override fun onAccessTokenExpiredAndRefreshed(accessToken: String, refreshToken: String) {

    }

    override fun onRefreshTokenExpired(): Pair<String?, String?> {
        return Pair("", "")
    }
}