package com.gang.notodo.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.gang.notodo.R
import com.gang.notodo.data.task.Task
import com.gang.notodo.data.task.TaskDataSource
import com.gang.notodo.data.task.TaskRepository
import com.gang.notodo.data.user.Authenticate
import com.gang.notodo.data.user.User
import com.gang.notodo.data.user.UserAgent
import com.gang.notodo.ui.calendar.CalendarActivity
import com.gang.notodo.util.loge
import com.gang.notodo.util.setupActionBar
import com.gang.notodo.util.startActivity
import com.gang.notodo.util.toast


class LoginActivity : AppCompatActivity() {

    private lateinit var userNameView: EditText
    private lateinit var passwordView: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private val authenticate: Authenticate = UserAgent
    private lateinit var sps: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sps = getSharedPreferences("login_name", MODE_PRIVATE)
        initView()
    }

    private fun initView() {
        userNameView = findViewById(R.id.login_username)
        passwordView = findViewById(R.id.login_password)
        userNameView.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        passwordView.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        userNameView.keyListener = EnglishDigitsKeyListener()
        passwordView.keyListener = EnglishDigitsKeyListener()

        loginButton = findViewById(R.id.button_login)
        registerButton = findViewById(R.id.button_register)

        loginButton.setOnClickListener {
            login()
        }

        registerButton.setOnClickListener {
            register()
        }

        setupActionBar(R.id.toolBar) {
            title = "NO-todo"
        }

        sps.getString(NAME_KEY, "")
            .takeIf { !it.isNullOrBlank() }
            ?.let { userNameView.setText(it) }
    }

    private fun login() {
        val userName = userNameView.text.toString()
        val password = passwordView.text.toString()
        val user = User(userName, password)
        authenticate.login(user, ::doLogin, ::fail)
    }

    private fun doLogin(user: User) {
        toast("登录成功!!")
        TaskRepository.userId = user.userId
        startActivity<CalendarActivity>()
        testDb()
        sps.edit()
            .apply {
                putString(NAME_KEY, userNameView.text.toString())
                apply()
            }
        finish()
    }

    private fun register() {
        val userName = userNameView.text.toString()
        val password = passwordView.text.toString()
        val user = User(userName, password)
        if (userName.isEmpty() || password.isEmpty()) {
            fail("用户名和密码不能为空, 失败!!")
            return
        }
        authenticate.register(user, ::registerSuccess, ::fail)
    }

    private fun registerSuccess() {
        toast("注册成功!!")
    }

    private fun fail(msg: String) = toast(msg)

    private fun testDb() {
        TaskRepository.getTasks(TestTaskGenerator())
    }

    private class EnglishDigitsKeyListener : DigitsKeyListener() {

        override fun getInputType() = InputType.TYPE_TEXT_VARIATION_PASSWORD

        override fun getAcceptedChars() = ENG_CHAR_AND_NUM.toCharArray()

        companion object {
            const val ENG_CHAR_AND_NUM =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
        }
    }

    private class TestTaskGenerator : TaskDataSource.LoadTasksCallback {

        override fun onTasksLoaded(tasks: List<Task>) {
            loge(tasks.toString())
            TaskRepository.completeTask(tasks[0].id)
        }

        override fun onDataNotAvailable() {
            TaskRepository.saveTask(
                Task(
                    "testTaskTitle1",
                    "testTaskDes1",
                    2020,
                    7,
                    13,
                    TaskRepository.userId
                )
            )
            val tmp = Task(
                "testTaskTitle2",
                "testTaskDes2",
                2020,
                6,
                26,
                TaskRepository.userId
            )
            TaskRepository.saveTask(tmp)
            TaskRepository.completeTask(tmp.id)
            TaskRepository.saveTask(
                Task(
                    "testTaskTitle3",
                    "testTaskDes3",
                    2020,
                    7,
                    11,
                    TaskRepository.userId
                )
            )
        }
    }

    companion object {
        const val NAME_KEY = "name_key"
    }
}
