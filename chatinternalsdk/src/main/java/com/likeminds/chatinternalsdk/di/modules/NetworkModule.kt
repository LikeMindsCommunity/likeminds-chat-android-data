package com.likeminds.chatinternalsdk.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.*
import com.likeminds.chatinternalsdk.BuildConfig
import com.likeminds.chatinternalsdk.utils.retrofit.CommonHeaderInterceptor
import com.likeminds.chatinternalsdk.utils.retrofit.RetryInterceptor
import com.likeminds.chatinternalsdk.utils.retrofit.TokenAuthenticator
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
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        commonHeaderInterceptor: CommonHeaderInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        dispatcher: Dispatcher,
        retryInterceptor: RetryInterceptor,
//        chuckerInterceptor: ChuckerInterceptor
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
        clientBuilder.addInterceptor(retryInterceptor)
//        clientBuilder.addInterceptor(chuckerInterceptor)

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

//    @Provides
//    @Singleton
//    fun provideChuckInterceptor(context: Context): ChuckerInterceptor {
//        val collector = ChuckerCollector(context, true, RetentionManager.Period.ONE_WEEK)
//        return ChuckerInterceptor.Builder(context)
//            .collector(collector)
//            .alwaysReadResponseBody(false)
//            .build()
//    }
}