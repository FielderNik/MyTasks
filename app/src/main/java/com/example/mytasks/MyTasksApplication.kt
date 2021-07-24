package com.example.mytasks

import android.app.Application
import com.example.mytasks.di.RepositoryModule.repositoryModule
import com.example.mytasks.di.UseCasesModule.useCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyTasksApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyTasksApplication)
            modules(repositoryModule, useCasesModule)
        }
    }
}