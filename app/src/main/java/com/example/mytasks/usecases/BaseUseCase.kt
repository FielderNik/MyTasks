package com.example.mytasks.usecases

import android.app.Application
import com.example.mytasks.data.TaskDataBase
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.DatabaseRepository
import com.example.mytasks.repositories.RemoteRepository

abstract class BaseUseCase(application: Application) {
    private val taskDao = TaskDataBase.getTaskDataBase(application).taskDao()
    val databaseRepository = DatabaseRepository(taskDao)

    val remoteRepository = RemoteRepository()

    abstract suspend fun run (taskEntity: TaskEntity)
}