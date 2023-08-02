package com.likeminds.internalsdk.utils

import android.util.Log
import kotlin.system.measureTimeMillis

fun <T> measureExecution(name: String, func: () -> T): T {
    val result: T
    val diff = measureTimeMillis {
        result = func()
    }
    Log.i(MEASURE_EXECUTION_TAG, "[$diff] $name")
    return result
}

const val MAX_RETRY_COUNT = 3
const val MEASURE_EXECUTION_TAG = "measureExecution"