package com.gang.notodo.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gang.notodo.R
import com.gang.notodo.data.Task

class ListFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var mView: View? = null
    private lateinit var mContext: Context

    // RecyclerView
    private lateinit var mRecyclerView: RecyclerView

    //recyclerView 的适配器
    private lateinit var mRecyclerViewAdapter: TaskRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //判断是否是第一次初始化该Fragment
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_list, null)
            initRecyclerView()
        }
        return mView
    }

    private fun initRecyclerView() {
        mRecyclerView = mView?.findViewById(R.id.list_recycler)!!
        //设置线性管理器
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)

        //设置分割线
        mRecyclerView.addItemDecoration(
            DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        )

        mRecyclerViewAdapter = TaskRecyclerViewAdapter(mContext)
        mRecyclerView.adapter = mRecyclerViewAdapter
    }

    fun setDataAndRefresh(tasks: List<Task>) {
        mRecyclerViewAdapter.mDataList = tasks
        mRecyclerViewAdapter.notifyDataSetChanged()
        mRecyclerView.scrollToPosition(0)
    }

    //设置Context
    fun setContext(context: Context) {
        mContext = context
    }
}