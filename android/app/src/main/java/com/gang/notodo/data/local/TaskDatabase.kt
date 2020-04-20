package com.gang.notodo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gang.notodo.data.Task


@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        private var INSTANCE: TaskDatabase? = null

        private val LOCK = Any()

        fun getInstance(context: Context): TaskDatabase {
            synchronized(LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TaskDatabase::class.java, "tasks.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }

}