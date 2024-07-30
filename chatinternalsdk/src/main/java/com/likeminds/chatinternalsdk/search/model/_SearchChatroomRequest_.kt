package com.likeminds.chatinternalsdk.search.model

import com.google.gson.annotations.SerializedName

class _SearchChatroomRequest_ private constructor(
    @SerializedName("search")
    val search: String,
    @SerializedName("follow_status")
    val followStatus: Boolean,
    @SerializedName("page")
    val page: Int,
    @SerializedName("page_size")
    val pageSize: Int,
    @SerializedName("search_type")
    val searchType: String,
) {
    class Builder {
        private var search: String = ""
        private var followStatus: Boolean = false
        private var page: Int = 1
        private var pageSize: Int = 10
        private var searchType: String = ""

        fun search(search: String) = apply { this.search = search }
        fun followStatus(followStatus: Boolean) = apply { this.followStatus = followStatus }
        fun page(page: Int) = apply { this.page = page }
        fun pageSize(pageSize: Int) = apply { this.pageSize = pageSize }
        fun searchType(searchType: String) = apply { this.searchType = searchType }

        fun build() = _SearchChatroomRequest_(
            search,
            followStatus,
            page,
            pageSize,
            searchType
        )
    }

    fun toBuilder(): Builder {
        return Builder().search(search)
            .followStatus(followStatus)
            .page(page)
            .pageSize(pageSize)
            .searchType(searchType)
    }
}