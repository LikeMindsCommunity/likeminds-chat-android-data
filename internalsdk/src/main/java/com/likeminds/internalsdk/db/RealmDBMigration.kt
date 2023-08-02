package com.likeminds.internalsdk.db

import io.realm.DynamicRealm
import io.realm.RealmMigration

class RealmDBMigration : RealmMigration {

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var olderVersion = oldVersion
        val schema = realm.schema
    }

}

const val DB_SCHEMA_NAME = "likeminds-chat-sdk"
const val DB_SCHEMA_VERSION = 1L