package com.likeminds.likemindschat

data class LMResponse<T>(
    var success: Boolean,
    var errorMessage: String? = null,
    var data: T? = null
)