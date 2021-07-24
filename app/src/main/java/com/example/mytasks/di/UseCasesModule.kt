package com.example.mytasks.di

import com.example.mytasks.usecases.AddTaskUseCase
import com.example.mytasks.usecases.DeleteTaskUseCase
import com.example.mytasks.usecases.EditTaskUseCase
import org.koin.dsl.module

object UseCasesModule {

    val useCasesModule = module {
        factory {
            AddTaskUseCase()
        }

        factory {
            DeleteTaskUseCase()
        }

        factory {
            EditTaskUseCase()
        }
    }
}