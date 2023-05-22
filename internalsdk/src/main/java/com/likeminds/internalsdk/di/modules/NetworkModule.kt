package com.likeminds.internalsdk.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.likeminds.internalsdk.utils.retrofit.CommonHeaderInterceptor
import com.likeminds.internalsdk.utils.retrofit.TokenAuthenticator
import com.likeminds.internalsdk.utils.retrofit.model.BaseUrl
import dagger.Module
import dagger.Provides
import io.sentry.android.okhttp.SentryOkHttpInterceptor
import okhttp3.OkHttpClient
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
        chuckerInterceptor: ChuckerInterceptor,
        commonHeaderInterceptor: CommonHeaderInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        sentryOkHttpInterceptor: SentryOkHttpInterceptor
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .readTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
        clientBuilder.authenticator(tokenAuthenticator)
        clientBuilder.addInterceptor(chuckerInterceptor)
        clientBuilder.addInterceptor(commonHeaderInterceptor)
        clientBuilder.addInterceptor(sentryOkHttpInterceptor)

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(context: Context): ChuckerInterceptor {
        val collector = ChuckerCollector(context, true, RetentionManager.Period.ONE_WEEK)
        return ChuckerInterceptor.Builder(context)
            .collector(collector)
            .alwaysReadResponseBody(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideSentryInterceptor(): SentryOkHttpInterceptor {
        return SentryOkHttpInterceptor()
    }
}