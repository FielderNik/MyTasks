package com.example.mytasks.repositories

import android.util.Log
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.remote.Api
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RemoteRepository: KoinComponent {
    private val api: Api by inject()

    suspend fun addTaskToRemote(taskEntity: TaskEntity) : Boolean {
        var result = false
        val response = api.taskService.addNewTask(taskEntity).execute()
        if (response.code() == 200){
            result = true
        }
        Log.d("RemoteRepository", "response: ${response.errorBody()}")
        return result

    }

    suspend fun editTaskToRemote(taskEntity: TaskEntity): Boolean {
        var result = false
        val response = api.taskService.editTask(taskEntity.id, taskEntity).execute()
        if (response.code() == 200){
            result = true
        }
        Log.d("RemoteRepository", "response: ${response.errorBody()}")
        return result
    }

    suspend fun deleteTaskRemote(taskEntity: TaskEntity): Boolean {
        var result = false
        val response = api.taskService.deleteTask(taskEntity.id).execute()
        if (response.code() == 200){
            result = true
        }
        Log.d("RemoteRepository", "response: ${response.errorBody()}")
        return result
    }
}