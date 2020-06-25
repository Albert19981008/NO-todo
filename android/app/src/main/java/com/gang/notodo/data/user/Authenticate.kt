package com.gang.notodo.data.user


interface Authenticate {

    fun login(user: User, successCb: (User) -> Unit, failCb: (String) -> Unit)

    fun register(user: User, successCb: () -> Unit, failCb: (String) -> Unit)
}