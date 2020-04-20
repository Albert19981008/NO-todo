package com.gang.notodo.util

import android.content.Context

object CalendarUtil {

    private val allColor: List<Int> = arrayListOf(
        -0xbf24db, -0x196ec8, -0x20ecaa, -0x123a93,
        -0x5533bc, -0x43ec10, -0xec5310
    )

    fun getRandomColor(): Int {
        return allColor.random()
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