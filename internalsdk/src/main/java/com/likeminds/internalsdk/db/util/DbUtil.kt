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
//    Add option for primitive data types too
//    if (this !is RealmObject) {
//        throw Exception("Cannot convert non realm model to realm list")
//    }
    return RealmList<T>(this)
}