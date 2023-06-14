package com.likeminds.internalsdk.di.modules

import com.google.gson.Gson
import com.likeminds.internalsdk.chatroom.ChatroomNetworkApi
import com.likeminds.internalsdk.sync.api.chatroom.ChatroomSyncNetworkApi
import com.likeminds.internalsdk.utils.retrofit.NetworkResponseAdapterFactory
import com.likeminds.internalsdk.utils.retrofit.model.BaseUrl
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
        client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): ChatroomNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBase())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .build()
            .create(ChatroomNetworkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideChatroomSyncModule(
        client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): ChatroomSyncNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBase())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .build()
            .create(ChatroomSyncNetworkApi::class.java)
    }
}