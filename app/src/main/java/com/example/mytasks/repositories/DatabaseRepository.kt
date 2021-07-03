package com.example.mytasks.repositories

import androidx.lifecycle.LiveData
import com.example.mytasks.data.TaskDao
import com.example.mytasks.models.TaskEntity

class DatabaseRepository(private val tasksDao: TaskDao) {




    suspend fun getAllTasksList(): List<TaskEntity> {
        return tasksDao.getAllTasksList()
    }

    suspend fun getAllTasks(): List<TaskEntity> {
        return tasksDao.getAllTask()
    }
}