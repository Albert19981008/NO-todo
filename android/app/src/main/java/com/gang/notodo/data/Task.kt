package com.gang.notodo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * 任务实体类
 */
@Entity(tableName = "tasks")
data class Task @JvmOverloads constructor(

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",


    @ColumnInfo(name = "time_stamp")
    var stamp: Int,

    @PrimaryKey
    @ColumnInfo(name = "entryId")
    var id: String = UUID.randomUUID().toString()
) {

    /**
     * 任务是否已经完成
     */
    @ColumnInfo(name = "completed")
    var isCompleted = false

    val titleForList: String
        get() = if (title.isNotEmpty()) title else description

    val isActive
        get() = !isCompleted

    val isEmpty
        get() = title.isEmpty() && description.isEmpty()
}