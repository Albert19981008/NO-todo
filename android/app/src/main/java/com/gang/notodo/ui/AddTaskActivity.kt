package com.gang.notodo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.gang.notodo.R

class AddTaskActivity : AppCompatActivity() {

    private lateinit var title: EditText
    private lateinit var description: EditText
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
    }

    companion object {

        const val YEAR = "year"
        const val MONTH = "month"
        const val DAY = "day"

        fun getIntent(context: Context, year: Int, month: Int, day: Int): Intent =
            Intent(context, AddTaskActivity::class.java).apply {
                putExtra(YEAR, year)
                putExtra(MONTH, month)
                putExtra(DAY, day)
            }
    }
}
