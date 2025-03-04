package com.likeminds.chatinternalsdk.di.modules

import com.google.gson.Gson
import com.likeminds.chatinternalsdk.BuildConfig
import com.likeminds.chatinternalsdk.refreshtoken.RefreshTokenNetworkApi
import com.likeminds.chatinternalsdk.sdk.SDKNetworkApi
import com.likeminds.chatinternalsdk.utils.retrofit.NetworkResponseAdapterFactory
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class SDKModule {

    @Provides
    @Singleton
    fun provideSDKModule(
        client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): SDKNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBase())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .build()
            .create(SDKNetworkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRefreshTokenApi(
        client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl,
    ): RefreshTokenNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBase())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .build()
            .create(RefreshTokenNetworkApi::class.java)
    }
}