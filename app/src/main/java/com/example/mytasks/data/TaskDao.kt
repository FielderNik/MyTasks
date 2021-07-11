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

    @Query("SELECT * FROM task_table WHERE isComplete = :isComplete")
    suspend fun getTaskWithFilterCompleted(isComplete: Boolean): List<TaskEntity>

    @Query("DELETE FROM task_table WHERE id =:id")
    suspend fun deleteTask(id: String)

    @Query("UPDATE task_table SET taskBody =:taskBody, isComplete =:isComplete, deadLine =:deadline, priority =:priority, updatedAt =:updatedTime WHERE id =:id")
    suspend fun editTask(id: String, taskBody: String, isComplete: Boolean, deadline: Int, priority: String, updatedTime: Int)

    @Query("UPDATE task_table SET isSynchronised =:isSynchronised WHERE id =:id")
    suspend fun editTaskSynchronised(id: String, isSynchronised: Boolean)

    @Query("SELECT isSynchronised FROM task_table WHERE id =:id")
    suspend fun getSyncValueTask(id: String): Boolean

    @Query("UPDATE task_table SET isDeleted =:isDeleted WHERE id =:id")
    suspend fun setDeletedFlag(id: String, isDeleted: Boolean)
}