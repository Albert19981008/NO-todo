package com.gang.notodo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.gang.notodo.R
import com.gang.notodo.util.CalendarUtil
import com.gang.notodo.util.CalendarUtil.getSchemeCalendar
import com.gang.notodo.util.setupActionBar
import com.gang.notodo.util.startActivity
import com.gang.notodo.util.toast
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import java.util.*
import kotlin.random.Random


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

    private lateinit var mRelativeTool: LinearLayout

    private var mYear: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        initView()
        initData()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        mToolBar = findViewById(R.id.toolBar)
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

        mCalendarView.setOnCalendarSelectListener(this)
        mCalendarView.setOnYearChangeListener(this)
        mCalendarView.setOnCalendarLongClickListener(this, false)
        mTextYear.text = mCalendarView.curYear.toString()
        mYear = mCalendarView.curYear
        mTextMonthDay.text = mCalendarView.curMonth.toString() + "月" + mCalendarView.curDay + "日"
        mTextLunar.text = "今日"

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
                    R.id.item_todo -> startActivity<ListActivity>()
                    R.id.item_calendar -> toast("已经是日历页")
                }
                false
            }
        }
    }

    private fun initData() {
        randomInitData()
    }

    private fun randomInitData() {
        val year = mCalendarView.curYear
        val month = mCalendarView.curMonth
        val map = HashMap<String, Calendar>()

        for (i in 1..28) {
            if (Random.nextBoolean()) {
                val c = getSchemeCalendar(year, month, i, CalendarUtil.getRandomColor(), "")
                map[c.toString()] = c
            }
        }

        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map)
    }

    @SuppressLint("SetTextI18n")
    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        mTextLunar.visibility = View.VISIBLE
        mTextYear.visibility = View.VISIBLE
        calendar?.let {
            mTextMonthDay.text = it.month.toString() + "月" + it.day + "日"
            mTextYear.text = it.year.toString()
            mTextLunar.text = it.lunar
            mYear = it.year
        }
        toast(calendar.toString())
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
        // nothing
    }

    override fun onCalendarLongClickOutOfRange(calendar: Calendar?) {
        // nothing
    }

    override fun onCalendarLongClick(calendar: Calendar?) {
        calendar?.let {
            Log.e("onDateLongClick", "  -- " + it.day + "  --  " + it.month)
        }
    }

    override fun onYearChange(year: Int) {
        mTextMonthDay.text = year.toString()
    }

}
