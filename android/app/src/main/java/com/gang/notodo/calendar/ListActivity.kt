package com.gang.notodo.calendar

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gang.notodo.R
import com.gang.notodo.util.loge
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import java.util.*
import kotlin.random.Random

class ListActivity : AppCompatActivity(),
    CalendarView.OnCalendarSelectListener,
    CalendarView.OnCalendarLongClickListener,
    CalendarView.OnYearChangeListener {

    private lateinit var  mTextMonthDay: TextView

    private lateinit var  mTextYear: TextView

    private lateinit var mTextLunar: TextView

    private lateinit var mCalendarView: CalendarView

    private lateinit var mRelativeTool: LinearLayout

    private var mYear: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initView()
        initData()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        mTextMonthDay = findViewById(R.id.tv_month_day)
        mTextYear = findViewById(R.id.tv_year)
        mTextLunar = findViewById(R.id.tv_lunar)
        mRelativeTool = findViewById(R.id.rl_tool)
        mCalendarView = findViewById(R.id.calendarView)
        mTextMonthDay.setOnClickListener {
            mCalendarView.showYearSelectLayout(mYear)
            mTextMonthDay.setText(mYear.toString())
        }

        mCalendarView.setOnCalendarSelectListener(this)
        mCalendarView.setOnYearChangeListener(this)
        mCalendarView.setOnCalendarLongClickListener(this, false)
        mTextYear.text = mCalendarView.curYear.toString()
        mYear = mCalendarView.curYear
        loge(mYear.toString())
        mTextMonthDay.text = mCalendarView.curMonth.toString() + "月" + mCalendarView.curDay + "日"
        mTextLunar.text = "今日"
    }

    private fun initData() {

        val year = mCalendarView.curYear
        val month = mCalendarView.curMonth

        val map = HashMap<String, Calendar>()


        for (i in 1..28) {
            if (Random.nextBoolean()) {
                val c = getSchemeCalendar(year, month, i, RandomColor.getRandomColor(), "")
                map[c.toString()] = c
            }
        }

        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        mCalendarView.setSchemeDate(map)

    }

    private fun getSchemeCalendar(
        year: Int,
        month: Int,
        day: Int,
        color: Int,
        text: String
    ): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        return calendar
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

    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
        // nothing
    }

    override fun onCalendarLongClickOutOfRange(calendar: Calendar?) {
        // nothing
    }

    override fun onCalendarLongClick(calendar: Calendar?) {
        Log.e("onDateLongClick", "  -- " + calendar?.getDay() + "  --  " + calendar?.getMonth())
    }

    override fun onYearChange(year: Int) {
        mTextMonthDay.text = year.toString()
    }

}
