package com.likeminds.chatinternalsdk.db.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CommunityRO(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var imageUrl: String? = null,
    var membersCount: Int? = null,
    var updatedAt: Long? = null,
    var relationshipNeeded: Boolean = true,
    var downloadableContentTypes: RealmList<String>? = null,

    var conversations: RealmList<ConversationRO> = RealmList(),
    var chatrooms: RealmList<ChatroomRO> = RealmList()
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.id,
        builder.name,
        builder.imageUrl,
        builder.membersCount,
        builder.updatedAt,
        builder.relationshipNeeded,
        builder.downloadableContentTypes,
        builder.conversations,
        builder.chatrooms
    )

    companion object {

        inline fun build(id: String, block: Builder.() -> Unit) = Builder(id).apply(block).build()
    }

    class Builder(var id: String) {

        var name: String = ""
        var imageUrl: String? = null
        var membersCount: Int? = null
        var updatedAt: Long? = null
        var relationshipNeeded: Boolean = true
        var conversations: RealmList<ConversationRO> = RealmList()
        var downloadableContentTypes: RealmList<String>? = null
        var chatrooms: RealmList<ChatroomRO> = RealmList()

        fun build() = CommunityRO(this)
    }

    fun toBuilder(): Builder {
        return Builder(id).apply {
            name = this@CommunityRO.name
            imageUrl = this@CommunityRO.imageUrl
            membersCount = this@CommunityRO.membersCount
            updatedAt = this@CommunityRO.updatedAt
            relationshipNeeded = this@CommunityRO.relationshipNeeded
            conversations = this@CommunityRO.conversations
            downloadableContentTypes = this@CommunityRO.downloadableContentTypes
            chatrooms = this@CommunityRO.chatrooms
        }
    }

}