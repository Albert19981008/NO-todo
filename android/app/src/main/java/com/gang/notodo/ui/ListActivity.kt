package com.gang.notodo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.gang.notodo.R
import com.gang.notodo.util.setupActionBar
import com.gang.notodo.util.startActivity
import com.gang.notodo.util.toast

class ListActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    private lateinit var mRootView: View
    private lateinit var mToolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initView()
    }

    private fun initView() {
        mRootView = findViewById(R.id.root)
        mToolBar = findViewById(R.id.toolBar)
        setupActionBar(R.id.toolBar) {
            setHomeAsUpIndicator(R.drawable.ic_menu)
            setDisplayHomeAsUpEnabled(true)
            title = "NO-todo"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val popup = PopupMenu(this, mToolBar)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.menu_indicator, popup.menu)
            popup.setOnMenuItemClickListener(this)
            popup.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_calendar -> startActivity<TaskActivity>()
            R.id.item_todo -> toast("已经是列表页")
        }
        return false
    }

}
