package com.likeminds.likemindschat.community.model

class GetExploreFeedRequest private constructor(
    val orderType: Int,
    val isPinned: Boolean?,
    val page: Int
) {
    class Builder {
        private var orderType: Int = -1
        private var isPinned: Boolean? = null
        private var page: Int = 1

        fun orderType(orderType: Int) = apply { this.orderType = orderType }
        fun isPinned(isPinned: Boolean?) = apply { this.isPinned = isPinned }
        fun page(page: Int) = apply { this.page = page }

        fun build() = GetExploreFeedRequest(
            orderType,
            isPinned,
            page
        )
    }

    fun toBuilder(): Builder {
        return Builder().orderType(orderType)
            .isPinned(isPinned)
            .page(page)
    }
}