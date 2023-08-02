package com.likeminds.internalsdk.di.modules

import com.google.gson.Gson
import com.likeminds.internalsdk.homefeed.api.HomeFeedNetworkApi
import com.likeminds.internalsdk.utils.retrofit.NetworkResponseAdapterFactory
import com.likeminds.internalsdk.utils.retrofit.model.BaseUrl
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
        client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): HomeFeedNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBase())
            .client(client)
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(HomeFeedNetworkApi::class.java)
    }
}