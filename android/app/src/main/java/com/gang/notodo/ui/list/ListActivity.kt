package com.gang.notodo.ui.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.gang.notodo.R
import com.gang.notodo.ui.calendar.CalendarActivity
import com.gang.notodo.util.setupActionBar
import com.gang.notodo.util.startActivity
import com.gang.notodo.util.toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout


class ListActivity : AppCompatActivity() {

    private lateinit var mRootView: View
    private lateinit var mToolBar: Toolbar
    private lateinit var mTabLayout: TabLayout
    private lateinit var mViewPager: ViewPager
    private lateinit var mClearButton: FloatingActionButton

    //ViewPager的适配器
    private lateinit var mViewPagerFragmentAdapter: ViewPagerFragmentAdapter

    //FragmentManager
    private val mFragmentManager = supportFragmentManager

    //不同新闻页的List
    private val mFragmentList = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initView()
    }

    private fun initView() {
        mRootView = findViewById(R.id.root)
        mToolBar = findViewById(R.id.toolBar)
        mTabLayout = findViewById(R.id.tabs)
        mViewPager = findViewById(R.id.view_pager)
        mClearButton = findViewById(R.id.task_clear)
        mClearButton.visibility = View.GONE

        initToolBar()
        initFragmentList()
        initViewPager()
        bindTabAndPager()
        bindClearButtonAndPosition()
    }

    private fun initToolBar() {
        setupActionBar(R.id.toolBar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
            title = "todo-list"
        }
        mToolBar.setNavigationOnClickListener { v ->
            val popup = PopupMenu(this, v)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.menu_indicator, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.item_calendar -> startActivity<CalendarActivity>()
                    R.id.item_todo -> toast("已经是列表页")
                }
                false
            }
            popup.show()
        }
    }

    /**
     * 初始化ViewPager并添加适配器
     */
    private fun initViewPager() {
        mViewPager.adapter = mViewPagerFragmentAdapter
        mViewPager.currentItem = 0
    }


    /**
     * 初始化Fragment（页）的列表，初始化并适配每页的Presenter,View 和 ViewPagerFragmentAdapter
     */
    private fun initFragmentList() {
        var active = true
        for (i in 0..1) {
            //初始化每个页面
            val fragment = ListFragment(R.layout.fragment_list, active)
            fragment.setContext(this)
            mFragmentList.add(fragment)
            active = !active
        }
        //设置适配器
        mViewPagerFragmentAdapter =
            ViewPagerFragmentAdapter(mFragmentManager, mFragmentList)
    }

    private fun bindClearButtonAndPosition() {
        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> mClearButton.visibility = View.GONE
                    1 -> mClearButton.visibility = View.VISIBLE
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun bindTabAndPager() {
        mTabLayout.setupWithViewPager(mViewPager)
    }

}
