package com.gang.notodo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.gang.notodo.R
import com.gang.notodo.data.Task
import com.gang.notodo.data.TaskRepository
import com.gang.notodo.ui.calendar.CalendarActivity
import com.gang.notodo.util.setupActionBar
import com.gang.notodo.util.startActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddTaskActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var mToolBar: Toolbar
    private lateinit var mAddButton: FloatingActionButton
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        initDate()
        initView()
    }

    private fun initDate() {
        year = intent.getIntExtra(YEAR, 0)
        month = intent.getIntExtra(MONTH, 0)
        day = intent.getIntExtra(DAY, 0)
    }

    private fun initView() {
        title = findViewById(R.id.add_task_title)
        description = findViewById(R.id.add_task_description)

        mAddButton = findViewById(R.id.fab_edit_task_done)
        mAddButton.setOnClickListener {
            doAddTaskAndFinish()
        }

        mToolBar = findViewById(R.id.toolBar)
        initToolBar()
    }

    private fun initToolBar() {
        setupActionBar(R.id.toolBar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
            title = "NO-todo"
        }
        mToolBar.setNavigationOnClickListener { v ->
            val popup = PopupMenu(this, v)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.menu_indicator, popup.menu)
            popup.show()
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_calendar -> startActivity<CalendarActivity>()
                    R.id.item_todo -> startActivity<ListActivity>()
                }
                false
            }
        }
    }

    private fun doAddTaskAndFinish() {
        val task = Task(title.text.toString(), description.text.toString(), year, month, day)
        TaskRepository.saveTask(task)
        startActivity<CalendarActivity>()
        finish()
    }

    companion object {

        const val YEAR = "year"
        const val MONTH = "month"
        const val DAY = "day"

        fun getIntent(context: Context, date: CalendarActivity.MyDate): Intent =
            Intent(context, AddTaskActivity::class.java).apply {
                putExtra(YEAR, date.year)
                putExtra(MONTH, date.month)
                putExtra(DAY, date.day)
            }
    }
}