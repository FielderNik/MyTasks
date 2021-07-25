package com.example.mytasks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.DatabaseRepository
import com.example.mytasks.usecases.DeleteTaskUseCase
import com.example.mytasks.usecases.EditTaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class MainScreenViewModel(application: Application) : AndroidViewModel(application), KoinComponent {


    private val deleteTaskUseCase: DeleteTaskUseCase by inject()
    private val editTaskUseCase: EditTaskUseCase by inject()

    private val databaseRepository: DatabaseRepository by inject()

    val tasksLiveData = MutableLiveData<List<TaskEntity>>()
    val allTasks = MutableLiveData<List<TaskEntity>>()



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