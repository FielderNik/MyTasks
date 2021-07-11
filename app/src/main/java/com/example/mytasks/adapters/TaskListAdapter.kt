package com.example.mytasks.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.R
import com.example.mytasks.databinding.TaskItemBinding
import com.example.mytasks.models.Priority
import com.example.mytasks.models.TaskEntity
import java.text.SimpleDateFormat
import java.util.*

class TaskListAdapter(private val taskAdapterCallback: TaskAdapterCallback): RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    interface TaskAdapterCallback {
        fun onTaskItemClicked(v: View, task: TaskEntity, position: Int)
        fun onCheckItemClicked(task: TaskEntity)
        fun onUnCheckItemClicked(task: TaskEntity)
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

        if (tasks[position].deadline == 0){
            holder.binding?.tvDeadline?.visibility = View.GONE
        } else {
            val date = Date(tasks[position].deadline * 1000L)
            val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val dateString = sdf.format(date)
            holder.binding?.tvDeadline?.text = dateString
        }


        when(tasks[position].priority){
            Priority.NONE.value -> {
                holder.binding?.ivTaskPriority?.setImageDrawable(null)
                holder.binding?.ivTaskPriority?.visibility = View.GONE
            }
            Priority.LOW.value -> {
                holder.binding?.ivTaskPriority?.setImageResource(R.drawable.ic_low_priority)
                holder.binding?.ivTaskPriority?.visibility = View.VISIBLE
            }
            Priority.HIGH.value -> {
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
//                tasks[position].isComplete = true
                holder.binding.tvTask.apply {
                    paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    setTextColor(resources.getColor(R.color.tertiary))
                }
                taskAdapterCallback.onCheckItemClicked(tasks[position])
            } else {
//                tasks[position].isComplete = false
                holder.binding.tvTask.apply {
                    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    setTextColor(resources.getColor(R.color.black))
                }
                taskAdapterCallback.onUnCheckItemClicked(tasks[position])
            }
        }
    }

    override fun getItemCount(): Int = tasks.size

    fun updateList(newTasks: List<TaskEntity>){
        val tasksDiffUtil = TasksDiffUtil(tasks, newTasks)
        val diffResult = DiffUtil.calculateDiff(tasksDiffUtil)
        tasks.clear()
        tasks.addAll(newTasks)
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeItem (position: Int){
        tasks.removeAt(position)
        notifyItemRemoved(position)
    }



}