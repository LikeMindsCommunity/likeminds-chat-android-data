package com.likeminds.chatinternalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class ReactionRO(
    var member: MemberRO? = null,
    var reaction: String = ""
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.member,
        builder.reaction
    )

    companion object {

        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder() {

        var member: MemberRO? = null
        var reaction: String = ""

        fun build() = ReactionRO(this)
    }

}