package com.example.mytasks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mytasks.models.TaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            TaskEntity(1, "Новая задача, которую надо сделать", "", 1, false),
            TaskEntity(2, "Новая задача, которую", "", 0, false),
            TaskEntity(3, "Новая задача", "", 2, true),
            TaskEntity(
                4,
                "Новая задача, которую надо сделать, которую надо сделать",
                "",
                2,
                false
            ),
            TaskEntity(
                5,
                "Новая задача, которую надо сделать, которую надо сделать, которую надо сделать",
                "",
                1,
                true
            ),
            TaskEntity(6, "Новая задача, которую надо сделать", "", 1, true),
            TaskEntity(7, "Новая задача", "", 0, false)
        )



    }

}