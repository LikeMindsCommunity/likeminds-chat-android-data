package com.likeminds.internalsdk.community.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class _SearchMembersRequest_ private constructor(
    @SerializedName("search")
    val search: String,
    @SerializedName("search_type")
    val searchType: String,
    @SerializedName("page")
    val page: Int,
    @SerializedName("page_size")
    val pageSize: Int,
    @SerializedName("member_states")
    val memberStates: List<Int>?
) {

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

        fun build() = _SearchMembersRequest_(
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