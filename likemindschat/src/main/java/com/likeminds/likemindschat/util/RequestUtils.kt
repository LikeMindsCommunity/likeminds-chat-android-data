package com.likeminds.likemindschat.util

import com.likeminds.likemindschat.LMChatClient

object RequestUtils {

    /**
     * validates whether LMFeedClient is instantiated or not
     * @throws IllegalAccessException - if LMFeedClient is not instantiated
     */
    fun validate() {
        LMChatClient.getInstance()
    }

    /**
     * @param property - Name of property which is null or empty
     * @throws IllegalAccessException - as required property is empty/null
     */
    fun throwException(property: String) {
        throw IllegalArgumentException("$property is empty.")
    }
}