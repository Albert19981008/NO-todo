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
        color: Int,
        text: String
    ): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        return calendar
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    @JvmStatic
    fun dipToPx(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}