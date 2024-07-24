package com.likeminds.internalsdk.sdk.util

import android.app.Application
import com.likeminds.internalsdk.utils.sharedpreferences.BasePreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SDKPreferences @Inject constructor(
    application: Application
) : BasePreferences(SDK_PREFS, application) {

    companion object {

        const val SDK_PREFS = "sdk_prefs"
        private const val COMMUNITY_ID = "community_id"

        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val API_KEY = "api_key"
    }

    fun setCommunityId(communityId: String) {
        putPreference(COMMUNITY_ID, communityId)
    }

    fun getCommunityId(): String? {
        return getPreference(COMMUNITY_ID, "")
    }

    fun setAccessToken(accessToken: String) {
        putPreference(ACCESS_TOKEN, accessToken)
    }

    fun getAccessToken(): String? {
        return getPreference(ACCESS_TOKEN, null)
    }

    fun setRefreshToken(refreshToken: String) {
        putPreference(REFRESH_TOKEN, refreshToken)
    }

    fun getRefreshToken(): String? {
        return getPreference(REFRESH_TOKEN, null)
    }

    fun setAPIKey(apiKey: String) {
        putPreference(API_KEY, apiKey)
    }

    fun getAPIKey(): String? {
        return getPreference(API_KEY, null)
    }
}