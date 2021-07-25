package com.example.mytasks.remote

import com.example.mytasks.functional.Constants
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.models.TestTask
import retrofit2.Call
import retrofit2.http.*

interface TaskService {
    @Headers("Authorization: Bearer ${Constants.AUTH_TOKEN}")
    @POST("/tasks/")
    fun addNewTask(@Body testTask: TaskEntity): Call<TaskEntity>

    @Headers("Authorization: Bearer ${Constants.AUTH_TOKEN}")
    @GET("/tasks/")
    fun getTasks(): Call<List<TestTask>>

    @Headers("Authorization: Bearer ${Constants.AUTH_TOKEN}")
    @PUT("/tasks/{task_id}")
    fun editTask(@Path("task_id") taskId: String, @Body taskEntity: TaskEntity): Call<TaskEntity>

    @Headers("Authorization: Bearer ${Constants.AUTH_TOKEN}")
    @DELETE("/tasks/{task_id}")
    fun deleteTask(@Path("task_id") taskId: String): Call<TaskEntity>

}