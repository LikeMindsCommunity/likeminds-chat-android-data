package com.likeminds.internalsdk.user.db

import com.likeminds.internalsdk.db.models.UserRO

interface UserDB {

    //save user in local db
    fun saveUser(userRO: UserRO)

    //get user object
    suspend fun getUser(): UserRO?
}