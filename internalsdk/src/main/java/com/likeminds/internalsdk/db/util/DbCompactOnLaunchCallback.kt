package com.likeminds.internalsdk.db.util

import io.realm.DefaultCompactOnLaunchCallback

class DbCompactOnLaunchCallback : DefaultCompactOnLaunchCallback() {

    override fun hashCode(): Int {
        return 20
    }

    override fun equals(other: Any?): Boolean {
        return other is DbCompactOnLaunchCallback
    }

}