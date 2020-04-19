package com.gang.notodo.calendar

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.gang.notodo.R
import com.gang.notodo.util.loge
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarLayout
import com.haibin.calendarview.CalendarView

class ListActivity : AppCompatActivity(),
    CalendarView.OnCalendarSelectListener,
    CalendarView.OnCalendarLongClickListener,
    CalendarView.OnYearChangeListener {

    private lateinit var  mTextMonthDay: TextView

    private lateinit var  mTextYear: TextView

    private lateinit var mTextLunar: TextView

//    private lateinit var mTextCurrentDay: TextView

    private lateinit var mCalendarView: CalendarView

    private lateinit var mRelativeTool: LinearLayout

    private var mYear: Int = 0

    private lateinit var mCalendarLayout: CalendarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initView()
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        mTextMonthDay = findViewById(R.id.tv_month_day)
        mTextYear = findViewById(R.id.tv_year)
        mTextLunar = findViewById(R.id.tv_lunar)
        mRelativeTool = findViewById(R.id.rl_tool)
        mCalendarView = findViewById(R.id.calendarView)
        mTextMonthDay.setOnClickListener(View.OnClickListener {
//            if (!mCalendarLayout.isExpand) {
//                mCalendarLayout.expand()
//                return@OnClickListener
//            }
            mCalendarView.showYearSelectLayout(mYear)
//            mTextLunar.setVisibility(View.GONE)
//            mTextYear.setVisibility(View.GONE)
            mTextMonthDay.setText(mYear.toString())
        })

        mCalendarView.setOnCalendarSelectListener(this)
        mCalendarView.setOnYearChangeListener(this)
        mCalendarView.setOnCalendarLongClickListener(this, false)
        mTextYear.text = mCalendarView.curYear.toString()
        mYear = mCalendarView.curYear
        loge(mYear.toString())
        mTextMonthDay.setText(mCalendarView.curMonth.toString() + "月" + mCalendarView.curDay + "日")
        mTextLunar.setText("今日")
    }

    @SuppressLint("SetTextI18n")
    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        if (calendar == null) {
            return
        }
        mTextLunar.visibility = View.VISIBLE
        mTextYear.visibility = View.VISIBLE
        mTextMonthDay.text = calendar.month.toString() + "月" + calendar.getDay() + "日"
        mTextYear.text = calendar.year.toString()
        mTextLunar.text = calendar.lunar
        mYear = calendar.year
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
