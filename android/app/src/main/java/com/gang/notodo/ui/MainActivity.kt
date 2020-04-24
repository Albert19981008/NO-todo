package com.gang.notodo.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gang.notodo.R
import com.gang.notodo.data.Task
import com.gang.notodo.data.TaskDataSource
import com.gang.notodo.data.TaskRepository
import com.gang.notodo.ui.calendar.CalendarActivity
import com.gang.notodo.util.loge
import com.gang.notodo.util.setupActionBar
import com.gang.notodo.util.startActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val buttonLogin: Button = findViewById(R.id.button_login)
        buttonLogin.setOnClickListener {
            startActivity<CalendarActivity>()
            testDb()
            finish()
        }

        setupActionBar(R.id.toolBar) {
            title = "NO-todo"
        }
    }

    private fun testDb() {
        TaskRepository.getTasks(object : TaskDataSource.LoadTasksCallback {
            override fun onDataNotAvailable() {
                TaskRepository.saveTask(Task("testTaskTitle1", "testTaskDes1", 2020, 4, 13))
                val tmp = Task("testTaskTitle2", "testTaskDes2", 2020, 4, 26)
                TaskRepository.saveTask(tmp)
                TaskRepository.completeTask(tmp.id)
                TaskRepository.saveTask(Task("testTaskTitle3", "testTaskDes3", 2020, 5, 11))
            }

            override fun onTasksLoaded(tasks: List<Task>) {
                loge(tasks.toString())
                TaskRepository.completeTask(tasks[0].id)
            }
        })
    }
}
