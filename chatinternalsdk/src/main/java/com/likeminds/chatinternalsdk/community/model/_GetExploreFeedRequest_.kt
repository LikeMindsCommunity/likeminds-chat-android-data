package com.likeminds.chatinternalsdk.community.model

import com.google.gson.annotations.SerializedName

class _GetExploreFeedRequest_ private constructor(
    @SerializedName("order_type")
    val orderType: Int,
    @SerializedName("is_pinned")
    val isPinned: Boolean?,
    @SerializedName("page")
    val page: Int
) {
    class Builder {
        private var orderType: Int = -1
        private var isPinned: Boolean? = null
        private var page: Int = 1

        fun orderType(orderType: Int) = apply { this.orderType = orderType }
        fun isPinned(isPinned: Boolean?) = apply { this.isPinned = isPinned }
        fun page(page: Int) = apply { this.page = page }

        fun build() = _GetExploreFeedRequest_(
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