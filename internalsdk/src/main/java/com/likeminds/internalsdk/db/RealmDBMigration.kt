package com.likeminds.internalsdk.db

import android.util.Log
import io.realm.DynamicRealm
import io.realm.RealmMigration

class RealmDBMigration : RealmMigration {

    companion object {
        private const val CHATROOM_CLASS = "ChatroomRO"
        private const val MEMBER_CLASS = "MemberRO"
    }

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var olderVersion = oldVersion
        val schema = realm.schema
        Log.d(
            "Migration",
            "Old version - $oldVersion, New version - $newVersion, Realm Name - ${realm.configuration.realmFileName}"
        )
        if (oldVersion == 1L) {
            //DM related
            schema[CHATROOM_CLASS]!!.apply {
                addField("chatRequestState", Int::class.javaObjectType)
                addField("isPrivateMember", Boolean::class.javaObjectType)
                addField("chatRequestedById", String::class.javaObjectType)
                addField("chatroomWithUserId", String::class.javaObjectType)
                addField("chatRequestCreatedAt", Long::class.javaObjectType)
                addRealmObjectField("chatRequestedBy", schema[MEMBER_CLASS]!!)
                addRealmObjectField("chatroomWithUser", schema[MEMBER_CLASS]!!)
            }

            olderVersion++
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is RealmDBMigration
    }

    override fun hashCode(): Int {
        return 37
    }
}

const val DB_SCHEMA_NAME = "likeminds-chat-sdk"
const val DB_SCHEMA_VERSION = 2L