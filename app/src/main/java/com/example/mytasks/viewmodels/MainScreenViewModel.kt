package com.example.mytasks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytasks.data.TaskDataBase
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.DatabaseRepository
import com.example.mytasks.repositories.TaskList
import com.example.mytasks.usecases.DeleteTaskUseCase
import com.example.mytasks.usecases.EditTaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainScreenViewModel(application: Application) : AndroidViewModel(application) {


    private val deleteTaskUseCase = DeleteTaskUseCase(application)
    private val editTaskUseCase = EditTaskUseCase(application)
    val tasksLiveData = MutableLiveData<List<TaskEntity>>()
    private val databaseRepository: DatabaseRepository
    val allTasks = MutableLiveData<List<TaskEntity>>()

    init {
        val taskDao = TaskDataBase.getTaskDataBase(application).taskDao()
        databaseRepository = DatabaseRepository(taskDao)

//        getTaskWithFilterCompleted(false)

    }

/*
    private fun getAllTasks() {
        tasksLiveData.value = TaskList.tasks
    }*/

    fun getAllTasks(){
        CoroutineScope(Dispatchers.IO).launch {
            allTasks.postValue(databaseRepository.getAllTasks())
        }
    }

    fun getTaskWithFilterCompleted(isComplete: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            allTasks.postValue(databaseRepository.getTaskWithFilterCompleted(isComplete))
        }
    }

    fun addTask(taskEntity: TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {
            databaseRepository.addTask(taskEntity)
        }
    }

    fun deleteTask(taskEntity: TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {
            deleteTaskUseCase.run(taskEntity)
//            databaseRepository.deleteTask(id)
        }
    }


    fun editTask(taskEntity: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            editTaskUseCase.run(taskEntity)
        }
    }


}