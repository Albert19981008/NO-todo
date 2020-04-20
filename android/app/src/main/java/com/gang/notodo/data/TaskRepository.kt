package com.gang.notodo.data

import com.gang.notodo.data.local.TaskLocalSource
import java.util.*


/**
 * Task 的仓库
 * 所有此类的方法 必须在主线程中调用
 */
object TaskRepository : TaskDataSource, TaskCache {

    private var cacheIsDirty = true

    private var loading = false

    private val local = TaskLocalSource

    private var cachedTasks: LinkedHashMap<String, Task> = LinkedHashMap()

    private val blockedCallback: Queue<TaskDataSource.LoadTasksCallback> =
        LinkedList<TaskDataSource.LoadTasksCallback>()

    override fun getTasks(callback: TaskDataSource.LoadTasksCallback) {
        when {
            loading -> blockedCallback.offer(callback)
            cacheIsDirty -> {
                refreshAll()
                blockedCallback.offer(callback)
            }
            else -> // !cacheIsDirty && !loading
                callback.onTasksLoaded(cachedTasks.values.toList())
        }
    }

    override fun getTask(taskId: String, callback: TaskDataSource.GetTaskCallback) {
        if (!cacheIsDirty && !loading) {
            val task = cachedTasks[taskId]
            task?.let { callback.onTaskLoaded(it) }
        } else {
            local.getTask(taskId, callback)
        }
    }

    override fun saveTask(task: Task) {
        cachedTasks[task.id] = task
        local.saveTask(task)
    }

    override fun clearCompletedTasks() {
        cachedTasks = cachedTasks.filterValues {
            !it.isCompleted
        } as LinkedHashMap<String, Task>
        local.clearCompletedTasks()
    }

    override fun completeTask(taskId: String) {
        cachedTasks[taskId]?.isCompleted = true
        local.completeTask(taskId)
    }

    override fun deleteAllTasks() {
        cachedTasks.clear()
        local.deleteAllTasks()
    }

    override fun deleteTask(taskId: String) {
        cachedTasks.remove(taskId)
        local.deleteTask(taskId)
    }

    private fun doRefreshCache(tasks: List<Task>) {
        cachedTasks.clear()
        tasks.forEach {
            cachedTasks[it.id] = it
        }
        cacheIsDirty = false
        loading = false
        while (!blockedCallback.isEmpty()) {
            blockedCallback.poll()?.onTasksLoaded(tasks)
        }
    }

    private fun notAvailable() {
        loading = false
        while (!blockedCallback.isEmpty()) {
            blockedCallback.poll()?.onDataNotAvailable()
        }
    }

    override fun refreshAll() {
        if (loading) {
            return
        }
        cacheIsDirty = true
        loading = true
        cachedTasks.clear()
        local.getTasks(object : TaskDataSource.LoadTasksCallback {

            override fun onTasksLoaded(tasks: List<Task>) {
                doRefreshCache(tasks)
            }

            override fun onDataNotAvailable() {
                notAvailable()
            }
        })
    }
}