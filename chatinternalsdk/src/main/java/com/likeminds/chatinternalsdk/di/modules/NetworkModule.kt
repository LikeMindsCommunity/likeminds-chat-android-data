package com.likeminds.chatinternalsdk.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.*
import com.likeminds.chatinternalsdk.BuildConfig
import com.likeminds.chatinternalsdk.di.HttpAPICallQualifier
import com.likeminds.chatinternalsdk.di.WebSocketQualifier
import com.likeminds.chatinternalsdk.utils.retrofit.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.Dispatcher
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
    @HttpAPICallQualifier
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        commonHeaderInterceptor: CommonHeaderInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        dispatcher: Dispatcher,
        chuckerInterceptor: ChuckerInterceptor
    ): OkHttpClient {
        //create okhttp client
        val clientBuilder = OkHttpClient.Builder()

        //set timeouts
        clientBuilder.readTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)

        //set dispatcher
        clientBuilder.dispatcher(dispatcher)

        //set authenticator
        clientBuilder.authenticator(tokenAuthenticator)
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(loggingInterceptor)
        }
        clientBuilder.addInterceptor(commonHeaderInterceptor)
        clientBuilder.addInterceptor(chuckerInterceptor)

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    @WebSocketQualifier
    fun provideWebSocketOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        mockResponseInterceptor: MockResponseInterceptor,
        dispatcher: Dispatcher
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()

        //add ping-pong interval
        clientBuilder.pingInterval(20, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        //add interceptors
        clientBuilder.addInterceptor(loggingInterceptor)
        clientBuilder.addInterceptor(chuckerInterceptor)

        //set dispatcher
        clientBuilder.dispatcher(dispatcher)

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpsDispatcher(): Dispatcher {
        val dispatcher = Dispatcher()

        dispatcher.maxRequests = 15
        dispatcher.maxRequestsPerHost = 15

        return dispatcher
    }

    @Provides
    @Singleton
    fun provideChuckInterceptor(context: Context): ChuckerInterceptor {
        val collector = ChuckerCollector(context, true, RetentionManager.Period.ONE_WEEK)
        return ChuckerInterceptor.Builder(context)
            .collector(collector)
            .alwaysReadResponseBody(false)
            .build()
    }
}