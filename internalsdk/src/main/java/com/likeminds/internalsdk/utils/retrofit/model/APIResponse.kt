package com.likeminds.internalsdk.utils.retrofit.model

import com.google.gson.annotations.SerializedName

data class APIResponse<T>(
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("error_message")
    var errorMessage: String?,
    @SerializedName("data")
    var data: T?
)