package com.likeminds.chatinternalsdk.utils.retrofit

import com.likeminds.chatinternalsdk.utils.retrofit.model.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlin.math.pow

class RetryInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return process(chain, 0)
    }

    // intercepts the request and processes it as per the response code
    private fun process(chain: Interceptor.Chain, attempt: Int): Response {
        var response: Response? = null
        try {
            val request = chain.request()
            response = chain.proceed(request)
            if (attempt < NetworkConstants.MAX_RETRIES_ALLOWED && response.code in NetworkConstants.retryErrorCodes) {
                return delayedAttempt(chain, response, attempt)
            }
            return response
        } catch (e: Exception) {
            if (attempt < NetworkConstants.MAX_RETRIES_ALLOWED) {
                return delayedAttempt(chain, response, attempt)
            }
            throw e
        }
    }

    // processes the request with a delay
    private fun delayedAttempt(
        chain: Interceptor.Chain,
        response: Response?,
        attempt: Int,
    ): Response {
        response?.body?.close()
        val retryDelay = 2.0.pow(attempt).toLong() * 1000
        Thread.sleep(retryDelay)
        return process(chain, attempt = attempt + 1)
    }
}