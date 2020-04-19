package com.gang.notodo.util

import android.content.Context
import android.util.Log
import android.widget.Toast


fun Any.loge(msg: String) {
    Log.e("_" + this::class.java.simpleName, msg)
}

fun Context.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}