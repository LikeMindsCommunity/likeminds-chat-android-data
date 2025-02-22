package com.likeminds.chatinternalsdk.utils.retrofit

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlin.math.pow

class RetryInterceptor @Inject constructor() : Interceptor {
    companion object {
        private const val MAX_RETRIES_ALLOWED = 3
        private val RETRY_STATUS_CODES = setOf(500, 502, 503, 504, 408, 429)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return process(chain, 0)
    }

    private fun process(chain: Interceptor.Chain, attempt: Int): Response {
        var response: Response? = null
        try {
            val request = chain.request()
            response = chain.proceed(request)
            if (attempt < MAX_RETRIES_ALLOWED && response.code in RETRY_STATUS_CODES) {
                return delayedAttempt(chain, response, attempt)
            }
            return response
        } catch (e: Exception) {
            if (attempt < MAX_RETRIES_ALLOWED) {
                return delayedAttempt(chain, response, attempt)
            }
            throw e
        }
    }

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