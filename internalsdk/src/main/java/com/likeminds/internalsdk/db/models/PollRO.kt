package com.likeminds.internalsdk.db.models

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass(embedded = true)
open class PollRO(
    var id: String = "",
    var text: String = "",
    var subText: String? = null,
    var isSelected: Boolean? = null,
    var percentage: Int? = null,
    var noVotes: Int? = null,
    var member: MemberRO? = null,
) : RealmObject() {

    private constructor(builder: Builder) : this(
        builder.id,
        builder.text,
        builder.subText,
        builder.isSelected,
        builder.percentage,
        builder.noVotes,
        builder.member
    )

    companion object {

        inline fun build(
            id: String,
            text: String,
            block: Builder.() -> Unit
        ) = Builder(id, text).apply(block).build()
    }

    class Builder(var id: String, var text: String) {

        var subText: String? = null
        var isSelected: Boolean? = null
        var percentage: Int? = null
        var noVotes: Int? = null
        var member: MemberRO? = null

        fun build() = PollRO(this)
    }

    fun toBuilder(): Builder {
        return Builder(id, text).apply {
            subText = this@PollRO.subText
            isSelected = this@PollRO.isSelected
            percentage = this@PollRO.percentage
            noVotes = this@PollRO.noVotes
            member = this@PollRO.member
        }
    }

}