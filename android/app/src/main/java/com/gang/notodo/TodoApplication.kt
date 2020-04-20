package com.gang.notodo

import android.app.Application
import com.gang.notodo.data.local.TaskDatabase


class TodoApplication : Application() {

    var taskDatabase: TaskDatabase? = null

    private fun initDb() {
        taskDatabase = TaskDatabase.getInstance(this)
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