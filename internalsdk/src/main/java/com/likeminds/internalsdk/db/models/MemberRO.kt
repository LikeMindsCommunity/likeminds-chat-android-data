package com.likeminds.internalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MemberRO(
    @PrimaryKey
    var uid: String = "",
    var id: String = "",
    var name: String = "",
    var imageUrl: String = "",
    var state: Int = 0,
    var customIntroText: String? = null,
    var customClickText: String? = null,
    var customTitle: String? = null,
    var communityId: Int? = null,
    var isOwner: Boolean? = null,
    var isGuest: Boolean? = false,
    var userUniqueId: String? = null
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.uid,
        builder.id,
        builder.name,
        builder.imageUrl,
        builder.state,
        builder.customIntroText,
        builder.customClickText,
        builder.customTitle,
        builder.communityId,
        builder.isOwner,
        builder.isGuest,
        builder.userUniqueId
    )

    companion object {

        inline fun build(
            uid: String,
            id: String,
            block: Builder.() -> Unit
        ) = Builder(uid, id).apply(block).build()
    }

    class Builder(var uid: String, var id: String) {

        var name: String = ""
        var imageUrl: String = ""
        var state: Int = 0
        var customIntroText: String? = null
        var customClickText: String? = null
        var customTitle: String? = null
        var communityId: Int? = null
        var isOwner: Boolean? = null
        var isGuest: Boolean? = false
        var userUniqueId: String? = null

        fun build() = MemberRO(this)
    }

    fun toBuilder(): Builder {
        return Builder(uid, id).apply {
            name = this@MemberRO.name
            imageUrl = this@MemberRO.imageUrl
            state = this@MemberRO.state
            customIntroText = this@MemberRO.customIntroText
            customClickText = this@MemberRO.customClickText
            communityId = this@MemberRO.communityId
            isOwner = this@MemberRO.isOwner
            isGuest = this@MemberRO.isGuest
            userUniqueId = this@MemberRO.userUniqueId
        }
    }

}