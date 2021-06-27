package com.example.mytasks.adapters

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.R
import com.example.mytasks.databinding.TaskItemBinding
import com.example.mytasks.models.TaskEntity

class TaskListAdapter(private val taskAdapterCallback: TaskAdapterCallback): RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    interface TaskAdapterCallback {
        fun onTaskItemClicked(v: View, task: TaskEntity, position: Int)
    }

    val tasks = mutableListOf<TaskEntity>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding = DataBindingUtil.bind<TaskItemBinding>(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.task = tasks[position]
        holder.binding?.callback = taskAdapterCallback
        holder.binding?.position = position

        when(tasks[position].priority){
            0 -> {
                holder.binding?.ivTaskPriority?.setImageDrawable(null)
                holder.binding?.ivTaskPriority?.visibility = View.GONE
            }
            1 -> {
                holder.binding?.ivTaskPriority?.setImageResource(R.drawable.ic_low_priority)
                holder.binding?.ivTaskPriority?.visibility = View.VISIBLE
            }
            2 -> {
                holder.binding?.ivTaskPriority?.setImageResource(R.drawable.ic_high)
                holder.binding?.ivTaskPriority?.visibility = View.VISIBLE
            }
        }

        when(tasks[position].isComplete){
            true -> {
                holder.binding?.tvTask?.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(resources.getColor(R.color.tertiary))
                }
            }
            false -> {
                holder.binding?.tvTask?.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    setTextColor(resources.getColor(R.color.black))
                }
            }
        }

        holder.binding?.chbTaskComplete?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tasks[position].isComplete = true
                holder.binding?.tvTask?.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(resources.getColor(R.color.tertiary))
                }
            } else {
                tasks[position].isComplete = false
                holder.binding?.tvTask?.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    setTextColor(resources.getColor(R.color.black))
                }
            }
        }
    }

    override fun getItemCount(): Int = tasks.size

    fun updateList(newTasks: List<TaskEntity>){
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }

    fun removeItem (position: Int){
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }



}