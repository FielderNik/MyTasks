package com.example.mytasks

import android.app.Application
import com.example.mytasks.di.RepositoryModule.repositoryModule
import com.example.mytasks.di.UseCasesModule.useCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.logger.EmptyLogger
import org.koin.core.module.Module

class MyTasksApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            if (BuildConfig.DEBUG) androidLogger() else EmptyLogger()
            androidContext(this@MyTasksApplication)
            modules(repositoryModule, useCasesModule)
        }
    }
}