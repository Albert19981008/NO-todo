package com.gang.notodo.ui.calendar


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint

import com.gang.notodo.util.CalendarUtil
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.MonthView
import kotlin.math.min


class IndexMonthView(context: Context) : MonthView(context) {

    private val mPadding = CalendarUtil.dipToPx(context, 4f)
    private val mH = CalendarUtil.dipToPx(context, 2f)
    private val mW = CalendarUtil.dipToPx(context, 8f)
    private var mRadius: Int = 0

    private val mSchemeBasicPaint = Paint().apply {
        this.isAntiAlias = true
        this.style = Paint.Style.FILL
        this.textAlign = Paint.Align.CENTER
        this.color = -0xcccccd
        this.isFakeBoldText = true
    }


    override fun onPreviewHook() {
        mRadius = min(mItemWidth, mItemHeight) * 5 / 12
    }


    override fun onDrawSelected(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean
    ): Boolean {
        //        mSelectedPaint.setStyle(Paint.Style.FILL);
        //        canvas.drawRect(x + mPadding, y + mPadding, x + mItemWidth - mPadding, y + mItemHeight - mPadding, mSelectedPaint);
        //        return true;
        val cx = x + mItemWidth / 2
        val cy = y + mItemHeight / 2
        canvas.drawCircle(cx.toFloat(), cy.toFloat(), mRadius.toFloat(), mSelectedPaint)
        return true
    }

    /**
     * onDrawSelected
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    override fun onDrawScheme(canvas: Canvas, calendar: Calendar, x: Int, y: Int) {
        mSchemeBasicPaint.color = calendar.schemeColor
        canvas.drawRect(
            (x + mItemWidth / 2 - mW / 2).toFloat(),
            (y + mItemHeight - mH * 2 - mPadding).toFloat(),
            (x + mItemWidth / 2 + mW / 2).toFloat(),
            (y + mItemHeight - mH - mPadding).toFloat(), mSchemeBasicPaint
        )
    }

    override fun onDrawText(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        y: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {
        val cx = x + mItemWidth / 2
        val top = y - mItemHeight / 6
        if (hasScheme) {
            canvas.drawText(
                calendar.day.toString(), cx.toFloat(), mTextBaseLine + top,
                when {
                    calendar.isCurrentDay -> mCurDayTextPaint
                    calendar.isCurrentMonth -> mSchemeTextPaint
                    else -> mOtherMonthTextPaint
                }
            )

            canvas.drawText(
                calendar.lunar,
                cx.toFloat(),
                mTextBaseLine + y.toFloat() + (mItemHeight / 10).toFloat(),
                if (calendar.isCurrentDay)
                    mCurDayLunarTextPaint
                else
                    mCurMonthLunarTextPaint
            )

        } else {
            canvas.drawText(
                calendar.day.toString(), cx.toFloat(), mTextBaseLine + top,
                when {
                    calendar.isCurrentDay -> mCurDayTextPaint
                    calendar.isCurrentMonth -> mCurMonthTextPaint
                    else -> mOtherMonthTextPaint
                }
            )
            canvas.drawText(
                calendar.lunar,
                cx.toFloat(),
                mTextBaseLine + y.toFloat() + (mItemHeight / 10).toFloat(),
                mCurMonthLunarTextPaint
            )
        }
    }

}
