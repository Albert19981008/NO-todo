package com.gang.notodo.data.task.local

import androidx.room.*
import com.gang.notodo.data.task.Task

@Dao
interface TaskDao {

    @Query("select * from tasks")
    fun getAllTasks(): List<Task>

    @Query("select * from tasks where year = :year and month = :month and day = :day")
    fun getTasksByDate(year: Int, month: Int, day: Int): List<Task>

    /**
     * 通过 id 查找任务
     */
    @Query("select * from tasks where entryId = :taskId")
    fun getTaskById(taskId: String): Task?

    /**
     * 插入一条 Task
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    /**
     * Update a task.
     *
     * @return 被更新的 Task数目 本值永远为 1
     */
    @Update
    fun updateTask(task: Task): Int

    /**
     * 把任务标记为已完成
     */
    @Query("update tasks set completed = :completed where entryId = :taskId")
    fun updateCompleted(taskId: String, completed: Boolean)


    @Query("delete from tasks where entryId = :taskId")
    fun deleteTaskById(taskId: String): Int


    @Query("delete from tasks")
    fun deleteAllTasks()


    @Query("delete from tasks where completed = 1")
    fun deleteCompletedTasks(): Int
}