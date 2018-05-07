package com.common.utils

import android.text.format.DateFormat
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Burhanuddin Rashid on 11/10/2017.
 * @auther {@link 'https://github.com/burhanrashid52'}
 */
object DateFormatUtils {
    private val serverDateFormat = "yyyy-MM-dd HH:mm:ss"
    private val mSdf = SimpleDateFormat(serverDateFormat, Locale.ENGLISH)

    /**
     * Convert timestamp to hh:mm a time format.
     */
    @JvmStatic
    fun getHourMinutesTime(calendar: Calendar): String {
        return try {
            DateFormat.format("hh:mm a", calendar).toString()
        } catch (e: Exception) {
            Timber.e(e)
            ""
        }
    }

    /**
     * Parse the date received from the server into the milliseconds.
     *
     * @param date Date string to parse in yyyy-MM-dd HH:mm:ss format.
     * @return Time in milli seconds
     */
    @JvmStatic
    fun parseServerDate(dateString: String?): Calendar {
        val calender = Calendar.getInstance()
        if (dateString == null) return calender

        //Parse the date
        calender.time = mSdf.parse(dateString)
        return calender
    }

    /**
     * Prepare date string in [serverDateFormat] format from time in milliseconds.
     *
     * @return Time in milli seconds
     */
    @JvmStatic
    fun prepareServerDate(timeMills: Long): String {
        return mSdf.format(timeMills)
    }

    /**
     * Prepare date string in [serverDateFormat] format from time in milliseconds.
     *
     * @return Time in milli seconds
     */
    @JvmStatic
    fun getTimeFromCurrentDay(calendar: Calendar): String {
        val nowCal = Calendar.getInstance()

        if (nowCal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
                && nowCal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
            //Message is from this month
            val currentDay = nowCal.get(Calendar.DAY_OF_MONTH)
            val messageDayDay = calendar.get(Calendar.DAY_OF_MONTH)

            return when (messageDayDay) {
                currentDay -> "Today"
                currentDay - 1 -> "Yesterday"
                else -> SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(Date(calendar.timeInMillis))
            }
        }
        //This date is from future
        return SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(Date(calendar.timeInMillis))
    }
}