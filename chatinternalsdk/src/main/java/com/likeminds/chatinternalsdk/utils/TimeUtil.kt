package com.likeminds.chatinternalsdk.utils

import android.annotation.SuppressLint

@SuppressLint("SimpleDateFormat")
object TimeUtil {

    private const val APPROX_INITIAL_LAUNCH_MILLIS = 1546281000000

    //to check whether [time] is on millisecond or not
    fun isInMillis(time: Long): Boolean {
        return time > APPROX_INITIAL_LAUNCH_MILLIS
    }
}