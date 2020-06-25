package com.gang.notodo.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "users")
data class User @JvmOverloads constructor(

    @ColumnInfo(name = "userName")
    var userName: String = "",

    @ColumnInfo(name = "password")
    var password: String = "",

    @PrimaryKey
    @ColumnInfo(name = "userId")
    var userId: String = UUID.randomUUID().toString()
)