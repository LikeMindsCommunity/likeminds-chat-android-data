package com.likeminds.likemindschat.poll.model

import com.likeminds.likemindschat.community.model.Member

class Poll private constructor(
    val id: String?,
    val text: String,
    val isSelected: Boolean?,
    val percentage: Int?,
    val subText: String?,
    val noVotes: Int?,
    val member: Member?,
    val userId: String?
) {
    class Builder {
        private var id: String? = null
        private var text: String = ""
        private var isSelected: Boolean? = null
        private var percentage: Int? = null
        private var subText: String? = null
        private var noVotes: Int? = null
        private var member: Member? = null
        private var userId: String? = null

        fun id(id: String?) = apply { this.id = id }
        fun text(text: String) = apply { this.text = text }
        fun isSelected(isSelected: Boolean?) = apply { this.isSelected = isSelected }
        fun percentage(percentage: Int?) = apply { this.percentage = percentage }
        fun subText(subText: String?) = apply { this.subText = subText }
        fun noVotes(noVotes: Int?) = apply { this.noVotes = noVotes }
        fun member(member: Member?) = apply { this.member = member }
        fun userId(userId: String?) = apply { this.userId = userId }

        fun build() = Poll(
            id,
            text,
            isSelected,
            percentage,
            subText,
            noVotes,
            member,
            userId
        )
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .text(text)
            .isSelected(isSelected)
            .percentage(percentage)
            .subText(subText)
            .noVotes(noVotes)
            .member(member)
            .userId(userId)
    }
}