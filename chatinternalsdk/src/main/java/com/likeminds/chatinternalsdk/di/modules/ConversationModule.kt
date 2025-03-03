package com.likeminds.chatinternalsdk.di.modules

import com.google.gson.Gson
import com.likeminds.chatinternalsdk.conversation.api.ConversationNetworkApi
import com.likeminds.chatinternalsdk.di.HttpAPICallQualifier
import com.likeminds.chatinternalsdk.sync.api.conversation.ConversationSyncNetworkApi
import com.likeminds.chatinternalsdk.utils.retrofit.NetworkResponseAdapterFactory
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ConversationModule {

    @Provides
    @Singleton
    fun provideConversationModule(
        @HttpAPICallQualifier client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): ConversationNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBaseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .build()
            .create(ConversationNetworkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConversationSyncModule(
        @HttpAPICallQualifier client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): ConversationSyncNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBaseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .build()
            .create(ConversationSyncNetworkApi::class.java)
    }
}