package com.likeminds.chatinternalsdk.sdk

import com.likeminds.chatinternalsdk.helper._LMChatLogger_
import com.likeminds.chatinternalsdk.helper.model._LMSeverity_
import com.likeminds.chatinternalsdk.sdk.model.*
import com.likeminds.chatinternalsdk.utils.retrofit.model.APIResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import javax.inject.Inject

class SDKReceiver @Inject constructor(private val sdkNetworkApi: SDKNetworkApi) {

    suspend fun initiateUser(
        apiKey: String,
        request: _InitiateUserRequest_
    ): NetworkResponse<APIResponse<_InitiateUserResponse_>> {

        _LMChatLogger_.getInstance()?.handleException(
            "Data",
            "B",
            _LMSeverity_.INFO
        )
        val newRequest = request.toBuilder().apiKey(null).build()
        return sdkNetworkApi.initiateUser(apiKey, newRequest)
    }

    suspend fun validateUser(): NetworkResponse<APIResponse<_ValidateUserResponse_>> {
        return sdkNetworkApi.validateUser()
    }
}