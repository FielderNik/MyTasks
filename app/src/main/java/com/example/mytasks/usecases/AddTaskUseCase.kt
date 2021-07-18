package com.example.mytasks.usecases

import android.app.Application
import com.example.mytasks.data.TaskDataBase
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.DatabaseRepository
import com.example.mytasks.repositories.RemoteRepository

class AddTaskUseCase(application: Application) : BaseUseCase(application) {


    override suspend fun run(taskEntity: TaskEntity) {
        //добавляем таску в БД
        databaseRepository.addTask(taskEntity)

        // пробуем отправить таску на сервер, если получается, то передаем в БД инфо о том, что данная таска синхронизирована
        if (remoteRepository.addTaskToRemote(taskEntity)) {
            taskEntity.isSynchronised = true
            databaseRepository.editTaskSynchronised(taskEntity)
        }
    }
}