package com.likeminds.likemindschat.community.model

import com.likeminds.likemindschat.user.model.MemberRole

class GetAllMemberRequest private constructor(
    val page: Int,
    val filterMemberRoles: List<MemberRole>
) {

    class Builder {

        private var page: Int = -1
        private var filterMemberRoles: List<MemberRole> = emptyList()

        fun page(page: Int) = apply { this.page = page }
        fun filterMemberRoles(filterMemberRoles: List<MemberRole>) =
            apply { this.filterMemberRoles = filterMemberRoles }

        fun build() = GetAllMemberRequest(page, filterMemberRoles)
    }

    fun toBuilder(): Builder {
        return Builder().page(page)
            .filterMemberRoles(filterMemberRoles)
    }
}