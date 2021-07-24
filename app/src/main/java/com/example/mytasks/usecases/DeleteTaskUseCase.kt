package com.example.mytasks.usecases

import android.app.Application
import com.example.mytasks.models.TaskEntity
import org.koin.core.component.KoinComponent

class DeleteTaskUseCase: BaseUseCase(), KoinComponent {
    override suspend fun run(taskEntity: TaskEntity) {
        taskEntity.isDeleted = true
        databaseRepository.setDeletedFlag(taskEntity)
        val isSync = databaseRepository.getSyncValueTask(taskEntity)

        if (isSync) {
            if (remoteRepository.deleteTaskRemote(taskEntity)) {
                databaseRepository.deleteTask(taskEntity.id)
            }
        } else {
            databaseRepository.deleteTask(taskEntity.id)
        }
    }
}