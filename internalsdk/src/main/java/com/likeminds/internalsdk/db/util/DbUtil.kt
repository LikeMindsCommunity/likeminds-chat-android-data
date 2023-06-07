package com.likeminds.internalsdk.db.util

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList

fun <T> List<T>?.toRealmList(): RealmList<T> {
    val list = realmListOf<T>()
    if (!this.isNullOrEmpty()) {
        list.addAll(this)
    }
    return list
}