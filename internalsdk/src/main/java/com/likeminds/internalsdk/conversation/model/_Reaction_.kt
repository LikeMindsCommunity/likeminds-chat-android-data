package com.likeminds.internalsdk.conversation.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_

class _Reaction_ private constructor(
    @SerializedName("member")
    val member: _Member_,
    @SerializedName("reaction")
    val reaction: String
) {

    class Builder {

        private var member: _Member_ = _Member_.Builder().build()
        private var reaction: String = ""

        fun member(member: _Member_) = apply { this.member = member }
        fun reaction(reaction: String) = apply { this.reaction = reaction }

        fun build() = _Reaction_(member, reaction)
    }

    fun toBuilder(): Builder {
        return Builder().reaction(reaction)
            .member(member)
    }
}