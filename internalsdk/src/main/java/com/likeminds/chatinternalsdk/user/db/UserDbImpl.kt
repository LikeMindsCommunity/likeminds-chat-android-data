package com.likeminds.chatinternalsdk.user.db

import com.likeminds.chatinternalsdk.db.models.UserRO
import com.likeminds.chatinternalsdk.user.UserReceiver
import io.realm.Realm
import javax.inject.Inject

class UserDbImpl @Inject constructor(private val userReceiver: UserReceiver) : UserDB {

    override fun saveUser(userRO: UserRO) {
        return userReceiver.saveUser(userRO)
    }

    override fun getUser(realm: Realm): UserRO? {
        return userReceiver.getUser(realm)
    }
}