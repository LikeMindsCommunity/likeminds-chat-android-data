package com.likeminds.internalsdk.community.model

import com.google.gson.annotations.SerializedName

class _GetAllMemberRequest_ private constructor(
    @SerializedName("page")
    val page: Int,
    @SerializedName("filter_member_roles")
    val filterMemberRoles: List<String>
) {

    class Builder {

        private var page: Int = -1
        private var filterMemberRoles: List<String> = emptyList()

        fun page(page: Int) = apply { this.page = page }
        fun filterMemberRoles(filterMemberRoles: List<String>) =
            apply { this.filterMemberRoles = filterMemberRoles }

        fun build() = _GetAllMemberRequest_(page, filterMemberRoles)
    }

    fun toBuilder(): Builder {
        return Builder().page(page)
            .filterMemberRoles(filterMemberRoles)
    }
}