package com.likeminds.internalsdk.user.db

import com.likeminds.internalsdk.db.models.UserRO
import com.likeminds.internalsdk.user.UserReceiver
import javax.inject.Inject

class UserDbImpl @Inject constructor(private val userReceiver: UserReceiver) : UserDB {

    override fun saveUser(userRO: UserRO) {
        return userReceiver.saveUser(userRO)
    }

    override suspend fun getUser(): UserRO? {
        return userReceiver.getUser()
    }
}