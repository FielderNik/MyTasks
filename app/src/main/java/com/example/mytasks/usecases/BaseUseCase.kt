package com.example.mytasks.usecases

import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.DatabaseRepositoryImpl
import com.example.mytasks.repositories.RemoteRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


abstract class BaseUseCase: KoinComponent {

    val databaseRepositoryImpl: DatabaseRepositoryImpl by inject()

    val remoteRepository: RemoteRepository by inject()

    abstract suspend fun run (taskEntity: TaskEntity)
}