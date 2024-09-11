package com.likeminds.likemindschat.search.model

class SearchConversationRequest private constructor(
    val search: String,
    val followStatus: Boolean?,
    val chatroomId: String?,
    val page: Int,
    val pageSize: Int
) {
    class Builder {
        private var search: String = ""
        private var followStatus: Boolean? = null
        private var chatroomId: String? = null
        private var page: Int = 1
        private var pageSize: Int = 10

        fun search(search: String) = apply {
            this.search = search
        }

        fun followStatus(followStatus: Boolean?) = apply {
            this.followStatus = followStatus
        }

        fun chatroomId(chatroomId: String?) = apply {
            this.chatroomId = chatroomId
        }

        fun page(page: Int) = apply {
            this.page = page
        }

        fun pageSize(pageSize: Int) = apply {
            this.pageSize = pageSize
        }

        fun build() = SearchConversationRequest(
            search,
            followStatus,
            chatroomId,
            page,
            pageSize
        )
    }

    fun toBuilder(): Builder {
        return Builder().search(search)
            .followStatus(followStatus)
            .chatroomId(chatroomId)
            .page(page)
            .pageSize(pageSize)
    }
}