package com.likeminds.chatinternalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class LinkRO(
    var url: String = "",
    var chatroomId: String = "",
    var communityId: String = "",
    var title: String? = null,
    var image: String? = null,
    var description: String? = null
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.url,
        builder.chatroomId,
        builder.communityId,
        builder.title,
        builder.image,
        builder.description
    )

    companion object {

        inline fun build(url: String, block: Builder.() -> Unit) = Builder(url).apply(block).build()
    }

    class Builder(var url: String) {

        var chatroomId: String = ""
        var communityId: String = ""
        var title: String? = null
        var image: String? = null
        var description: String? = null

        fun build() = LinkRO(this)
    }
}