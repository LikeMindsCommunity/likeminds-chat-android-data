package com.likeminds.likemindschat.community.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SearchMembersRequest private constructor(
    val search: String,
    val searchType: String,
    val page: Int,
    val pageSize: Int,
    val memberStates: List<Int>?
) : Parcelable {

    class Builder {

        private var search: String = ""
        private var searchType: String = ""
        private var page: Int = 1
        private var pageSize: Int = 10
        private var memberStates: List<Int>? = null

        fun search(search: String) = apply { this.search = search }
        fun searchType(searchType: String) = apply { this.searchType = searchType }
        fun page(page: Int) = apply { this.page = page }
        fun pageSize(pageSize: Int) = apply { this.pageSize = pageSize }
        fun memberStates(memberStates: List<Int>?) = apply { this.memberStates = memberStates }

        fun build() = SearchMembersRequest(
            search,
            searchType,
            page,
            pageSize,
            memberStates
        )
    }

    fun toBuilder(): Builder {
        return Builder().search(search)
            .searchType(searchType)
            .page(page)
            .pageSize(pageSize)
            .memberStates(memberStates)
    }
}