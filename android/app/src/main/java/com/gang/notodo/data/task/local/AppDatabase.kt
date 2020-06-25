package com.gang.notodo.data.task.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gang.notodo.data.task.Task
import com.gang.notodo.data.user.User
import com.gang.notodo.data.user.UserDao


@Database(entities = [Task::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    abstract fun userDao(): UserDao

    companion object {

        private var instance: AppDatabase? = null

        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "tasks.db"
                        )
                            .build()
                    }

                }
            }
            return instance!!
        }
    }

}