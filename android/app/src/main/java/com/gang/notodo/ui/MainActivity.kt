package com.gang.notodo.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.gang.notodo.R
import com.gang.notodo.data.task.Task
import com.gang.notodo.data.task.TaskDataSource
import com.gang.notodo.data.task.TaskRepository
import com.gang.notodo.data.user.User
import com.gang.notodo.data.user.UserAgent
import com.gang.notodo.ui.calendar.CalendarActivity
import com.gang.notodo.util.loge
import com.gang.notodo.util.setupActionBar
import com.gang.notodo.util.startActivity
import com.gang.notodo.util.toast


class MainActivity : AppCompatActivity() {

    private lateinit var userNameView: EditText
    private lateinit var passwordView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        userNameView = findViewById(R.id.login_username)
        passwordView = findViewById(R.id.login_password)

        val buttonLogin: Button = findViewById(R.id.button_login)
        buttonLogin.setOnClickListener {
            login()

        }

        setupActionBar(R.id.toolBar) {
            title = "NO-todo"
        }
    }

    private fun login() {
        val userName = userNameView.text.toString()
        val password = passwordView.text.toString()
        val user = User(userName, password)
//        toast("用户名: $userName")
        UserAgent.login(user, ::doLogin, ::loginFail)
    }

    private fun doLogin(user: User) {
        toast("登录成功!!")
        startActivity<CalendarActivity>()
        testDb()
        finish()
    }

    private fun loginSuccess(user: User) {
        TaskRepository.userId = user.userId
        toast("登录成功!!")
    }

    private fun loginFail() {
        toast("用户名或密码错误, 登录失败!!")
    }

    private fun testDb() {
        TaskRepository.getTasks(object : TaskDataSource.LoadTasksCallback {
            override fun onDataNotAvailable() {
                TaskRepository.saveTask(
                    Task(
                        "testTaskTitle1",
                        "testTaskDes1",
                        2020,
                        4,
                        13
                    )
                )
                val tmp = Task(
                    "testTaskTitle2",
                    "testTaskDes2",
                    2020,
                    4,
                    26
                )
                TaskRepository.saveTask(tmp)
                TaskRepository.completeTask(tmp.id)
                TaskRepository.saveTask(
                    Task(
                        "testTaskTitle3",
                        "testTaskDes3",
                        2020,
                        5,
                        11
                    )
                )
            }

            override fun onTasksLoaded(tasks: List<Task>) {
                loge(tasks.toString())
                TaskRepository.completeTask(tasks[0].id)
            }
        })
    }
}
