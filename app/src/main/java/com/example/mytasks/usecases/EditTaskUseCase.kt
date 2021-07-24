package com.example.mytasks.usecases

import android.app.Application
import com.example.mytasks.models.TaskEntity
import org.koin.core.component.KoinComponent

class EditTaskUseCase: BaseUseCase(), KoinComponent {
    override suspend fun run(taskEntity: TaskEntity) {
        // записали отредактированную задачу в бд
        databaseRepository.editTask(taskEntity)

        // спрашиваем в бд, есть ли задача на сервере
        val isSync = databaseRepository.getSyncValueTask(taskEntity)

        if (!isSync) { // если задачи на сервере нет
            if (remoteRepository.addTaskToRemote(taskEntity)) { // пробуем записать новую задачу
                taskEntity.isSynchronised = true
                databaseRepository.editTaskSynchronised(taskEntity) // записываем флаг
            } else if (remoteRepository.editTaskToRemote(taskEntity)) { // на случай, если задача на сервере все таки есть
                taskEntity.isSynchronised = true
                databaseRepository.editTaskSynchronised(taskEntity) // записываем флаг
            }
        } else {
            // если задача на сервере есть
            if (!remoteRepository.editTaskToRemote(taskEntity)) { // пробуем изменить задачу на сервере
                // если не получилось изменить задачу на сервере, переставляем флаг на false
                taskEntity.isSynchronised = false
            }
            // записываем актуальный флаг
            databaseRepository.editTaskSynchronised(taskEntity)
        }







    }



}