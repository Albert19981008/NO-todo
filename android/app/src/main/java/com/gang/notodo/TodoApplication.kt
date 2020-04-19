package com.gang.notodo

import android.app.Application
import android.util.Log

class TodoApplication : Application() {

    private fun initDb() {
        Log.e("application", "init")
    }

    override fun onCreate() {
        super.onCreate()
        todoApplication = this
        initDb()
    }

    companion object {
        var todoApplication: TodoApplication? = null
    }
}