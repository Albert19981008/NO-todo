package com.gang.notodo.data.local

import com.gang.notodo.TodoApplication
import com.gang.notodo.data.Task
import com.gang.notodo.data.TaskDataSource
import com.gang.notodo.util.AppExecutors


object TaskLocalSource : TaskDataSource {

    private val appExecutors = AppExecutors
    private val dao: TaskDao = AppDatabase.getInstance(TodoApplication.instance!!).taskDao()

    override fun getTasks(callback: TaskDataSource.LoadTasksCallback) {
        appExecutors.diskIO.execute {
            val tasks = dao.getAllTasks()
            appExecutors.mainThread.execute {
                if (tasks.isNotEmpty()) {
                    callback.onTasksLoaded(tasks)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun getTasksByDate(
        year: Int,
        month: Int,
        day: Int,
        callback: TaskDataSource.LoadTasksCallback
    ) {
        appExecutors.diskIO.execute {
            val tasks = dao.getTasksByDate(year, month, day)
            appExecutors.mainThread.execute {
                if (tasks.isEmpty()) {
                    callback.onTasksLoaded(tasks)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun getTask(taskId: String, callback: TaskDataSource.GetTaskCallback) {
        appExecutors.diskIO.execute {
            val task = dao.getTaskById(taskId)
            appExecutors.mainThread.execute {
                if (task != null) {
                    callback.onTaskLoaded(task)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun saveTask(task: Task) {
        appExecutors.diskIO.execute {
            dao.insertTask(task)
        }
    }

    override fun completeTask(taskId: String) {
        appExecutors.diskIO.execute {
            dao.updateCompleted(taskId, true)
        }
    }

    override fun clearCompletedTasks() {
        appExecutors.diskIO.execute {
            dao.deleteCompletedTasks()
        }
    }

    override fun deleteAllTasks() {
        appExecutors.diskIO.execute {
            dao.deleteAllTasks()
        }
    }

    override fun deleteTask(taskId: String) {
        appExecutors.diskIO.execute {
            dao.deleteTaskById(taskId)
        }
    }
}
