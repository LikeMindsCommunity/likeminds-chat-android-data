package com.likeminds.samplechatapp

import android.app.Application
import com.likeminds.likemindschat.LMChatClient

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val client = LMChatClient.Builder(this).build()
    }
}