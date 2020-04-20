package com.gang.notodo.data.local

import androidx.room.*
import com.gang.notodo.data.Task

@Dao
interface TaskDao {

    @Query("select * from tasks")
    fun getAllTasks(): List<Task>

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
    fun deleteTasks()


    @Query("delete from tasks where completed = 1")
    fun deleteCompletedTasks(): Int
}