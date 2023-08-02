package com.likeminds.internalsdk.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GsonModule {

    @Provides
    @Singleton
    fun provideFinalGson(): Gson {
        return GsonBuilder().create()
    }
}