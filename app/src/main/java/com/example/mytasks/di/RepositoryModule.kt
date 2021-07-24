package com.example.mytasks.di

import android.app.Application
import androidx.room.Room
import com.example.mytasks.data.TaskDao
import com.example.mytasks.data.TaskDataBase
import com.example.mytasks.remote.Api
import com.example.mytasks.repositories.DatabaseRepository
import com.example.mytasks.repositories.RemoteRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object RepositoryModule {

    val repositoryModule = module {

        fun provideDatabase(application: Application): TaskDataBase {
            return Room.databaseBuilder(application, TaskDataBase::class.java, "table_task")
                .fallbackToDestructiveMigration()
                .build()
        }

        fun provideTaskDao(dataBase: TaskDataBase): TaskDao {
            return dataBase.taskDao()
        }

        single { provideDatabase(androidApplication()) }

        single { provideTaskDao(get()) }


        factory {
            DatabaseRepository(get())
        }

        factory {
            RemoteRepository()
        }

        factory {
            Api()
        }


    }
}