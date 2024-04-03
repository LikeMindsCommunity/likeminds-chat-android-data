package com.likeminds.internalsdk.di.modules

import com.likeminds.internalsdk.BuildConfig
import com.likeminds.internalsdk.utils.retrofit.CommonHeaderInterceptor
import com.likeminds.internalsdk.utils.retrofit.TokenAuthenticator
import com.likeminds.internalsdk.utils.retrofit.model.BaseUrl
import dagger.Module
import dagger.Provides
import io.sentry.android.okhttp.SentryOkHttpInterceptor
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
        sentryOkHttpInterceptor: SentryOkHttpInterceptor
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
        clientBuilder.addInterceptor(sentryOkHttpInterceptor)

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
    fun provideSentryInterceptor(): SentryOkHttpInterceptor {
        return SentryOkHttpInterceptor()
    }
}