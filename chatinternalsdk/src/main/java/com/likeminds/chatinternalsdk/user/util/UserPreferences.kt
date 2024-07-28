package com.likeminds.chatinternalsdk.user.util

import android.app.Application
import com.likeminds.chatinternalsdk.utils.sharedpreferences.BasePreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    application: Application
) : BasePreferences(USER_PREFS, application) {

    companion object {

        const val USER_PREFS = "user_prefs"
        private const val LM_UUID = "lm_uuid"
        private const val LM_MEMBER_ID = "lm_member_id"
        private const val CLIENT_UUID = "client_uuid"
    }

    fun setLMUUID(lmUUID: String) {
        putPreference(LM_UUID, lmUUID)
    }

    fun getLMUUID(): String {
        return getPreference(LM_UUID, "") ?: ""
    }

    fun setLMMemberId(lmMemberId: String) {
        putPreference(LM_MEMBER_ID, lmMemberId)
    }

    fun getLMMemberId(): String {
        return getPreference(LM_MEMBER_ID, "") ?: ""
    }

    fun setClientUUID(clientUUID: String) {
        putPreference(CLIENT_UUID, clientUUID)
    }

    fun getClientUUID(): String {
        return getPreference(CLIENT_UUID, "") ?: ""
    }
}