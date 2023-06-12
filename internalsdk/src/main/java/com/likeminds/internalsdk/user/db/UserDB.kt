package com.likeminds.internalsdk.user.db

import com.likeminds.internalsdk.db.models.UserRO

interface UserDB {

    fun saveUser(userRO: UserRO)

    suspend fun getUser(): UserRO?
}