package com.example.mytasks.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mytasks.data.TaskDataBase
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.models.TestTask
import com.example.mytasks.remote.Api
import com.example.mytasks.repositories.DatabaseRepository
import com.example.mytasks.usecases.AddTaskUseCase
import com.example.mytasks.usecases.DeleteTaskUseCase
import com.example.mytasks.usecases.EditTaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEditTaskViewModel(application: Application): AndroidViewModel(application), KoinComponent {

    private val addTaskUseCase: AddTaskUseCase by inject()
    private val editTaskUseCase: EditTaskUseCase by inject()
    private val deleteTaskUseCase: DeleteTaskUseCase by inject()


    fun addTask(taskEntity: TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {
            addTaskUseCase.run(taskEntity)
        }
    }

    fun editTask(taskEntity: TaskEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            editTaskUseCase.run(taskEntity)
        }
    }

    fun deleteTask(taskEntity: TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {
            deleteTaskUseCase.run(taskEntity)
        }
    }


}