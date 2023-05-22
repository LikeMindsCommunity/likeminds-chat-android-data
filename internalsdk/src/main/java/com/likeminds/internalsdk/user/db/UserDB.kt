package com.likeminds.internalsdk.user.db

import com.likeminds.internalsdk.db.models.UserRO

interface UserDB {

    suspend fun saveUser(userRO: UserRO)
}