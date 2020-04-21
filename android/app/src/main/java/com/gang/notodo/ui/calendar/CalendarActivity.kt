package com.gang.notodo.ui.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gang.notodo.R
import com.gang.notodo.data.Task
import com.gang.notodo.data.TaskDataSource
import com.gang.notodo.data.TaskRepository
import com.gang.notodo.ui.AddTaskActivity
import com.gang.notodo.ui.ListActivity
import com.gang.notodo.util.CalendarUtil
import com.gang.notodo.util.CalendarUtil.getSchemeCalendar
import com.gang.notodo.util.setupActionBar
import com.gang.notodo.util.startActivity
import com.gang.notodo.util.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView


class CalendarActivity : AppCompatActivity(),
    CalendarView.OnCalendarSelectListener,
    CalendarView.OnCalendarLongClickListener,
    CalendarView.OnYearChangeListener {


    private lateinit var mRootView: View

    private lateinit var mToolBar: Toolbar

    private lateinit var mTextMonthDay: TextView

    private lateinit var mTextYear: TextView

    private lateinit var mTextLunar: TextView

    private lateinit var mCalendarView: CalendarView

    private lateinit var mAddButton: FloatingActionButton

    private lateinit var mRelativeTool: LinearLayout

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mRecyclerViewAdapter: TaskRecyclerViewAdapter

    private val dateWhichHasTasks = HashMap<String, Calendar>()

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        initView()
        initData()
    }

    override fun onResume() {
        super.onResume()
        reloadRecyclerView(getSchemeCalendar(mYear, mMonth, mDay, 0))
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        mRootView = findViewById(R.id.root)
        mTextMonthDay = findViewById(R.id.tv_month_day)
        mTextYear = findViewById(R.id.tv_year)
        mTextLunar = findViewById(R.id.tv_lunar)
        mRelativeTool = findViewById(R.id.rl_tool)
        mCalendarView = findViewById(R.id.calendarView)
        mTextMonthDay.setOnClickListener {
            mCalendarView.showYearSelectLayout(mYear)
            mTextMonthDay.text = mYear.toString()
        }

        mAddButton = findViewById(R.id.fab_edit_task_add)
        mAddButton.setOnClickListener {
            startActivity(AddTaskActivity.getIntent(this, mYear, mMonth, mDay))
        }

        mCalendarView.setOnCalendarSelectListener(this)
        mCalendarView.setOnYearChangeListener(this)
        mCalendarView.setOnCalendarLongClickListener(this, false)
        mTextYear.text = mCalendarView.curYear.toString()
        mYear = mCalendarView.curYear
        mMonth = mCalendarView.curMonth
        mDay = mCalendarView.curDay
        mTextMonthDay.text = mCalendarView.curMonth.toString() + "月" + mCalendarView.curDay + "日"
        mTextLunar.text = "今日"

        initToolBar()
        initRecyclerView()
    }

    private fun initToolBar() {
        mToolBar = findViewById(R.id.toolBar)
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
                    R.id.item_todo -> startActivity<ListActivity>()
                    R.id.item_calendar -> toast("已经是日历页")
                }
                false
            }
        }
    }

    private fun initRecyclerView() {
        mRecyclerView = findViewById(R.id.calendar_recycler)
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        //设置分割线
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        mRecyclerViewAdapter = TaskRecyclerViewAdapter(this)
        mRecyclerView.adapter = mRecyclerViewAdapter
    }

    private fun initData() {
        TaskRepository.getTasks(object : TaskDataSource.LoadTasksCallback {

            override fun onTasksLoaded(tasks: List<Task>) {
                dateWhichHasTasks.clear()
                tasks.distinctBy { "" + it.year + "#" + it.month + "#" + it.day }
                    .filter { it.isActive }
                    .forEach {
                        getSchemeCalendar(
                            it.year,
                            it.month,
                            it.day,
                            CalendarUtil.getRandomColor()
                        ).let { it2 ->
                            dateWhichHasTasks[it2.toString()] = it2
                        }

                    }
                mCalendarView.setSchemeDate(dateWhichHasTasks)
            }

            override fun onDataNotAvailable() {
                // nothing
            }

        })
    }

    @SuppressLint("SetTextI18n")
    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        calendar?.let {
            mTextMonthDay.text = it.month.toString() + "月" + it.day + "日"
            mTextYear.text = it.year.toString()
            mTextLunar.text = it.lunar
            mYear = it.year
            reloadRecyclerView(it)
        }
    }



    private fun reloadRecyclerView(calendar: Calendar) {

        TaskRepository.getTasksByDate(calendar.year, calendar.month, calendar.day,

            object : TaskDataSource.LoadTasksCallback {

                override fun onTasksLoaded(tasks: List<Task>) {
                    mRecyclerViewAdapter.mDataList = tasks
                    mRecyclerViewAdapter.notifyDataSetChanged()
                    moveToPosition(mRecyclerView.layoutManager as LinearLayoutManager,0)
                }

                override fun onDataNotAvailable() {
                    mRecyclerViewAdapter.mDataList = arrayListOf()
                    mRecyclerViewAdapter.notifyDataSetChanged()
                    moveToPosition(mRecyclerView.layoutManager as LinearLayoutManager,0)
                }
            })

    }

    fun moveToPosition(manager: LinearLayoutManager, n: Int) {
        manager.scrollToPositionWithOffset(n, 0)
        manager.stackFromEnd = true
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
        // nothing
    }

    override fun onCalendarLongClickOutOfRange(calendar: Calendar?) {
        // nothing
    }

    override fun onCalendarLongClick(calendar: Calendar?) {
        calendar?.let {
            Log.e("onDateLongClick", "--" + it.day + "--" + it.month)
        }
    }

    override fun onYearChange(year: Int) {
        mTextMonthDay.text = year.toString()
    }

}
