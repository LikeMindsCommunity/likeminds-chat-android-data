package com.likeminds.chatinternalsdk.db

import android.util.Log
import com.likeminds.chatinternalsdk.db.util.DbKey.UPLOAD_WORKER_UUID
import com.likeminds.chatinternalsdk.db.util.DbKey.WORKER_UUID
import io.realm.*

class RealmDBMigration : RealmMigration {

    companion object {
        private const val CHATROOM_CLASS = "ChatroomRO"
        private const val MEMBER_CLASS = "MemberRO"
        private const val WIDGET_CLASS = "WidgetRO"
        private const val CONVERSATION_CLASS = "ConversationRO"
        private const val LAST_CONVERSATION_CLASS = "LastConversationRO"
        private const val USER_CLASS = "UserRO"
        private const val LM_LOG_CLASS = "LMLogRO"
        private const val LM_SDK_META_CLASS = "LMSDKMetaRO"
        private const val LM_STACK_TRACE_CLASS = "LMStackTraceRO"
    }

    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        var olderVersion = oldVersion
        val schema = realm.schema
        Log.d(
            "Migration",
            "Old version - $oldVersion, New version - $newVersion, Realm Name - ${realm.configuration.realmFileName}"
        )
        if (olderVersion == 1L) {
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

            //add widget class
            val widgetSchema = schema.create(WIDGET_CLASS)
                .addField("id", String::class.java, FieldAttribute.REQUIRED)
                .addField("parentEntityId", String::class.java, FieldAttribute.REQUIRED)
                .addField("parentEntityType", String::class.java, FieldAttribute.REQUIRED)
                .addField("metadata", String::class.javaObjectType)
                .addField("createdAt", Long::class.java, FieldAttribute.REQUIRED)
                .addField("updatedAt", Long::class.java, FieldAttribute.REQUIRED)

            schema[CONVERSATION_CLASS]!!.addRealmObjectField("widgetRO", widgetSchema)
                .addField("widgetId", String::class.javaObjectType)

            schema[LAST_CONVERSATION_CLASS]!!.addRealmObjectField("widgetRO", widgetSchema)
                .addField("widgetId", String::class.javaObjectType)

            widgetSchema.isEmbedded = true

            olderVersion++
        }

        if (olderVersion == 2L) {
            schema[CHATROOM_CLASS]!!.apply {
                removeField("state")
                addField("state", Int::class.javaObjectType)
            }
            schema[MEMBER_CLASS]!!.apply {
                removeField("isOwner")
                addField("isOwner", Boolean::class.javaObjectType)
            }

            olderVersion++
        }

        if (olderVersion == 3L) {
            schema[USER_CLASS]!!.apply {
                addRealmListField("roles", String::class.javaObjectType)
            }
            schema[MEMBER_CLASS]!!.apply {
                addRealmListField("roles", String::class.javaObjectType)
            }

            olderVersion++
        }

        if (olderVersion == 4L) {
            schema[CHATROOM_CLASS]!!.apply {
                addField("conversationSyncMinTimestamp", Long::class.javaObjectType)
            }

            olderVersion++
        }

        if (olderVersion == 5L) {
            schema[CONVERSATION_CLASS]!!.apply {
                addField("attachmentsUploadedEpoch", Long::class.javaObjectType)
                renameField(UPLOAD_WORKER_UUID, WORKER_UUID)
            }

            schema[LAST_CONVERSATION_CLASS]!!.apply {
                renameField(UPLOAD_WORKER_UUID, WORKER_UUID)
            }

            olderVersion++
        }

        if (olderVersion == 6L) {
            val sdkMetaSchema = schema.create(LM_SDK_META_CLASS)
                .addField("dataLayerVersion", String::class.javaObjectType)
                .addField("coreVersion", String::class.javaObjectType)

            val stackTraceSchema = schema.create(LM_STACK_TRACE_CLASS)
                .addField("exception", String::class.java, FieldAttribute.REQUIRED)
                .addField("trace", String::class.java, FieldAttribute.REQUIRED)

            schema.create(LM_LOG_CLASS).apply {
                addField("timestamp", Long::class.java, FieldAttribute.REQUIRED)
                addRealmObjectField("stackTrace", stackTraceSchema)
                addRealmObjectField("sdkMeta", sdkMetaSchema)
            }

            sdkMetaSchema.isEmbedded = true
            stackTraceSchema.isEmbedded = true

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
const val DB_SCHEMA_VERSION = 7L