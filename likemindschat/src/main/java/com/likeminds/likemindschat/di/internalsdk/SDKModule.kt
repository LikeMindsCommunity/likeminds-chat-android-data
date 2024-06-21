package com.likeminds.likemindschat.di.internalsdk

import com.google.gson.Gson
import com.likeminds.internalsdk.LMChatSDK
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class SDKModule {

    @Provides
    @Singleton
    fun provideInternalSDK(): LMChatSDK {
        return LMChatSDK.getInstance()
    }

    @Provides
    @Singleton
    fun provideGson(chatSDK: LMChatSDK): Gson {
        return chatSDK.gson
    }
}