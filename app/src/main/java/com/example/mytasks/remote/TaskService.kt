package com.example.mytasks.remote

import com.example.mytasks.models.TaskEntity
import com.example.mytasks.models.TestTask
import retrofit2.Call
import retrofit2.http.*

interface TaskService {
    @Headers("Authorization: Bearer e9c740e693624beca6a3afedf7610dc6")
    @POST("/tasks/")
    fun addNewTask(@Body testTask: TaskEntity): Call<TaskEntity>

    @Headers("Authorization: Bearer e9c740e693624beca6a3afedf7610dc6")
    @GET("/tasks/")
    fun getTasks(): Call<List<TestTask>>

    @Headers("Authorization: Bearer e9c740e693624beca6a3afedf7610dc6")
    @PUT("/tasks/{task_id}")
    fun editTask(@Path("task_id") taskId: String, @Body taskEntity: TaskEntity): Call<TaskEntity>

    @Headers("Authorization: Bearer e9c740e693624beca6a3afedf7610dc6")
    @DELETE("/tasks/{task_id}")
    fun deleteTask(@Path("task_id") taskId: String): Call<TaskEntity>

}