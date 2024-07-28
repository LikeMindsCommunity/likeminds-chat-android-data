package com.likeminds.chatinternalsdk.user.db

import com.likeminds.chatinternalsdk.db.models.UserRO
import io.realm.Realm

interface UserDB {

    //save user in local db
    fun saveUser(userRO: UserRO)

    //get user object
    fun getUser(realm: Realm): UserRO?
}