package com.example.mytasks.usecases

import android.app.Application
import com.example.mytasks.models.TaskEntity

class DeleteTaskUseCase(application: Application): BaseUseCase(application) {
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