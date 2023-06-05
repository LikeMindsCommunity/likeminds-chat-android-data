package com.likeminds.internalsdk.db

import android.util.Log
import io.realm.kotlin.MutableRealm
import io.realm.kotlin.Realm
import java.util.concurrent.atomic.AtomicInteger

object ChatDBUtil {

    private val ONGOING_WRITE_TRANSACTION = AtomicInteger(0)
    suspend fun write(realm: Realm, block: (realm: MutableRealm) -> Unit): Boolean {
        ONGOING_WRITE_TRANSACTION.incrementAndGet()
        return try {
            realm.write {
                block(this)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Realm Write", "", e)
            false
        } finally {
            ONGOING_WRITE_TRANSACTION.decrementAndGet()
        }
    }
}