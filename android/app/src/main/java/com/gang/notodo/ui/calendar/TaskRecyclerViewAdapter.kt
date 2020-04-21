package com.gang.notodo.ui.calendar


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

import com.gang.notodo.R
import com.gang.notodo.data.Task
import com.gang.notodo.util.toast


/**
 * RecyclerView适配器
 */
class TaskRecyclerViewAdapter(private val mContext: Context) : Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    //数据列表
    private var mDataList: List<Task> = arrayListOf()

    /**
     * 返回数据List大小
     */
    override fun getItemCount(): Int {
        return mDataList.size
    }

    /**
     * 关联ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTitle.text = mDataList[position].title
        holder.mDate.text = mDataList[position].title
        holder.mRoot.setOnClickListener { asv ->
            mContext.toast(mDataList[position].toString())
            // todo 打开内容页
        }
    }


    /**
     * 初始化ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_item,
                parent,
                false
            )
        )
    }


    /**
     * ViewHolder
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mTitle: TextView = itemView.findViewById(R.id.item_title)
        val mDate: TextView = itemView.findViewById(R.id.item_date)
        val mRoot: View = itemView.findViewById(R.id.root)
    }

    fun setmDataList(dataList: List<Task>) {
        this.mDataList = dataList
    }

}
