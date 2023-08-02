package com.likeminds.likemindschat.conversation.model

import com.likeminds.likemindschat.community.model.Member

class Reaction private constructor(
    val member: Member?,
    val reaction: String
) {

    class Builder {

        private var member: Member? = null
        private var reaction: String = ""

        fun member(member: Member?) = apply { this.member = member }
        fun reaction(reaction: String) = apply { this.reaction = reaction }

        fun build() = Reaction(member, reaction)
    }

    fun toBuilder(): Builder {
        return Builder().reaction(reaction)
            .member(member)
    }
}