package com.likeminds.internalsdk.db.util

import io.realm.DefaultCompactOnLaunchCallback

class DbCompactOnLaunchCallback : DefaultCompactOnLaunchCallback() {

    companion object {

        const val HASH_CODE = 20
    }

    override fun hashCode(): Int {
        return HASH_CODE
    }

    override fun equals(other: Any?): Boolean {
        return other is DbCompactOnLaunchCallback
    }

}