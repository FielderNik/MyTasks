package com.example.mytasks.usecases

import android.app.Application
import com.example.mytasks.data.TaskDataBase
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.DatabaseRepository
import com.example.mytasks.repositories.RemoteRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


abstract class BaseUseCase: KoinComponent {

    val databaseRepository: DatabaseRepository by inject()

    val remoteRepository: RemoteRepository by inject()

    abstract suspend fun run (taskEntity: TaskEntity)
}