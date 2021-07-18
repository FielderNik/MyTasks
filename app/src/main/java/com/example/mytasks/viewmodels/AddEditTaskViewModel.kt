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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEditTaskViewModel(application: Application): AndroidViewModel(application) {

    private val addTaskUseCase = AddTaskUseCase(application)
    private val editTaskUseCase = EditTaskUseCase(application)
    private val deleteTaskUseCase = DeleteTaskUseCase(application)

    private val databaseRepository: DatabaseRepository
    private val api: Api
    val stringFromRemote = MutableLiveData<String>()

    init {
        val taskDao = TaskDataBase.getTaskDataBase(application).taskDao()
        databaseRepository = DatabaseRepository(taskDao)
        api = Api()

    }

    fun addTask(taskEntity: TaskEntity){
        CoroutineScope(Dispatchers.IO).launch {
            addTaskUseCase.run(taskEntity)
//            databaseRepository.addTask(taskEntity)
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

/*    fun addTaskToRemote(testTask: TaskEntity){
        api.taskService.addNewTask(testTask).enqueue(object : Callback<TaskEntity>{
            override fun onResponse(call: Call<TaskEntity>, response: Response<TaskEntity>) {
                Log.d("milk", "response: ${response.body()}")
                Log.d("milk", "response code: ${response.code()}")
            }

            override fun onFailure(call: Call<TaskEntity>, t: Throwable) {
                Log.d("milk", "failure: ${t.stackTraceToString()}")
            }

        })


    }*/

/*    fun getTasksFromRemote() {
        api.taskService.getTasks().enqueue(object : Callback<List<TestTask>> {
            override fun onResponse(call: Call<List<TestTask>>, response: Response<List<TestTask>>) {
                Log.d("milk", "response: ${response.body()}")
                Log.d("milk", "response code: ${response.code()}")
            }

            override fun onFailure(call: Call<List<TestTask>>, t: Throwable) {
                Log.d("milk", "failure: ${t.stackTraceToString()}")
            }

        })
    }*/


}