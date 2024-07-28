package com.likeminds.chatinternalsdk.utils.retrofit

import com.google.gson.Gson
import com.likeminds.chatinternalsdk.utils.retrofit.model.ErrorResponse
import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkResponse
import okhttp3.ResponseBody
import retrofit2.*
import java.lang.reflect.Type

internal class NetworkResponseAdapter<S : Any>(
    private val gson: Gson,
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody, ErrorResponse>
) : CallAdapter<S, Call<NetworkResponse<S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S>> {
        return NetworkResponseCall(gson, call, errorBodyConverter)
    }
}