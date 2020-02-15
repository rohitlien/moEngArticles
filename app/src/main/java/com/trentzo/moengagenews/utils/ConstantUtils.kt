package com.trentzo.moengagenews.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ConstantUtils {
    const val NEW_ARTICLE_ARRIVED = "new_article_arrived"
    const val KEY_UPLOAD_DATA= "updateData"
    private val months = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

    const val KEY_ARTICLE_URL = "article_url"
    const val KEY_GET_DATA_API = "https://candidate-test-data-moengage.s3.amazonaws.com/android/news-api-feed/staticResponse.json"


    @SuppressLint("SimpleDateFormat")
    @Synchronized
    fun getDateTimeToShow(dateTime: String?, isShowYesterday: Boolean = true): String {
        if (dateTime.isNullOrEmpty())
            return " --- "

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        format.timeZone = TimeZone.getTimeZone("UTC")
        val c = Calendar.getInstance()
        try {
            c.time = format.parse(dateTime)!!
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return getDateTimeFromCalendar(c, isShowYesterday)
    }

    private fun getDateTimeFromCalendar(c: Calendar, isShowYesterday: Boolean): String {
        val today: Calendar = Calendar.getInstance()

        val day: String = if (c.get(Calendar.DAY_OF_MONTH) < 10)
            "0" + c.get(Calendar.DAY_OF_MONTH)
        else
            c.get(Calendar.DAY_OF_MONTH).toString()

        var diff: Int = -1

        if (c.get(Calendar.DATE) == today.get(Calendar.DATE)
            && c.get(Calendar.MONTH) == today.get(Calendar.MONTH)
            && c.get(Calendar.YEAR) == today.get(Calendar.YEAR))
            diff = 0

        if (diff == -1) {
            today.add(Calendar.DATE, -1)
            if (c.get(Calendar.DATE) == today.get(Calendar.DATE)
                && c.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                && c.get(Calendar.YEAR) == today.get(Calendar.YEAR))
                diff = 1
        }


        var hour: Int = c.get(Calendar.HOUR)
        if (hour == 0)
            hour = 12

        return (if (diff == 0) "" else if (diff == 1 && isShowYesterday) "Yesterday" else day + " " + months[c.get(Calendar.MONTH)]) + " " +
                (if (hour < 10) "0$hour" else hour) + ":" +
                (if (c.get(Calendar.MINUTE) < 10) "0${c.get(Calendar.MINUTE)}" else c.get(Calendar.MINUTE)) +
                if (c.get(Calendar.HOUR_OF_DAY) > 11) " PM" else " AM"
    }
}