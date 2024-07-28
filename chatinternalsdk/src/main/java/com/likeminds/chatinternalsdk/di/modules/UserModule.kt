package com.likeminds.chatinternalsdk.di.modules

import com.google.gson.Gson
import com.likeminds.chatinternalsdk.user.api.UserNetworkApi
import com.likeminds.chatinternalsdk.utils.retrofit.NetworkResponseAdapterFactory
import com.likeminds.chatinternalsdk.utils.retrofit.model.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class UserModule {

    @Provides
    @Singleton
    fun provideUserModule(
        client: OkHttpClient,
        gson: Gson,
        baseUrl: BaseUrl
    ): UserNetworkApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl.getKettleBase())
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory(gson))
            .build()
            .create(UserNetworkApi::class.java)
    }
}