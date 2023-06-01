package com.likeminds.internalsdk.poll.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_

class _Poll_ private constructor(
    @SerializedName(value = "id", alternate = ["draft_poll_id"])
    val id: String?,
    @SerializedName("text")
    val text: String,
    @SerializedName("is_selected")
    val isSelected: Boolean?,
    @SerializedName("percentage")
    val percentage: Int?,
    @SerializedName(value = "sub_text", alternate = ["subText"])
    val subText: String?,
    @SerializedName("no_votes")
    val noVotes: Int?,
    @SerializedName("member")
    val member: _Member_?,
    @SerializedName("user_id")
    val userId: String?
) {
    class Builder {
        private var id: String? = null
        private var text: String = ""
        private var isSelected: Boolean? = null
        private var percentage: Int? = null
        private var subText: String? = null
        private var noVotes: Int? = null
        private var member: _Member_? = null
        private var userId: String? = null

        fun id(id: String?) = apply { this.id = id }
        fun text(text: String) = apply { this.text = text }
        fun isSelected(isSelected: Boolean?) = apply { this.isSelected = isSelected }
        fun percentage(percentage: Int?) = apply { this.percentage = percentage }
        fun subText(subText: String?) = apply { this.subText = subText }
        fun noVotes(noVotes: Int?) = apply { this.noVotes = noVotes }
        fun member(member: _Member_?) = apply { this.member = member }
        fun userId(userId: String?) = apply { this.userId = userId }

        fun build() = _Poll_(
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