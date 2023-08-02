package com.likeminds.likemindschat.util

object ValueUtils {
    fun Array<String>.containsArray(vararg subArray: String): Boolean {
        var found = false
        for (s in subArray) {
            if (this.contains(s)) {
                found = true
                break
            }
        }
        return found
    }
}