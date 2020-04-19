package com.gang.notodo

import android.app.Application
import androidx.room.Room
import com.gang.notodo.data.local.TodoDatabase


class TodoApplication : Application() {

    var todoDatabase: TodoDatabase? = null

    private fun initDb() {
        todoDatabase = TodoDatabase.getInstance(this)
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