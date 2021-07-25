package com.example.mytasks.usecases

import com.example.mytasks.models.TaskEntity
import org.koin.core.component.KoinComponent

class AddTaskUseCase: BaseUseCase(), KoinComponent {


    override suspend fun run(taskEntity: TaskEntity) {
        //добавляем таску в БД
        databaseRepositoryImpl.addTask(taskEntity)

        // пробуем отправить таску на сервер, если получается, то передаем в БД инфо о том, что данная таска синхронизирована
        if (remoteRepository.addTaskToRemote(taskEntity)) {
            taskEntity.isSynchronised = true
            databaseRepositoryImpl.editTaskSynchronised(taskEntity)
        }
    }
}