package com.likeminds.chatinternalsdk.community.model

import com.google.gson.annotations.SerializedName

class _GetAllMemberRequest_ private constructor(
    @SerializedName("page")
    val page: Int,
    @SerializedName("filter_member_roles")
    val filterMemberRoles: List<String>,
    @SerializedName("exclude_self_user")
    val excludeSelfUser: Boolean?
) {
    class Builder {
        private var page: Int = 1
        private var filterMemberRoles: List<String> = emptyList()
        private var excludeSelfUser: Boolean? = null

        fun page(page: Int) = apply { this.page = page }
        fun filterMemberRoles(filterMemberRoles: List<String>) =
            apply { this.filterMemberRoles = filterMemberRoles }

        fun excludeSelfUser(excludeSelfUser: Boolean?) =
            apply { this.excludeSelfUser = excludeSelfUser }

        fun build() = _GetAllMemberRequest_(page, filterMemberRoles, excludeSelfUser)
    }

    fun toBuilder(): Builder {
        return Builder().page(page)
            .filterMemberRoles(filterMemberRoles)
            .excludeSelfUser(excludeSelfUser)
    }
}