package com.likeminds.chatinternalsdk.di.modules

import com.google.gson.Gson
import com.likeminds.chatinternalsdk.di.HttpAPICallQualifier
import com.likeminds.chatinternalsdk.homefeed.api.HomeFeedNetworkApi
import com.likeminds.chatinternalsdk.utils.retrofit.NetworkResponseAdapterFactory
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class HomeFeedModule {

    @Provides
    @Singleton
    fun provideHomeFeedModule(
        @HttpAPICallQualifier client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): HomeFeedNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBaseUrl())
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(HomeFeedNetworkApi::class.java)
    }
}