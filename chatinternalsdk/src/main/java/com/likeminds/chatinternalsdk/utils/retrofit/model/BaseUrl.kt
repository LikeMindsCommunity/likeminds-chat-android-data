package com.likeminds.chatinternalsdk.utils.retrofit.model

import com.likeminds.chatinternalsdk.BuildConfig

class BaseUrl {

    //"https://auth.likeminds.community/"
    fun getKettleBaseUrl(): String {
        return BuildConfig.URLS_MAP[BuildConfig.BASE_URL].toString()
    }

    //wss://auth-ws.likeminds.community/
    fun getPandemoniumBaseUrl(): String {
        return BuildConfig.URLS_MAP[BuildConfig.BASE_WEB_SOCKET_URL].toString()
    }
}