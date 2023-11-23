package com.likeminds.likemindschat.community.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SearchMembersRequest private constructor(
    val search: String,
    val searchType: String,
    val page: Int,
    val pageSize: Int
) : Parcelable {

    class Builder {

        private var search: String = ""
        private var searchType: String = ""
        private var page: Int = 1
        private var pageSize: Int = 10

        fun search(search: String) = apply { this.search = search }
        fun searchType(searchType: String) = apply { this.searchType = searchType }
        fun page(page: Int) = apply { this.page = page }
        fun pageSize(pageSize: Int) = apply { this.pageSize = pageSize }

        fun build() = SearchMembersRequest(search, searchType, page, pageSize)
    }

    fun toBuilder(): Builder {
        return Builder().search(search)
            .searchType(searchType)
            .page(page)
            .pageSize(pageSize)
    }
}