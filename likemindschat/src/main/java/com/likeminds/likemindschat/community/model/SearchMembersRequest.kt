package com.likeminds.likemindschat.community.model

class SearchMembersRequest private constructor(
    val search: String,
    val searchType: MemberSearchType,
    val page: Int,
    val pageSize: Int,
    val memberStates: List<Int>?,
    val excludeSelfUser: Boolean?
) {
    class Builder {

        private var search: String = ""
        private var searchType: MemberSearchType = MemberSearchType.EMPTY
        private var page: Int = 1
        private var pageSize: Int = 10
        private var memberStates: List<Int>? = null
        private var excludeSelfUser: Boolean? = null

        fun search(search: String) = apply { this.search = search }
        fun searchType(searchType: MemberSearchType) = apply { this.searchType = searchType }
        fun page(page: Int) = apply { this.page = page }
        fun pageSize(pageSize: Int) = apply { this.pageSize = pageSize }
        fun memberStates(memberStates: List<Int>?) = apply { this.memberStates = memberStates }
        fun excludeSelfUser(excludeSelfUser: Boolean?) =
            apply { this.excludeSelfUser = excludeSelfUser }

        fun build() = SearchMembersRequest(
            search,
            searchType,
            page,
            pageSize,
            memberStates,
            excludeSelfUser
        )
    }

    fun toBuilder(): Builder {
        return Builder().search(search)
            .searchType(searchType)
            .page(page)
            .pageSize(pageSize)
            .memberStates(memberStates)
            .excludeSelfUser(excludeSelfUser)
    }
}