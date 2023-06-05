package com.likeminds.likemindschat.chatroom.model

import com.google.gson.annotations.SerializedName
import com.likeminds.likemindschat.community.model.Member

class Cohort private constructor(
    @SerializedName("cohort_id", alternate = ["id"])
    val id: Int?,
    @SerializedName(value = "total_members", alternate = ["member_count"])
    val totalMembers: Int?,
    val name: String?,
    val members: List<Member>?
) {

    class Builder {

        private var id: Int? = null
        private var totalMembers: Int? = null
        private var name: String? = null
        private var members: List<Member>? = null

        fun id(id: Int?) = apply { this.id = id }
        fun totalMembers(totalMembers: Int?) = apply { this.totalMembers = totalMembers }
        fun name(name: String?) = apply { this.name = name }
        fun members(members: List<Member>?) = apply { this.members = members }

        fun build() = Cohort(id, totalMembers, name, members)
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .name(name)
            .totalMembers(totalMembers)
            .members(members)
    }
}