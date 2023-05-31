package com.likeminds.likemindschat.user

import com.likeminds.likemindschat.LMResponse
import com.likeminds.likemindschat.base.BaseClient
import com.likeminds.likemindschat.sdk.LikeMindsChatApplication
import com.likeminds.likemindschat.sdk.ModelConverter
import com.likeminds.likemindschat.user.model.User
import javax.inject.Inject

class UserClient @Inject constructor() : BaseClient() {

    override fun attachDagger() {
        LikeMindsChatApplication.getInstance().userComponent()?.inject(this)
    }

    private val userDb by lazy {
        groupChatSDK.getUserDb()
    }

    suspend fun getUser(): LMResponse<User> {
        return LMResponse(
            success = true,
            null,
            ModelConverter.convertUser(userDb.getUser())
        )
    }
}