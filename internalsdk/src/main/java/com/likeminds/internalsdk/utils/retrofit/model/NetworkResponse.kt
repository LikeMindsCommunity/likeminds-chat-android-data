package com.likeminds.internalsdk.utils.retrofit.model

sealed class NetworkResponse<out T : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T>()

    /**
     * Error response with body
     */
    data class Error(val body: ErrorResponse) : NetworkResponse<Nothing>()
}
