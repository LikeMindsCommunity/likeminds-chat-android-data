package com.likeminds.internalsdk.utils.retrofit.model

import com.google.gson.annotations.SerializedName

data class APIResponse<T>(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("error_message")
    val errorMessage: String?,
    @SerializedName("data")
    val data: T?
)