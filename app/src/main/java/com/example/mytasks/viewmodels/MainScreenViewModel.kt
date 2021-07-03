package com.example.mytasks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mytasks.data.TaskDataBase
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.DatabaseRepository
import com.example.mytasks.repositories.TaskList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    val tasksLiveData = MutableLiveData<List<TaskEntity>>()
    private val databaseRepository: DatabaseRepository
    val allTasks = MutableLiveData<List<TaskEntity>>()

    init {
        val taskDao = TaskDataBase.getTaskDataBase(application).taskDao()
        databaseRepository = DatabaseRepository(taskDao)

        getAllTasks()

    }

/*
    private fun getAllTasks() {
        tasksLiveData.value = TaskList.tasks
    }*/

    private fun getAllTasks(){
        CoroutineScope(Dispatchers.IO).launch {
            allTasks.postValue(databaseRepository.getAllTasks())
        }
    }


}