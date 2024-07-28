package com.likeminds.chatinternalsdk.community.model

import com.google.gson.annotations.SerializedName

class _SearchMembersRequest_ private constructor(
    @SerializedName("search")
    val search: String,
    @SerializedName("search_type")
    val searchType: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("page_size")
    val pageSize: Int,
    @SerializedName("member_states")
    val memberStates: List<Int>?,
    @SerializedName("exclude_self_user")
    val excludeSelfUser: Boolean?
) {

    class Builder {
        private var search: String = ""
        private var searchType: String = ""
        private var page: Int = 1
        private var pageSize: Int = 10
        private var memberStates: List<Int>? = null
        private var excludeSelfUser: Boolean? = null

        fun search(search: String) = apply { this.search = search }
        fun searchType(searchType: String) = apply { this.searchType = searchType }
        fun page(page: Int) = apply { this.page = page }
        fun pageSize(pageSize: Int) = apply { this.pageSize = pageSize }
        fun memberStates(memberStates: List<Int>?) = apply { this.memberStates = memberStates }
        fun excludeSelfUser(excludeSelfUser: Boolean?) =
            apply { this.excludeSelfUser = excludeSelfUser }

        fun build() = _SearchMembersRequest_(
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