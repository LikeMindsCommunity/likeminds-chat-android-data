package com.likeminds.chatinternalsdk.di.modules

import com.google.gson.Gson
import com.likeminds.chatinternalsdk.di.HttpAPICallQualifier
import com.likeminds.chatinternalsdk.helper.api.HelperNetworkApi
import com.likeminds.chatinternalsdk.utils.retrofit.NetworkResponseAdapterFactory
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class HelperModule {

    @Provides
    @Singleton
    fun provideHelperModule(
        @HttpAPICallQualifier client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): HelperNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBaseUrl())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .build()
            .create(HelperNetworkApi::class.java)
    }
}