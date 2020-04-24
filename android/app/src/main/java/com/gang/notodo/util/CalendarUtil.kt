package com.gang.notodo.util

import android.content.Context
import com.haibin.calendarview.Calendar

object CalendarUtil {

    private val allColor: List<Int> = arrayListOf(
        -0xbf24db, -0x196ec8, -0x20ecaa, -0x123a93,
        -0x5533bc, -0x43ec10, -0xec5310
    )

    fun getRandomColor(): Int {
        return allColor.random()
    }

    fun getSchemeCalendar(
        year: Int,
        month: Int,
        day: Int,
        color: Int
    ): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        return calendar
    }

    fun timeStampToCalendar(timeMillis: Long): Calendar {
        val javaUtilCalendar = java.util.Calendar.getInstance()
        javaUtilCalendar.timeInMillis = timeMillis
        return Calendar().apply {
            this.year = javaUtilCalendar.get(java.util.Calendar.YEAR)
            this.month = javaUtilCalendar.get(java.util.Calendar.MONTH) + 1
            this.day = javaUtilCalendar.get(java.util.Calendar.DAY_OF_MONTH)
        }
    }

    fun calendarToTimeStamp(calendar: Calendar): Long {
        return calendar.timeInMillis
    }
}