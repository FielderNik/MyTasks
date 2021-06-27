package com.example.mytasks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.TaskList

class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    val tasksLiveData = MutableLiveData<List<TaskEntity>>()

    init {
        getAllTasks()

    }


    private fun getAllTasks() {
        tasksLiveData.value = TaskList.tasks
    }


}