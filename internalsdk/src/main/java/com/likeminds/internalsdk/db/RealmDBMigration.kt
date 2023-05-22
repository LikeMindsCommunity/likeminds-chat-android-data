package com.likeminds.internalsdk.db

import io.realm.kotlin.dynamic.DynamicMutableRealmObject
import io.realm.kotlin.dynamic.DynamicRealmObject
import io.realm.kotlin.migration.AutomaticSchemaMigration
import io.realm.kotlin.migration.RealmMigration

class RealmDBMigration : AutomaticSchemaMigration {

    override fun migrate(migrationContext: AutomaticSchemaMigration.MigrationContext) {
        var oldVersion = migrationContext.oldRealm.schemaVersion()
        var newVersion = migrationContext.newRealm.schemaVersion()


    }
}

const val DB_SCHEMA_NAME = "likeminds-chat-sdk"
const val DB_SCHEMA_VERSION = 1L