package com.likeminds.internalsdk.user

import javax.inject.Inject

class UserApiImpl @Inject constructor(private val userReceiver: UserReceiver) : UserApi {

}