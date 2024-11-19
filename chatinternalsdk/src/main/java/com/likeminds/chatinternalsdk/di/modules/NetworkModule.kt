package com.likeminds.chatinternalsdk.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.*
import com.likeminds.chatinternalsdk.BuildConfig
import com.likeminds.chatinternalsdk.utils.retrofit.CommonHeaderInterceptor
import com.likeminds.chatinternalsdk.utils.retrofit.TokenAuthenticator
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): BaseUrl {
        return BaseUrl()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        commonHeaderInterceptor: CommonHeaderInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .readTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
        clientBuilder.authenticator(tokenAuthenticator)
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(loggingInterceptor)
        }
        clientBuilder.addInterceptor(commonHeaderInterceptor)

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}