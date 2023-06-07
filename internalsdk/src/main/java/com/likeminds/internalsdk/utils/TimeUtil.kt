package com.likeminds.internalsdk.utils

import android.annotation.SuppressLint
import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
object TimeUtil {

    private const val APPROX_INITIAL_LAUNCH_MILLIS = 1546281000000
    private const val MILLIS_IN_DAY = 24 * 60 * 60 * 1000
    private const val MILLIS_IN_HOURS = 60 * 60 * 1000

    private val DATE_SDF = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd/MM/yy")
        }
    }
    private val DATE_SDF_1 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd MMM yyyy")
        }
    }
    private val DATE_SDF_2 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("MMM dd, yyyy")
        }
    }
    private val DATE_SDF_3 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd/MM/yyyy")
        }
    }
    private val DATE_SDF_4 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd MMM yy")
        }
    }
    private val DATE_TIME_SDF_1 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd MMM yyyy, hh:mm a")
        }
    }
    private val DATE_TIME_SDF_2 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd MMM, hh:mm a")
        }
    }
    private val DATE_TIME_SDF_3 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd MMM, EEEE, HH:mm")
        }
    }
    private val DATE_TIME_SDF_4 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("MMM dd (EEE), hh:mm a")
        }
    }
    private val TIME_SDF = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("HH:mm")
        }
    }
    private val TIME_SDF_1 = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("hh:mm a")
        }
    }

    /**
     * Returns current time in HH:mm format
     */
    fun generateCreatedAt(): String {
        return TIME_SDF.get()?.format(Date()) ?: ""
    }

    /**
     * Returns current date in dd MMM yyyy format
     */
    fun generateDate(): String {
        return DATE_SDF_1.get()?.format(Date()) ?: ""
    }

    /**
     * Returns date in dd MMM yyyy format
     */
    fun getDate(time: Long?): String {
        if (time == null) {
            return ""
        }
        return DATE_SDF_1.get()?.format(Date(time)) ?: ""
    }

    /**
     * Returns date in MMM dd, yyyy format
     */
    fun getDate2(time: Long): String {
        return DATE_SDF_2.get()?.format(Date(time)) ?: ""
    }

    /**
     * Returns date in dd/MM/yyyy format
     */
    fun getDate3(time: Long): String {
        return DATE_SDF_3.get()?.format(Date(time)) ?: ""
    }

    /**
     * Returns date in dd MMM yy format
     */
    fun getDate4(time: Long): String {
        return DATE_SDF_4.get()?.format(Date(time)) ?: ""
    }

    /**
     * Returns date in dd MMM yyyy format
     */
    fun getDateTime(time: Long): String {
        return DATE_TIME_SDF_1.get()?.format(Date(time)) ?: ""
    }

    /**
     * Returns date in dd MMM, hh:mm a format
     */
    fun getDateTime2(time: Long?): String {
        if (time == null) {
            return ""
        }
        return (DATE_TIME_SDF_2.get()?.format(Date(time)) ?: "")
            .replace(" am", " AM")
            .replace(" pm", "PM")
    }

    /**
     * Returns date in dd MMM, EEEE, hh:mm format
     */
    fun getDateTime3(time: Long?): String {
        if (time == null) {
            return ""
        }
        return DATE_TIME_SDF_3.get()?.format(Date(time)) ?: ""
    }

    fun getDateTime4(time: Long?): String {
        if (time == null) {
            return ""
        }
        return DATE_TIME_SDF_4.get()?.format(Date(time)) ?: ""
    }

    fun getDaysLeft(timestamp: Long): Int {
        val currentTimeMillis = System.currentTimeMillis()
        val diff = timestamp - currentTimeMillis
        return if (diff <= 0) {
            0
        } else {
            (diff / MILLIS_IN_DAY).toInt()
        }
    }

    fun getDaysAndHours(timestamp: Long): String {
        val days = (timestamp / MILLIS_IN_DAY).toInt()
        val hours = ((timestamp - (days * MILLIS_IN_DAY)) / MILLIS_IN_HOURS).toInt()
        return when {
            days == 0 && hours == 1 -> "$hours hour"
            days == 0 && hours > 1 -> "$hours hours"
            days == 1 && hours == 0 -> "$days day"
            days == 1 && hours > 1 -> "$days day $hours hours"
            days > 1 && hours == 0 -> "$days days"
            days > 1 && hours == 1 -> "$days days $hours hour"
            days > 1 && hours > 1 -> "$days days $hours hours"
            else -> "0 hours"
        }
    }

    fun getHoursAndMinutes(timestamp: Long): String {
        val hrs = TimeUnit.MILLISECONDS.toHours(timestamp)
        val mins = TimeUnit.MILLISECONDS.toMinutes(timestamp)
        return buildString {
            if (hrs != 0L) {
                if (hrs == 1L) {
                    append("1 hr")
                } else {
                    append("$hrs hrs")
                }
            }
            if (mins != 0L) {
                if (hrs != 0L) {
                    append(" ")
                }
                if (mins == 1L) {
                    append("1 min")
                } else {
                    append("$mins mins")
                }
            }
        }
    }

    fun getHoursAndMinutes2(timestamp: Long): String {
        val mins = TimeUnit.MILLISECONDS.toMinutes(timestamp)
        val secs = TimeUnit.MILLISECONDS.toSeconds(timestamp) % 60
        return "$mins:$secs"
    }

    /**
     * @param time in millis
     * @return text to show
     */
    fun getLastConversationTime(time: Long?): String {
        if (time == null) {
            return ""
        }
        val newTime = if (isInMillis(time)) {
            time
        } else {
            time * 1000
        }
        val midnightTimestamp = getMidnightTimestamp()
        return when {
            newTime > midnightTimestamp -> {
                TIME_SDF.get()?.format(Date(newTime)) ?: ""
            }

            newTime > (midnightTimestamp - MILLIS_IN_DAY) -> {
                "Yesterday"
            }

            else -> {
                DATE_SDF.get()?.format(Date(newTime)) ?: ""
            }
        }
    }

    fun getTime(time: Long?): String {
        if (time == null) {
            return ""
        }
        return TIME_SDF.get()?.format(Date(time)) ?: ""
    }

    fun getFormattedTime(time: Long?): String {
        if (time == null) {
            return ""
        }
        return (TIME_SDF_1.get()?.format(Date(time)) ?: "")
            .replace(" am", " AM")
            .replace(" pm", " PM")
    }

    fun isInMillis(time: Long): Boolean {
        return time > APPROX_INITIAL_LAUNCH_MILLIS
    }

    fun isSameDay(time1: Long?, time2: Long?): Boolean {
        if (time1 != null && time2 != null) {
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.time = Date(time1)
            cal2.time = Date(time2)
            return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
        }
        return false
    }

    /**
     * @param time Time in millis
     * @return Tripe<Date, Day (EEE), Month (MMM)>
     */
    fun getDateDayMonth(time: Long): Triple<Int, String, String> {
        val date = Date(time)
        return Triple(
            DateFormat.format("dd", date).toString().toIntOrNull() ?: 0,
            DateFormat.format("EEE", date).toString(),
            DateFormat.format("MMM", date).toString()
        )
    }

    private fun getMidnightTimestamp(): Long {
        val date = GregorianCalendar()
        date.set(Calendar.HOUR_OF_DAY, 0)
        date.set(Calendar.MINUTE, 0)
        date.set(Calendar.SECOND, 0)
        date.set(Calendar.MILLISECOND, 0)
        return date.time.time
    }
}