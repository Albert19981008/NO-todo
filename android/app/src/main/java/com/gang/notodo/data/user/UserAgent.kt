package com.gang.notodo.data.user

import com.gang.notodo.TodoApplication
import com.gang.notodo.data.task.local.AppDatabase
import com.gang.notodo.util.AppExecutors


object UserAgent {

    private val userDao: UserDao = AppDatabase.getInstance(TodoApplication.instance).userDao()

    private val appExecutors = AppExecutors

    fun login(user: User, successCb: (User) -> Unit, failCb: () -> Unit) {
        appExecutors.diskIO.execute {
            val userSearching = userDao.getUserByName(user.userName)
            if (userSearching != null && userSearching.password == user.password) {
                appExecutors.mainThread.execute {
                    successCb.invoke(userSearching)
                }
            } else {
                appExecutors.mainThread.execute(failCb)
            }
        }
    }

    fun register(user: User, successCb: (User) -> Unit, failCb: () -> Unit) {
        appExecutors.diskIO.execute {
            val userSearching = userDao.getUserByName(user.userName)
            if (userSearching != null) {
                appExecutors.mainThread.execute(failCb)
            } else {
                appExecutors.diskIO.execute {
                    userDao.insertUser(user)
                    appExecutors.mainThread.execute {
                        successCb.invoke(user)
                    }
                }
            }
        }
    }
}