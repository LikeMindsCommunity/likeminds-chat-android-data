package com.likeminds.chatinternalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class LMMetaRO(
    var sourceChatroomId: String? = null,
    var sourceChatroomName: String? = null,
    var sourceConversation: ConversationRO? = null,
    var type: String? = null
) : RealmObject() {


    private constructor(builder: Builder) : this(
        builder.sourceChatroomId,
        builder.sourceChatroomName,
        builder.sourceConversation,
        builder.type
    )

    companion object {
        inline fun build(
            block: Builder.() -> Unit
        ) = Builder().apply(block).build()
    }

    class Builder() {
        var sourceChatroomId: String? = null
        var sourceChatroomName: String? = null
        var sourceConversation: ConversationRO? = null
        var type: String? = null

        fun build() = LMMetaRO(this)
    }

    fun toBuilder(): Builder {
        return Builder().apply {
            sourceChatroomId = this@LMMetaRO.sourceChatroomId
            sourceChatroomName = this@LMMetaRO.sourceChatroomName
            sourceConversation = this@LMMetaRO.sourceConversation
            type = this@LMMetaRO.type
        }
    }
}