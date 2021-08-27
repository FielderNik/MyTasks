package com.example.mytasks.repositories

import androidx.lifecycle.LiveData
import com.example.mytasks.data.TaskDao
import com.example.mytasks.models.TaskEntity
import org.koin.core.component.KoinComponent

interface DatabaseRepository {

}

class DatabaseRepositoryImpl(private val tasksDao: TaskDao): KoinComponent {


    suspend fun addTask(taskEntity: TaskEntity) {
        tasksDao.addTask(taskEntity)
    }

    suspend fun getAllTasksList(): List<TaskEntity> {
        return tasksDao.getAllTasksList()
    }

    suspend fun getAllTasks(): List<TaskEntity> {
        return tasksDao.getAllTask()
    }

    suspend fun getTaskWithFilterCompleted(isComplete: Boolean): List<TaskEntity> {
        return tasksDao.getTaskWithFilterCompleted(isComplete)
    }

    suspend fun deleteTask(id: String){
        return tasksDao.deleteTask(id)
    }

    suspend fun editTask(taskEntity: TaskEntity) {
        return tasksDao.editTask(taskEntity.id, taskEntity.taskBody, taskEntity.isComplete, taskEntity.deadline, taskEntity.priority, taskEntity.updatedAt)
    }

    suspend fun editTaskSynchronised(taskEntity: TaskEntity) {
        return tasksDao.editTaskSynchronised(taskEntity.id, taskEntity.isSynchronised)
    }

    suspend fun getSyncValueTask(taskEntity: TaskEntity): Boolean {
        return tasksDao.getSyncValueTask(taskEntity.id)
    }

    suspend fun setDeletedFlag(taskEntity: TaskEntity) {
        return tasksDao.setDeletedFlag(taskEntity.id, taskEntity.isDeleted)
    }


}