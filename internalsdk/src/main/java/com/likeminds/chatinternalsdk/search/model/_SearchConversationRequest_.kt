package com.likeminds.chatinternalsdk.search.model

import com.google.gson.annotations.SerializedName

class _SearchConversationRequest_ private constructor(
    @SerializedName("search")
    val search: String,
    @SerializedName("follow_status")
    val followStatus: Boolean,
    @SerializedName("page")
    val page: Int,
    @SerializedName("page_size")
    val pageSize: Int
) {
    class Builder {
        private var search: String = ""
        private var followStatus: Boolean = false
        private var page: Int = 1
        private var pageSize: Int = 10

        fun search(search: String) = apply { this.search = search }
        fun followStatus(followStatus: Boolean) = apply { this.followStatus = followStatus }
        fun page(page: Int) = apply { this.page = page }
        fun pageSize(pageSize: Int) = apply { this.pageSize = pageSize }

        fun build() = _SearchConversationRequest_(
            search,
            followStatus,
            page,
            pageSize
        )
    }

    fun toBuilder(): Builder {
        return Builder().search(search)
            .followStatus(followStatus)
            .page(page)
            .pageSize(pageSize)
    }
}