package com.likeminds.likemindschat.community.model

class GetAIChatbotsRequest private constructor(
    val page: Int,
    val pageSize: Int
) {
    class Builder {
        private var page: Int = 1
        private var pageSize: Int = 10

        fun page(page: Int) = apply {
            this.page = page
        }

        fun pageSize(pageSize: Int) = apply {
            this.pageSize = pageSize
        }

        fun build() = GetAIChatbotsRequest(page, pageSize)
    }

    fun toBuilder(): Builder {
        return Builder().page(page)
            .pageSize(pageSize)
    }
}