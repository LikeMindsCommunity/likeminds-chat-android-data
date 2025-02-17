package com.likeminds.chatinternalsdk.di.modules

import com.google.gson.Gson
import com.likeminds.chatinternalsdk.chatroom.api.ChatroomNetworkApi
import com.likeminds.chatinternalsdk.di.HttpAPICallQualifier
import com.likeminds.chatinternalsdk.sync.api.chatroom.ChatroomSyncNetworkApi
import com.likeminds.chatinternalsdk.utils.retrofit.NetworkResponseAdapterFactory
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ChatroomModule {

    @Provides
    @Singleton
    fun provideChatroomModule(
        @HttpAPICallQualifier client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): ChatroomNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBaseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .build()
            .create(ChatroomNetworkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChatroomSyncModule(
        @HttpAPICallQualifier client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): ChatroomSyncNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBaseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .build()
            .create(ChatroomSyncNetworkApi::class.java)
    }
}