package com.likeminds.chatinternalsdk.di.modules

import com.likeminds.chatinternalsdk.di.WebSocketQualifier
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import com.likeminds.chatinternalsdk.websocket.LMChatWebSocketManager
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class WebSocketModule {

    @Provides
    @Singleton
    fun provideWebSocketModule(
        @WebSocketQualifier client: OkHttpClient,
        baseUrl: BaseUrl
    ): LMChatWebSocketManager {
        return LMChatWebSocketManager(client, baseUrl)
    }
}