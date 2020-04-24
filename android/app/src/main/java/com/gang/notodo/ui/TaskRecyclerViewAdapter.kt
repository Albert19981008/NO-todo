package com.gang.notodo.ui


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.gang.notodo.R
import com.gang.notodo.data.Task
import com.gang.notodo.data.TaskRepository
import com.gang.notodo.util.toast


/**
 * RecyclerView适配器
 */
class TaskRecyclerViewAdapter(private val mContext: Context) :
    Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    //数据列表
    var mDataList: List<Task> = arrayListOf()

    /**
     * 返回数据List大小
     */
    override fun getItemCount() = mDataList.size

    /**
     * 初始化ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
    )


    /**
     * 关联ViewHolder
     */
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTitle.text = mDataList[position].title
        mDataList[position].let {
            holder.mDate.text = "" + it.year + "-" + it.month + "-" + it.day
        }
        holder.mRoot.setOnClickListener {
            mContext.toast("详情: " + mDataList[position].description)
        }

        if (mDataList[position].isActive) {
            holder.mRoot.setOnLongClickListener { view ->

                val popup = PopupMenu(mContext, view)
                val inflater = popup.menuInflater
                inflater.inflate(R.menu.menu_list_item_complete, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.item_complete -> {
                            val toDelete = mDataList[position]
                            mDataList = mDataList.filter {
                                it.id != toDelete.id
                            }
                            notifyDataSetChanged()
                            TaskRepository.completeTask(toDelete.id)
                        }
                    }
                    false
                }
                popup.show()

                true
            }
        } else {
            holder.mRoot.setOnLongClickListener { view ->

                val popup = PopupMenu(mContext, view)
                val inflater = popup.menuInflater
                inflater.inflate(R.menu.menu_list_item_delete, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.item_delete -> {
                            val toDelete = mDataList[position]
                            mDataList = mDataList.filter {
                                it.id != toDelete.id
                            }
                            notifyDataSetChanged()
                            TaskRepository.deleteTask(toDelete.id)
                        }
                    }
                    false
                }
                popup.show()

                true
            }
        }

    }


    /**
     * ViewHolder
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mTitle: TextView = itemView.findViewById(R.id.item_title)
        val mDate: TextView = itemView.findViewById(R.id.item_date)
        val mRoot: View = itemView.findViewById(R.id.root)
    }

}
