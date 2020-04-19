package com.gang.notodo.calendar

object RandomColor {

    private val allColor: List<Int> = arrayListOf(
        -0xbf24db, -0x196ec8, -0x20ecaa, -0x123a93,
        -0x5533bc, -0x43ec10, -0xec5310
    )

    fun getRandomColor(): Int {
        return allColor.random()
    }
}