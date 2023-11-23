package com.likeminds.likemindschat.community.model

class GetAllMemberRequest private constructor(
    val page: Int,
    val filterMemberRoles: List<String>
) {

    class Builder {

        private var page: Int = -1
        private var filterMemberRoles: List<String> = emptyList()

        fun page(page: Int) = apply { this.page = page }
        fun filterMemberRoles(filterMemberRoles: List<String>) =
            apply { this.filterMemberRoles = filterMemberRoles }

        fun build() = GetAllMemberRequest(page, filterMemberRoles)
    }

    fun toBuilder(): Builder {
        return Builder().page(page)
            .filterMemberRoles(filterMemberRoles)
    }
}