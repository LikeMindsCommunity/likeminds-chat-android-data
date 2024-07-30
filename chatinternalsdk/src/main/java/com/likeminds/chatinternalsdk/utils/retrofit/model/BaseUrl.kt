package com.likeminds.chatinternalsdk.utils.retrofit.model

import com.likeminds.chatinternalsdk.BuildConfig

class BaseUrl {

    //"https://auth.likeminds.community/"
    fun getKettleBase(): String {
        return BuildConfig.URLS_MAP[BuildConfig.BASE_URL].toString()
    }
}