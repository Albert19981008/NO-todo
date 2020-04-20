package com.gang.notodo.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gang.notodo.R
import com.gang.notodo.data.Task
import com.gang.notodo.data.TaskDataSource
import com.gang.notodo.data.TaskRepository
import com.gang.notodo.util.loge
import com.gang.notodo.util.setupActionBar
import com.gang.notodo.util.startActivity
import com.gang.notodo.util.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val buttonLogin: Button = findViewById(R.id.button_login)
        buttonLogin.setOnClickListener {
            startActivity<TaskActivity>()
            testDb()
        }

        setupActionBar(R.id.toolBar) {
            title = "NO-todo"
        }
    }

    private fun testDb() {

        TaskRepository.saveTask(Task("testTitle", "testDes"))
        TaskRepository.refreshAll()
        TaskRepository.getTasks(object : TaskDataSource.LoadTasksCallback {
            override fun onDataNotAvailable() {
            }

            override fun onTasksLoaded(tasks: List<Task>) {
                loge(tasks.toString())
                toast(tasks.toString())
            }

        })
        TaskRepository.deleteAllTasks()
    }
}
