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
    }

    fun setCommunityId(communityId: String) {
        putPreference(COMMUNITY_ID, communityId)
    }

    fun getCommunityId(): String? {
        return getPreference(COMMUNITY_ID, "")
    }
}