package com.gang.notodo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gang.notodo.data.Task
import com.gang.notodo.data.local.TaskDao
import com.gang.notodo.data.local.TodoDatabase
import com.gang.notodo.util.AppExecutors

class MainActivity : AppCompatActivity() {

    private val executors = AppExecutors()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun testDao() {
        val dao: TaskDao = TodoDatabase.getInstance(this).taskDao()
        val task = Task("testTitle", "testDes")
        dao.insertTask(task)
        val li = dao.getAllTasks()
        Log.e("test", "sssssssss")
        Log.e("test", li.toString())

    }

    private fun initView() {
        //测试按钮
        val buttonLogin: Button = findViewById(R.id.button_login)

        //设置Login监听器
        buttonLogin.setOnClickListener {
            executors.diskIO.execute {
                testDao()
            }
        }
    }
}
