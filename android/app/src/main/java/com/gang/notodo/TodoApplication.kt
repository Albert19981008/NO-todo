package com.gang.notodo

import android.app.Application
import com.gang.notodo.data.TaskRepository


class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        TaskRepository.refreshAll()
    }

    companion object {
        var instance: TodoApplication? = null
    }
}