package com.likeminds.likemindschat

import android.app.Application
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import javax.inject.Singleton

@Singleton
class LMChatClient private constructor() {

    class Builder(val application: Application) {
        fun build(): LMChatClient {
            lmChatClientInstance = LMChatClient()
            val sdkApplication = LikeMindsChatApplication.getInstance()
            sdkApplication.likeMindsChatComponent?.inject(lmChatClientInstance!!)
            return lmChatClientInstance!!
        }
    }

    companion object {
        @JvmStatic
        private var lmChatClientInstance: LMChatClient? = null


        @JvmStatic
        fun getInstance(): LMChatClient {
            if (lmChatClientInstance == null) {
                throw IllegalAccessException("LMChatClient is not created, please call LMChatClient.build()")
            }
            return lmChatClientInstance!!
        }
    }
}