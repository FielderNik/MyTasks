package com.example.mytasks.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.usecases.AddTaskUseCase
import com.example.mytasks.usecases.DeleteTaskUseCase
import com.example.mytasks.usecases.EditTaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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