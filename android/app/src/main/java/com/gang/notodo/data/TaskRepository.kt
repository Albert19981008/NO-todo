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

    private var onRefreshObservers = mutableListOf<() -> Unit>()

    override fun getTasks(callback: TaskDataSource.LoadTasksCallback) {
        when {
            loading -> blockedCallback.offer(callback)
            cacheIsDirty -> {
                blockedCallback.offer(callback)
                refreshAll()
            }
            else -> // !cacheIsDirty && !loading
                callback.onTasksLoaded(cachedTasks.values.toList())
        }
    }

    override fun getTasksByDate(
        year: Int,
        month: Int,
        day: Int,
        callback: TaskDataSource.LoadTasksCallback
    ) {
        if (!cacheIsDirty && !loading) {
            val task =
                cachedTasks.values.filter { it.year == year && it.month == month && it.day == day }
            if (task.isNotEmpty()) {
                callback.onTasksLoaded(task)
            } else {
                callback.onDataNotAvailable()
            }
        } else {
            local.getTasksByDate(year, month, day, callback)
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
        onRefresh()
    }

    override fun clearCompletedTasks() {
        cachedTasks = cachedTasks.filterValues {
            it.isActive
        } as LinkedHashMap<String, Task>
        local.clearCompletedTasks()
        onRefresh()
    }

    override fun completeTask(taskId: String) {
        cachedTasks[taskId]?.isCompleted = true
        local.completeTask(taskId)
        onRefresh()
    }

    override fun deleteAllTasks() {
        cachedTasks.clear()
        local.deleteAllTasks()
        onRefresh()
    }

    override fun deleteTask(taskId: String) {
        cachedTasks.remove(taskId)
        local.deleteTask(taskId)
        onRefresh()
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

    private fun onNotAvailable() {
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
                onNotAvailable()
            }
        })
    }

    private fun onRefresh() {
        onRefreshObservers.forEach {
            it.invoke()
        }
    }

    fun addOnRefreshObserver(onRefreshObserver: () -> Unit) {
        onRefreshObservers.add(onRefreshObserver)
    }

    fun removeOnRefreshObserver(onRefreshObserver: () -> Unit) {
        onRefreshObservers.remove(onRefreshObserver)
    }
}