package com.example.mytasks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mytasks.models.Priority
import com.example.mytasks.models.TaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDataBase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object{
      @Volatile
      private var INSTANCE: TaskDataBase? = null

        fun getTaskDataBase(context: Context): TaskDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, TaskDataBase::class.java, "table_task")
                    .addCallback(object : Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                tasks.forEach { task ->
                                    INSTANCE?.taskDao()?.addTask(task)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                return instance
            }
        }

        val tasks = mutableListOf(
            TaskEntity(
                id = UUID.randomUUID().toString(),
                taskBody = "Новая задача, которую надо сделать",
                deadline = 0,
                priority = Priority.HIGH.value,
                isComplete = false,
                createdAt = 0,
                updatedAt = 0,
                isSynchronised = false,
                isDeleted = false,
            ),
            TaskEntity(
                id = UUID.randomUUID().toString(),
                taskBody = "Еще одна задача",
                deadline = 0,
                priority = Priority.HIGH.value,
                isComplete = true,
                createdAt = 0,
                updatedAt = 0,
                isSynchronised = false,
                isDeleted = false,
            ),
            TaskEntity(
                id = UUID.randomUUID().toString(),
                taskBody = "Помыть кота",
                deadline = 1626070910,
                priority = Priority.LOW.value,
                isComplete = false,
                createdAt = 0,
                updatedAt = 0,
                isSynchronised = false,
                isDeleted = false,
            ),
            TaskEntity(
                id = UUID.randomUUID().toString(),
                taskBody = "Высушить кота",
                deadline = 1626157310,
                priority = Priority.NONE.value,
                isComplete = false,
                createdAt = 0,
                updatedAt = 0,
                isSynchronised = false,
                isDeleted = false,
            ),
            TaskEntity(
                id = UUID.randomUUID().toString(),
                taskBody = "Причесать кота",
                deadline = 1626243710,
                priority = Priority.LOW.value,
                isComplete = false,
                createdAt = 0,
                updatedAt = 0,
                isSynchronised = false,
                isDeleted = false,
            ),
            TaskEntity(
                id = UUID.randomUUID().toString(),
                taskBody = "Выгнать кота на улицу",
                deadline = 1626330110,
                priority = Priority.HIGH.value,
                isComplete = false,
                createdAt = 0,
                updatedAt = 0,
                isSynchronised = false,
                isDeleted = false,
            ),
            TaskEntity(
                id = UUID.randomUUID().toString(),
                taskBody = "Отдохнуть",
                deadline = 1626416510,
                priority = Priority.HIGH.value,
                isComplete = false,
                createdAt = 0,
                updatedAt = 0,
                isSynchronised = false,
                isDeleted = false,
            ),
        )

    }

}