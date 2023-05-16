package com.likeminds.internalsdk.utils.retrofit

import com.google.gson.Gson
import com.likeminds.internalsdk.utils.retrofit.model.ErrorResponse
import com.likeminds.internalsdk.utils.retrofit.model.NetworkResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException

internal class NetworkResponseCall<S : Any>(
    private val gson: Gson,
    private val delegate: Call<S>,
    private val errorConverter: Converter<ResponseBody, ErrorResponse>
) : Call<NetworkResponse<S>> {

    override fun enqueue(callback: Callback<NetworkResponse<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val error = response.errorBody()

                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Success(body))
                        )
                    } else {
                        // Response is successful but the body is null
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                NetworkResponse.Error(
                                    ErrorResponse(
                                        "Unknown error occurred",
                                        null,
                                        success = false
                                    )
                                )
                            )
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            errorConverter.convert(error)
                        } catch (ex: Exception) {
                            null
                        }
                    }
                    if (errorBody != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                NetworkResponse.Error(errorBody)
                            )
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(
                                NetworkResponse.Error(
                                    ErrorResponse(
                                        "Unknown error occurred",
                                        null,
                                        success = false
                                    )
                                )
                            )
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse = when (throwable) {
                    is IOException -> NetworkResponse.Error(
                        ErrorResponse(
                            throwable.message,
                            null,
                            success = false
                        )
                    )
                    else -> NetworkResponse.Error(
                        ErrorResponse(
                            throwable.message,
                            null,
                            success = false
                        )
                    )
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(gson, delegate.clone(), errorConverter)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

}