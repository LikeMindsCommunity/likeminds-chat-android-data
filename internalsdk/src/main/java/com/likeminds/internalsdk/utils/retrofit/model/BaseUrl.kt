package com.likeminds.internalsdk.utils.retrofit.model

import com.likeminds.internalsdk.BuildConfig

class BaseUrl {

    //"https://auth.likeminds.community/"
    fun getKettleBase(): String {
        return BuildConfig.URLS_MAP[BuildConfig.BASE_URL].toString()
    }
}