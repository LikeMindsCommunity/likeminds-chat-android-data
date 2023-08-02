package com.likeminds.internalsdk.db.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class AppConfigRO(
    @PrimaryKey
    var id: Int = 0,
    @Required
    var communities: RealmList<String> = RealmList(),
    var isConversationsSynced: Boolean = false,
    var isChatroomsSynced: Boolean = false,
    var isCommunitiesSynced: Boolean = false
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.id,
        builder.communities,
        builder.isConversationsSynced,
        builder.isChatroomsSynced,
        builder.isCommunitiesSynced
    )

    companion object {

        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {

        var id: Int = 0
        var communities: RealmList<String> = RealmList()
        var isConversationsSynced: Boolean = false
        var isChatroomsSynced: Boolean = false
        var isCommunitiesSynced: Boolean = false

        fun build() = AppConfigRO(this)
    }

    fun toBuilder(): Builder {
        return Builder().apply {
            communities = this@AppConfigRO.communities
            isConversationsSynced = this@AppConfigRO.isConversationsSynced
            isChatroomsSynced = this@AppConfigRO.isChatroomsSynced
            isCommunitiesSynced = this@AppConfigRO.isCommunitiesSynced
        }
    }

}

