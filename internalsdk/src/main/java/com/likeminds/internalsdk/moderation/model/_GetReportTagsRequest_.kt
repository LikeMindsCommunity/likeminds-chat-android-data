package com.likeminds.internalsdk.moderation.model

import com.google.gson.annotations.SerializedName

class _GetReportTagsRequest_ private constructor(
    @SerializedName("type")
    val type: Int
) {
    class Builder {
        private var type: Int = 0

        fun type(type: Int) = apply { this.type = type }

        fun build() = _GetReportTagsRequest_(type)
    }

    fun toBuilder(): Builder {
        return Builder().type(type)
    }
}