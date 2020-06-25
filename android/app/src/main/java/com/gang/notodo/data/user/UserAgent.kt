package com.gang.notodo.data.user

import com.gang.notodo.TodoApplication
import com.gang.notodo.data.task.local.AppDatabase
import com.gang.notodo.util.AppExecutors


object UserAgent : Authenticate {

    private val userDao: UserDao = AppDatabase.getInstance(TodoApplication.instance).userDao()

    private val appExecutors = AppExecutors

    override fun login(user: User, successCb: (User) -> Unit, failCb: (String) -> Unit) {
        appExecutors.diskIO.execute {
            val userSearching = userDao.getUserByName(user.userName)
            if (userSearching != null && userSearching.password == user.password) {
                appExecutors.mainThread.execute {
                    successCb.invoke(userSearching)
                }
            } else {
                appExecutors.mainThread.execute {
                    failCb.invoke("用户名或密码错误, 登录失败!!")
                }
            }
        }
    }

    override fun register(user: User, successCb: () -> Unit, failCb: (String) -> Unit) {
        appExecutors.diskIO.execute {
            val userSearching = userDao.getUserByName(user.userName)
            if (userSearching != null) {
                appExecutors.mainThread.execute {
                    failCb.invoke("用户名已存在, 注册失败!!")
                }
            } else {
                appExecutors.diskIO.execute {
                    userDao.insertUser(user)
                    appExecutors.mainThread.execute(successCb)
                }
            }
        }
    }
}