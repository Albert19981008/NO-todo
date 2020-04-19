package com.gang.notodo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gang.notodo.calendar.ListActivity
import com.gang.notodo.data.Task
import com.gang.notodo.data.local.TaskDao
import com.gang.notodo.util.AppExecutors
import com.gang.notodo.util.loge
import com.gang.notodo.util.startActivity
import com.gang.notodo.util.toast

class MainActivity : AppCompatActivity() {

    private val executors = AppExecutors

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun testDao() {
        executors.diskIO.execute {
            val dao: TaskDao = TodoApplication.todoApplication
                ?.todoDatabase
                ?.taskDao()!!
            val task = Task("testTitle", "testDes")
            dao.insertTask(task)
            val li = dao.getAllTasks()
            loge(li.toString())
            dao.deleteTasks()
            executors.mainThread.execute { toast(li.toString()) }
        }
    }

    private fun testNewActivity() {
        startActivity<ListActivity>()
    }

    private fun initView() {
        val buttonLogin: Button = findViewById(R.id.button_login)
        buttonLogin.setOnClickListener {
            testDao()
            testNewActivity()
        }
    }
}
