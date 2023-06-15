package com.likeminds.internalsdk.db.util

import io.realm.RealmList

fun <T> List<T>?.toRealmList(): RealmList<T> {
    val list = RealmList<T>()
    if (!this.isNullOrEmpty()) {
        list.addAll(this)
    }
    return list
}

fun <T> T?.toRealmList(): RealmList<T> {
    return RealmList<T>(this)
}