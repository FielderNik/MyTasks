package com.example.mytasks.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.mytasks.models.TaskEntity

class TasksDiffUtil(private val oldList: List<TaskEntity>, private val newList: List<TaskEntity>): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem::class != newItem::class) return false

        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }


}