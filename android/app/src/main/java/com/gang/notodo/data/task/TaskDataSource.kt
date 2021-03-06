package com.gang.notodo.data.task


interface TaskDataSource {

    interface LoadTasksCallback {

        fun onTasksLoaded(tasks: List<Task>)

        fun onDataNotAvailable()
    }

    interface GetTaskCallback {

        fun onTaskLoaded(task: Task)

        fun onDataNotAvailable()
    }

    fun getTasks(callback: LoadTasksCallback)

    fun getTasksByDate(year: Int, month: Int, day: Int, callback: LoadTasksCallback)

    fun getTask(taskId: String, callback: GetTaskCallback)

    fun saveTask(task: Task)

    fun completeTask(taskId: String)

    fun clearCompletedTasks()

    fun deleteAllTasks()

    fun deleteTask(taskId: String)
}