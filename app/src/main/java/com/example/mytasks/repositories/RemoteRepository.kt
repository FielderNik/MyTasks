package com.example.mytasks.repositories

import android.util.Log
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.remote.Api
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteRepository: KoinComponent {
    private val api: Api by inject()

    suspend fun addTaskToRemote(taskEntity: TaskEntity) : Boolean {
        var result = false
        val response = api.taskService.addNewTask(taskEntity).execute()
        if (response.code() == 200){
            result = true
        }
        return result
/*

        api.taskService.addNewTask(taskEntity).enqueue(object : Callback<TaskEntity> {
            override fun onResponse(call: Call<TaskEntity>, response: Response<TaskEntity>) {
                if (response.code() == 200) {

                }
                Log.d("milk", "response: ${response.body()}")
                Log.d("milk", "response code: ${response.code()}")
            }

            override fun onFailure(call: Call<TaskEntity>, t: Throwable) {
                Log.d("milk", "failure: ${t.stackTraceToString()}")
            }

        })*/
    }

    suspend fun editTaskToRemote(taskEntity: TaskEntity): Boolean {
        var result = false
        val response = api.taskService.editTask(taskEntity.id, taskEntity).execute()
        if (response.code() == 200){
            result = true
        }
        return result
    }

    suspend fun deleteTaskRemote(taskEntity: TaskEntity): Boolean {
        var result = false
        val response = api.taskService.deleteTask(taskEntity.id).execute()
        if (response.code() == 200){
            result = true
        }
        return result
    }
}