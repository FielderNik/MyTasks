package com.example.mytasks

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.example.mytasks.data.TaskDao
import com.example.mytasks.data.TaskDataBase
import com.example.mytasks.models.TaskEntity
import com.example.mytasks.repositories.DatabaseRepository
import com.example.mytasks.repositories.DatabaseRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = MyTasksApplication::class, manifest = Config.NONE, sdk = [29])
abstract class AndroidBaseUnitTest: KoinComponent {

    lateinit var db: TaskDataBase
    lateinit var tasksDao: TaskDao

    val testDispatcher = TestCoroutineDispatcher()
    val testScope = TestCoroutineScope(testDispatcher)


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val context: Context = ApplicationProvider.getApplicationContext()
    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(context, TaskDataBase::class.java)
            .build()
        tasksDao = db.taskDao()
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        stopKoin()
        db.close()
    }
}
//
//
//@ExperimentalCoroutinesApi
//class AndroidBaseDbUnitTest: AndroidBaseUnitTest() {
//
//
//
//
//}