package com.likeminds.internalsdk.utils.retrofit.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ErrorResponse(
    @SerializedName("error_message")
    val errorMessage: String? = null,
    val code: Int? = null,
    val success: Boolean = false
) {

    fun error() = errorMessage ?: "Unknown error occurred"

}