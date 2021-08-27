package com.example.mytasks.usecases

import com.example.mytasks.models.TaskEntity
import org.koin.core.component.KoinComponent

class DeleteTaskUseCase: BaseUseCase(), KoinComponent {
    override suspend fun run(taskEntity: TaskEntity) {
        taskEntity.isDeleted = true
        databaseRepositoryImpl.setDeletedFlag(taskEntity)
        val isSync = databaseRepositoryImpl.getSyncValueTask(taskEntity)

        if (isSync) {
            if (remoteRepository.deleteTaskRemote(taskEntity)) {
                databaseRepositoryImpl.deleteTask(taskEntity.id)
            }
        } else {
            databaseRepositoryImpl.deleteTask(taskEntity.id)
        }
    }
}