package com.gang.notodo.data.task

import androidx.room.*
import java.util.*

/**
 * 任务实体类
 */
@Entity(tableName = "tasks", indices = [Index(value = ["year", "month", "day"])])
data class Task @JvmOverloads constructor(

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "year")
    var year: Int = 0,

    @ColumnInfo(name = "month")
    var month: Int = 0,

    @ColumnInfo(name = "day")
    var day: Int = 0,

    @ColumnInfo(name = "userId")
    var userId: String = "",

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