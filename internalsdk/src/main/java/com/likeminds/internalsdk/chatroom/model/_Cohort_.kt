package com.likeminds.internalsdk.chatroom.model

import com.google.gson.annotations.SerializedName
import com.likeminds.internalsdk.community.model._Member_

class _Cohort_ private constructor(
    @SerializedName("cohort_id", alternate = ["id"])
    val id: Int?,
    @SerializedName(value = "total_members", alternate = ["member_count"])
    val totalMembers: Int?,
    val name: String?,
    val members: List<_Member_>?
) {

    class Builder {

        private var id: Int? = null
        private var totalMembers: Int? = null
        private var name: String? = null
        private var members: List<_Member_>? = null

        fun id(id: Int?) = apply { this.id = id }
        fun totalMembers(totalMembers: Int?) = apply { this.totalMembers = totalMembers }
        fun name(name: String?) = apply { this.name = name }
        fun members(members: List<_Member_>?) = apply { this.members = members }

        fun build() = _Cohort_(id, totalMembers, name, members)
    }

    fun toBuilder(): Builder {
        return Builder().id(id)
            .name(name)
            .totalMembers(totalMembers)
            .members(members)
    }
}