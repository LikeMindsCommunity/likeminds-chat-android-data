package com.likeminds.chatinternalsdk.community.model

import com.google.gson.annotations.SerializedName

class _GetAIChatbotsRequest_ private constructor(
    @SerializedName("page")
    val page: Int,
    @SerializedName("page_size")
    val pageSize: Int,
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

        fun build() = _GetAIChatbotsRequest_(page, pageSize)
    }

    fun toBuilder(): Builder {
        return Builder().page(page)
            .pageSize(pageSize)
    }
}