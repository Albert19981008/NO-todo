package com.gang.notodo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gang.notodo.data.User

@Dao
interface UserDao {

    @Query("select * from users where userId = :id")
    fun getUserById(id: String): User

    @Query("select * from users where userName like :name")
    fun getUserByName(name: String): User

    @Insert
    fun insertUser(user: User)
}