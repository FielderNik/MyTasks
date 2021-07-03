package com.example.mytasks.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytasks.models.TaskEntity

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM task_table")
    suspend fun getAllTasksList(): List<TaskEntity>

    @Query("SELECT * FROM task_table")
    suspend fun getAllTask(): List<TaskEntity>
}