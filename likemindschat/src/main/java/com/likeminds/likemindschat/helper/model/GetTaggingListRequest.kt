package com.likeminds.likemindschat.helper.model

class GetTaggingListRequest private constructor(
    val chatroomId: String,
    val page: Int,
    val pageSize: Int,
    val searchName: String?
) {
    class Builder {
        private var chatroomId: String = ""
        private var page: Int = 1
        private var pageSize: Int = 10
        private var searchName: String? = null

        fun chatroomId(chatroomId: String) = apply { this.chatroomId = chatroomId }
        fun page(page: Int) = apply { this.page = page }
        fun pageSize(pageSize: Int) = apply { this.pageSize = pageSize }
        fun searchName(searchName: String?) = apply { this.searchName = searchName }

        fun build() = GetTaggingListRequest(
            chatroomId,
            page,
            pageSize,
            searchName
        )
    }

    fun toBuilder(): Builder {
        return Builder().chatroomId(chatroomId)
            .page(page)
            .pageSize(pageSize)
            .searchName(searchName)
    }
}